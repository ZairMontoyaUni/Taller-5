package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

class RestauranteTest {
	
	private Restaurante restaurante;

	@BeforeEach
	void setUp() throws Exception {
		restaurante = new Restaurante();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
    void testIniciarPedido() throws YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Juan Moreno", "Calle 153");
        
        assertNotNull(restaurante.getPedidoEnCurso(), "No se inició correctamente el pedido.");
        assertEquals("Juan Moreno", restaurante.getPedidoEnCurso().getNombreCliente(), "El nombre del cliente no coincide.");
    }
	
	@Test
    void testIniciarPedidoYaExistente() throws YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Juan Moreno", "Calle 153");
        assertThrows(YaHayUnPedidoEnCursoException.class, () -> {
            restaurante.iniciarPedido("Carlos Vega", "Calle 127");
        }, "Se permitió iniciar un segundo pedido sin cerrar el primero.");
    }
	
	@Test
	void testCerrarYGuardarPedido() throws YaHayUnPedidoEnCursoException, NoHayPedidoEnCursoException, IOException {
        restaurante.iniciarPedido("Juan Moreno", "Calle 153");
       
        restaurante.cerrarYGuardarPedido();
        
        assertNull(restaurante.getPedidoEnCurso(), "No se cerró el pedido correctamente.");
    }
	
	@Test
	void testProductoRepetido() throws IOException {
	    File archivoIngredientes = new File("ingredientesTest.txt");
	    PrintWriter writerIngredientes = new PrintWriter(archivoIngredientes);
	    writerIngredientes.println("Tomate;1000");
	    writerIngredientes.close();

	    File archivoCombos = new File("combosTest.txt");
	    PrintWriter writerCombos = new PrintWriter(archivoCombos);
	    writerCombos.close();

	    File archivoMenu = new File("menuBaseTest.txt");
	    PrintWriter writerMenu = new PrintWriter(archivoMenu);
	    writerMenu.println("Hamburguesa;10000");
	    writerMenu.println("Hamburguesa;10000"); 
	    writerMenu.close();

	    assertThrows(ProductoRepetidoException.class, () -> {
	        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
	    });

	    archivoIngredientes.delete();
	    archivoCombos.delete();
	    archivoMenu.delete();
	}
	
	@Test
	void testComboRepetido() throws IOException {
	    File archivoIngredientes = new File("ingredientesTest.txt");
	    PrintWriter writerIngredientes = new PrintWriter(archivoIngredientes);
	    writerIngredientes.println("Tomate;1000");
	    writerIngredientes.close();

	    File archivoMenu = new File("menuBaseTest.txt");
	    PrintWriter writerMenu = new PrintWriter(archivoMenu);
	    writerMenu.println("Hamburguesa;10000");
	    writerMenu.println("Papas Fritas;5000");
	    writerMenu.close();

	    File archivoCombos = new File("combosTest.txt");
	    PrintWriter writerCombos = new PrintWriter(archivoCombos);
	    writerCombos.println("Combo1;20%;Hamburguesa;Papas Fritas");
	    writerCombos.println("Combo1;20%;Hamburguesa;Papas Fritas"); 
	    writerCombos.close();

	    assertThrows(ProductoRepetidoException.class, () -> {
	        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
	    });

	    archivoIngredientes.delete();
	    archivoMenu.delete();
	    archivoCombos.delete();
	}
	
	@Test
	void testIngredienteRepetido() throws IOException {
	    File archivoIngredientes = new File("ingredientesTest.txt");
	    PrintWriter writerIngredientes = new PrintWriter(archivoIngredientes);
	    writerIngredientes.println("Lechuga;500");
	    writerIngredientes.println("Lechuga;500"); 
	    writerIngredientes.close();

	    assertThrows(IngredienteRepetidoException.class, () -> {
	        restaurante.cargarInformacionRestaurante(archivoIngredientes, new File("menuBaseTest.txt"), new File("combosTest.txt"));
	    });

	    archivoIngredientes.delete();
	}



	@Test
	void testProductoFaltanteEnCombo() throws IOException {

	    File archivoIngredientes = new File("ingredientesTest.txt");
	    PrintWriter writerIngredientes = new PrintWriter(archivoIngredientes);
	    writerIngredientes.println("Tomate;1000");
	    writerIngredientes.close();

	    File archivoMenu = new File("menuBaseTest.txt");
	    PrintWriter writerMenu = new PrintWriter(archivoMenu);
	    writerMenu.println("Hamburguesa;10000");
	    writerMenu.close();

	    File archivoCombos = new File("combosTest.txt");
	    PrintWriter writerCombos = new PrintWriter(archivoCombos);
	    writerCombos.println("Combo1;20%;Hamburguesa;ProductoFaltante");
	    writerCombos.close();

	    assertThrows(ProductoFaltanteException.class, () -> {
	        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
	    });

	    archivoIngredientes.delete();
	    archivoMenu.delete();
	    archivoCombos.delete();
	}


	
	@Test
	void testCerrarYGuardarPedidoSinProductos() throws YaHayUnPedidoEnCursoException, NoHayPedidoEnCursoException, IOException {
	    restaurante.iniciarPedido("Juan Moreno", "Calle 153");
	    restaurante.cerrarYGuardarPedido();
	    
	    assertNull(restaurante.getPedidoEnCurso(), "El pedido debería haberse cerrado correctamente incluso sin productos.");
	}
	
	@Test
	void testGetIngredientes() {
	    assertNotNull(restaurante.getIngredientes(), "La lista de ingredientes no debería ser null.");
	}

	@Test
	void testGetMenuCombos() {
	    assertNotNull(restaurante.getMenuCombos(), "La lista de combos no debería ser null.");
	}

	@Test
	void testGetPedidos() {
	    assertNotNull(restaurante.getPedidos(), "La lista de pedidos no debería ser null.");
	}
	
	@Test
	void testCargarMenuBase() throws IOException, HamburguesaException {
	    File archivoIngredientes = new File("ingredientesTest.txt");
	    PrintWriter writerIngredientes = new PrintWriter(archivoIngredientes);
	    writerIngredientes.close();

	    File archivoMenu = new File("menuBaseTest.txt");
	    PrintWriter writerMenu = new PrintWriter(archivoMenu);
	    writerMenu.println("Hamburguesa;10000");
	    writerMenu.println("Papas Fritas;5000");
	    writerMenu.close();

	    File archivoCombos = new File("combosTest.txt");
	    PrintWriter writerCombos = new PrintWriter(archivoCombos);
	    writerCombos.close();

	    restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);

	    ArrayList<ProductoMenu> menuBase = restaurante.getMenuBase();
	    assertEquals(2, menuBase.size());
	    assertEquals("Hamburguesa", menuBase.get(0).getNombre());
	    assertEquals(10000, menuBase.get(0).getPrecio());

	    archivoIngredientes.delete();
	    archivoMenu.delete();
	    archivoCombos.delete();
	}

	@Test
	void testGuardarPedidoExcepcion(){
		
	try {
		restaurante.cerrarYGuardarPedido();
	} catch (NoHayPedidoEnCursoException e) {
		e.printStackTrace();
		
		assertEquals("Actualmente no hay un pedido en curso",e.getMessage());
	} catch(IOException e) {
	}
		    	
	}

}





