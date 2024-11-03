package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest {
	
	
	
	private Combo combo;

    @BeforeEach
    void setUp( ) throws Exception
    {
    	combo = new Combo( "Combo Paradisiaco", 0.8 );
    	 	
    	//primer producto
    	    	
    	Ingrediente quesoExtra = new Ingrediente("Queso Extra", 50);
        Ingrediente pepperoni = new Ingrediente("Pepperoni", 30);
        Ingrediente champiñones = new Ingrediente("Champiñones", 20);
        
        ProductoMenu productoMenu1 = new ProductoMenu("Pizza Margarita", 400);
        ProductoAjustado productoAjustado1 = new ProductoAjustado(productoMenu1);

     
        productoAjustado1.agregarIngrediente(quesoExtra);
        productoAjustado1.agregarIngrediente(pepperoni);
        productoAjustado1.agregarIngrediente(quesoExtra); 

       
        productoAjustado1.eliminarIngrediente(champiñones);
    	
    	//segundo Producto
    	
        Ingrediente pinia = new Ingrediente("Pinia", 50);
        Ingrediente pollo = new Ingrediente("Pollo", 30);
        Ingrediente jamon = new Ingrediente("Jamon", 20);
        
        ProductoMenu productoMenu = new ProductoMenu("Pizza Hawai", 500);
        ProductoAjustado productoAjustado = new ProductoAjustado(productoMenu);

     
        productoAjustado.agregarIngrediente(quesoExtra);
        productoAjustado.agregarIngrediente(pepperoni);
        productoAjustado.agregarIngrediente(quesoExtra); 

       
        productoAjustado.eliminarIngrediente(champiñones);
        
        
        combo.agregarProducto(productoAjustado);
        combo.agregarProducto(productoAjustado1);
    	
    	// combo
        
    }
    
    @AfterEach
    void tearDown( ) throws Exception
    {
    }
    
    @Test
    void testGetNombre() {
        assertEquals("Combo Paradisiaco", combo.getNombre(), "El nombre del producto no es el esperado.");
    }
    
    @Test
    void testGetPrecio() {
        
        int precioTotalSinDescuento = 400 + 500;
        int precioEsperadoConDescuento = (int) (precioTotalSinDescuento * 0.8);
        assertEquals(precioEsperadoConDescuento, combo.getPrecio(), "El precio del combo no es el esperado.");
    }
    
    @Test
    void testGenerarTextoFactura() {

    	int precioTotalSinDescuento = 400 + 500;
        int precioConDescuento = (int) (precioTotalSinDescuento * 0.8);
        String facturaEsperada = "Combo Combo Paradisiaco\n" +
                                 " Descuento: 0.8\n" +
                                 "            " + precioConDescuento + "\n";

        assertEquals(facturaEsperada, combo.generarTextoFactura(), "El texto de la factura no es el esperado.");
    }
}
