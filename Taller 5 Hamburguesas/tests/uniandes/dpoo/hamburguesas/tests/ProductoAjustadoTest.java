package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest {

    private ProductoAjustado productoAjustado;
    private ProductoMenu productoMenu;

    @BeforeEach
    void setUp() throws Exception {
        Ingrediente quesoExtra = new Ingrediente("Queso Extra", 50);
        Ingrediente pepperoni = new Ingrediente("Pepperoni", 30);
        Ingrediente champiñones = new Ingrediente("Champiñones", 20);
        
        productoMenu = new ProductoMenu("Pizza Margarita", 400);
        productoAjustado = new ProductoAjustado(productoMenu);

     
        productoAjustado.agregarIngrediente(quesoExtra);
        productoAjustado.agregarIngrediente(pepperoni);
        productoAjustado.agregarIngrediente(quesoExtra); 

       
        productoAjustado.eliminarIngrediente(champiñones);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testGetNombre() {
        assertEquals("Pizza Margarita", productoAjustado.getNombre(), "El nombre del producto no es el esperado.");
    }

    @Test
    void testGetPrecio() {
       
        int precioEsperado = 400 + 50 + 30 + 50; 
        assertEquals(precioEsperado, productoAjustado.getPrecio(), "El precio Ajustado del producto no es el esperado.");
    }
    
    @Test 
    void testGenerarTextoFactura() {
    	String facturaEsperada =
    			"Pizza Margarita" + 
                "    +Queso Extra                50" +
                "    +Pepperoni                30" +
                "    +Queso Extra                50" +
                "    -Champiñones" +
                "            530\n";
    	assertEquals(facturaEsperada,productoAjustado.generarTextoFactura(),"La factura no es la esperada." );
    }
    
}    
