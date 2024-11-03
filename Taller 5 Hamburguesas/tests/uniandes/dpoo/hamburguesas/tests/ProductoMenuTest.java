package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {

	private ProductoMenu productoMenu;

    @BeforeEach
    void setUp( ) throws Exception
    {
    	productoMenu = new ProductoMenu( "pizza", 30000 );
    	
    }
    
    @AfterEach
    void tearDown( ) throws Exception
    {
    }
    
    @Test
    void testGetNombre( )
    {
        assertEquals( "pizza", productoMenu.getNombre( ), "El nombre del producto no es el esperado." );
    }

    @Test
    void testGetCostoAdicional( )
    {
        assertEquals( 30000, productoMenu.getPrecio( ), "El Precio del producto no es el esperado." );
    }
    
    @Test
    void testGenerarTextoFactura() {
    	
    	String esperado = "pizza\n" + "            30000\n";
        assertEquals(esperado, productoMenu.generarTextoFactura());
    	
    }
	
}
