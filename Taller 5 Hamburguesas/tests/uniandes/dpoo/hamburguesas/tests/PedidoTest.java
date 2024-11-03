package uniandes.dpoo.hamburguesas.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import uniandes.dpoo.hamburguesas.mundo.Pedido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest {
    
    private Pedido pedido;

    @BeforeEach
    void setUp() throws Exception {
        // Crear un pedido con un cliente de prueba
        pedido = new Pedido("Cliente de Prueba", "Calle Falsa 123");

        // Crear productos para agregar al pedido
        ProductoMenu pizzaMargarita = new ProductoMenu("Pizza Margarita", 400);
        ProductoAjustado pizzaAjustada = new ProductoAjustado(pizzaMargarita);
        pizzaAjustada.agregarIngrediente(new Ingrediente("Queso Extra", 50));
        
        ProductoMenu pizzaHawai = new ProductoMenu("Pizza Hawai", 450);
        ProductoAjustado pizzaAjustada2 = new ProductoAjustado(pizzaHawai);
        pizzaAjustada2.agregarIngrediente(new Ingrediente("Piña", 30));
        
        // Agregar productos al pedido
        pedido.agregarProducto(pizzaAjustada);
        pedido.agregarProducto(pizzaAjustada2);
    }

    @AfterEach
    void tearDown() throws Exception {
        pedido = null;
    }
    
    @Test
    void testGetIdPedido() {
        int idEsperado = Pedido.getNumeroPedidos()-1; // Suponemos que el número de pedido aumenta con cada creación
        assertEquals(idEsperado, pedido.getIdPedido(), "El ID del pedido no es el esperado.");
    }

    @Test
    void testGetNombreCliente() {
        assertEquals("Cliente de Prueba", pedido.getNombreCliente(), "El nombre del cliente no es el esperado.");
    }
    @Test
    void testGetPrecioTotalPedido() {
        int precioNetoEsperado = (400 + 50) + (450 + 30);
        int ivaEsperado = (int) (precioNetoEsperado * 0.19);
        int precioTotalEsperado = precioNetoEsperado + ivaEsperado;

        assertEquals(precioTotalEsperado, pedido.getPrecioTotalPedido(), "El precio total del pedido no es el esperado.");
    }

    @Test
    void testGenerarTextoFactura() {
        String textoFactura = pedido.generarTextoFactura();

        assertTrue(textoFactura.contains("Cliente: Cliente de Prueba"), "El nombre del cliente no está en la factura.");
        assertTrue(textoFactura.contains("Dirección: Calle Falsa 123"), "La dirección del cliente no está en la factura.");
        assertTrue(textoFactura.contains("Pizza Margarita"), "El nombre del producto no está en la factura.");
        assertTrue(textoFactura.contains("Precio Total"), "La sección de precio total no está en la factura.");
    }

    @Test
    void testGuardarFactura() throws FileNotFoundException {
        File archivoFactura = new File("factura_test.txt");
        pedido.guardarFactura(archivoFactura);

        // Leer el archivo para verificar que se guardó correctamente
        try (Scanner scanner = new Scanner(archivoFactura)) {
            assertTrue(scanner.hasNextLine(), "El archivo de la factura está vacío.");
        } finally {
            archivoFactura.delete(); // Elimina el archivo de prueba
        }
    }
}

