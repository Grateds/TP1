package test.java.winningBet;

import static org.junit.Assert.*;
import main.java.winningBet.*;

import org.junit.Test;

/**
 * Esta clase contiene tests para todas las rutinas públicas de  las clases que participan
 * de la implementación del sistema de apuestas de fútbol. 
 * @author Nazareno Aguirre
 * @version 0.1 14/04/2014
 */
public class TestsApuestasFutbol {
	
	@Test
	public void crearApuestaSimple() {
		Apuesta apuesta = new Apuesta("usuario 1");
		apuesta.cambiarNroEquipos(3);
		int[] posiciones = {1,2,3};
		apuesta.cambiarPosiciones(posiciones);
		assertTrue(apuesta.nroEquipos()==3);
		assertEquals("usuario 1", apuesta.usuario());
		assertEquals(posiciones, apuesta.posiciones());
	}

	@Test(expected=IllegalArgumentException.class)
	public void crearApuestaInvalida() { 
		new Apuesta(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void crearApuestaInvalidaConstructorComplejo() {
		int[] posiciones = {1,2,3};
		new Apuesta(null, 3, posiciones);
	}

	
	@Test
	public void crearApuestaSimpleViaConstructor() {
		int[] posiciones = {1,2,3};
		Apuesta apuesta = new Apuesta("usuario 1", 3, posiciones);
		assertTrue(apuesta.nroEquipos()==3);
		assertEquals("usuario 1", apuesta.usuario());
		assertEquals(posiciones, apuesta.posiciones());
	}

	@Test(expected=IllegalArgumentException.class)
	public void crearApuestaInvalidaViaConstructor() {
		int[] posiciones = {1,2,3};
		new Apuesta("usuario 1", 4, posiciones);
		// Debe romperse!
		// Nro. de equipos debe coincidir con long. de posiciones.
	}

	@Test(expected=IllegalArgumentException.class)
	public void crearApuestaInvalidaViaConstructorNullPos() {
		new Apuesta("usuario 1", 0, null);
		// Debe romperse!
		// posiciones no puede ser nulo
	}

	
	@Test
	public void sinApuestasIniciales() {
		ColeccionApuestas apuestas = new ColeccionApuestas();
		assertTrue("Sin apuestas al comienzo", apuestas.numApuestas()==0);
	}
	
	@Test
	public void agregandoUnaApuesta() {
		int[] posiciones = {3,2,1};
		Apuesta apuesta = new Apuesta("usuario 1", 3, posiciones);
		ColeccionApuestas apuestas = new ColeccionApuestas(3);
		apuestas.agregar(apuesta);
		assertTrue("bet has been added", apuestas.numApuestas()==1);
	}
	
	@Test
	public void agregandoUnaApuestaConModifNroDeEquipos() {
		int[] posiciones = {3,2,1};
		Apuesta apuesta = new Apuesta("usuario 1", 3, posiciones);
		ColeccionApuestas apuestas = new ColeccionApuestas();
		apuestas.cambiarNroEquipos(3);
		apuestas.agregar(apuesta);
		assertTrue("bet has been added", apuestas.numApuestas()==1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void agregandoUnaApuestaInvalida() {
		int[] posiciones = {3,2,1};
		Apuesta apuesta = new Apuesta("usuario 1", 3, posiciones);
		ColeccionApuestas apuestas = new ColeccionApuestas(4);
		apuestas.agregar(apuesta);
		// debe fallar! Nro. de equipos en apuesta no coincide con coleccion apuestas
	}

	@Test(expected=IllegalStateException.class)
	public void cambiandoNroEquiposColNoVacia() {
		int[] posiciones = {3,2,1};
		Apuesta apuesta = new Apuesta("usuario 1", 3, posiciones);
		ColeccionApuestas apuestas = new ColeccionApuestas(3);
		apuestas.agregar(apuesta);
		apuestas.cambiarNroEquipos(4);
		// Debe fallar! Colección no vacía, y se quiere cambiar el
		// nro. de equipos.
	}

	@Test
	public void agregandoVariasApuestas() {
		int[] posiciones1 = {3,2,1};
		Apuesta apuesta1 = new Apuesta("usuario 1", 3, posiciones1);
		int[] posiciones2 = {3,1,2};
		Apuesta apuesta2 = new Apuesta("usuario 2", 3, posiciones2);
		int[] posiciones3 = {3,1,2};
		Apuesta apuesta3 = new Apuesta("usuario 3", 3, posiciones3);
		int[] posiciones4 = {3,2,1};
		Apuesta apuesta4 = new Apuesta("usuario 4", 3, posiciones4);
		int[] posiciones5 = {3,2,1};
		Apuesta apuesta5 = new Apuesta("usuario 5", 3, posiciones5);
		int[] posiciones6 = {3,2,1};
		Apuesta apuesta6 = new Apuesta("usuario 6", 3, posiciones6);
		int[] posiciones7 = {1,2,3};
		Apuesta apuesta7 = new Apuesta("usuario 7", 3, posiciones7);
		int[] posiciones8 = {3,2,1};
		Apuesta apuesta8 = new Apuesta("usuario 8", 3, posiciones8);
		int[] posiciones9 = {3,2,1};
		Apuesta apuesta9 = new Apuesta("usuario 9", 3, posiciones9);
		int[] posiciones10 = {1,2,3};
		Apuesta apuesta10 = new Apuesta("usuario 10", 3, posiciones10);		
		
		ColeccionApuestas apuestas = new ColeccionApuestas(3);
		apuestas.agregar(apuesta1);
		apuestas.agregar(apuesta2);
		apuestas.agregar(apuesta3);
		apuestas.agregar(apuesta4);
		apuestas.agregar(apuesta5);
		apuestas.agregar(apuesta6);
		apuestas.agregar(apuesta7);
		apuestas.agregar(apuesta8);
		apuestas.agregar(apuesta9);
		apuestas.agregar(apuesta10);
		assertEquals(10, apuestas.numApuestas());
	}

	@Test(expected=IllegalArgumentException.class)
	public void agregandoApuestasUsuarioRepetido() {
		int[] posiciones1 = {3,2,1};
		Apuesta apuesta1 = new Apuesta("usuario 1", 3, posiciones1);
		int[] posiciones2 = {3,1,2};
		Apuesta apuesta2 = new Apuesta("usuario 1", 3, posiciones2);
		ColeccionApuestas apuestas = new ColeccionApuestas(3);
		apuestas.agregar(apuesta1);
		apuestas.agregar(apuesta2);
		// Debe fallar! Se agrega una apuesta de un usuario existente.
		// Cada usuario puede apostar sólo una vez.
	}

	@Test
	public void calculoGanadorPocasApuestas() {
		int[] posiciones1 = {3,2,1};
		Apuesta apuesta1 = new Apuesta("usuario 1", 3, posiciones1);
		int[] posiciones2 = {3,1,2};
		Apuesta apuesta2 = new Apuesta("usuario 2", 3, posiciones2);
		ColeccionApuestas apuestas = new ColeccionApuestas(3);
		apuestas.agregar(apuesta1);
		apuestas.agregar(apuesta2);
		apuestas.establecerPosicionesFinales(posiciones1);
		apuestas.calcularGanadores();	
		assertEquals(1, apuestas.ganadores().size());
		assertTrue(apuestas.ganadores().get(0)=="usuario 1");
	}

	@Test
	public void calculoGanadoresMuchasApuestas() {
		int[] posiciones1 = {3,2,1};
		Apuesta apuesta1 = new Apuesta("usuario 1", 3, posiciones1);
		int[] posiciones2 = {3,1,2};
		Apuesta apuesta2 = new Apuesta("usuario 2", 3, posiciones2);
		int[] posiciones3 = {3,1,2};
		Apuesta apuesta3 = new Apuesta("usuario 3", 3, posiciones3);
		int[] posiciones4 = {3,2,1};
		Apuesta apuesta4 = new Apuesta("usuario 4", 3, posiciones4);
		int[] posiciones5 = {3,2,1};
		Apuesta apuesta5 = new Apuesta("usuario 5", 3, posiciones5);
		int[] posiciones6 = {3,2,1};
		Apuesta apuesta6 = new Apuesta("usuario 6", 3, posiciones6);
		int[] posiciones7 = {1,2,3};
		Apuesta apuesta7 = new Apuesta("usuario 7", 3, posiciones7);
		int[] posiciones8 = {3,2,1};
		Apuesta apuesta8 = new Apuesta("usuario 8", 3, posiciones8);
		int[] posiciones9 = {3,2,1};
		Apuesta apuesta9 = new Apuesta("usuario 9", 3, posiciones9);
		int[] posiciones10 = {1,2,3};
		Apuesta apuesta10 = new Apuesta("usuario 10", 3, posiciones10);		
		
		ColeccionApuestas apuestas = new ColeccionApuestas(3);
		apuestas.agregar(apuesta1);
		apuestas.agregar(apuesta2);
		apuestas.agregar(apuesta3);
		apuestas.agregar(apuesta4);
		apuestas.agregar(apuesta5);
		apuestas.agregar(apuesta6);
		apuestas.agregar(apuesta7);
		apuestas.agregar(apuesta8);
		apuestas.agregar(apuesta9);
		apuestas.agregar(apuesta10);
		assertEquals(10, apuestas.numApuestas());	
		apuestas.establecerPosicionesFinales(posiciones10);
		apuestas.calcularGanadores();
		assertEquals(2, apuestas.ganadores().size());
		assertTrue(apuestas.ganadores().contains("usuario 10"));
		assertTrue(apuestas.ganadores().contains("usuario 7"));
	}

	@Test
	public void calculoGanadoresMuchasApuestasLargas() {     
		ColeccionApuestas apuestas = new ColeccionApuestas(20);
		for (int i=0; i<5000; i++) {
			int[] posiciones = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
			Apuesta apuesta = new Apuesta("usuario "+i, 20, posiciones);
			apuestas.agregar(apuesta);
		}
		assertEquals(5000, apuestas.numApuestas());
		int[] posiciones = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
		apuestas.establecerPosicionesFinales(posiciones);
		apuestas.calcularGanadores();
		assertEquals(5000, apuestas.ganadores().size());
		assertTrue(apuestas.ganadores().contains("usuario 10"));
		assertTrue(apuestas.ganadores().contains("usuario 7"));
	}

	@Test
	public void calculoGanadoresNadieLePega() {
		int[] posiciones1 = {4,3,2,1};
		Apuesta apuesta1 = new Apuesta("usuario 1", 4, posiciones1);
		int[] posiciones2 = {3,1,2,4};
		Apuesta apuesta2 = new Apuesta("usuario 2", 4, posiciones2);
		ColeccionApuestas apuestas = new ColeccionApuestas(4);
		apuestas.agregar(apuesta1);
		apuestas.agregar(apuesta2);
		int[] posicionesFinales = {1,2,3,4};
		apuestas.establecerPosicionesFinales(posicionesFinales);
		apuestas.calcularGanadores();	
		assertEquals(1, apuestas.ganadores().size());
		assertTrue(apuestas.ganadores().get(0)=="usuario 2");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void tablaFinalInvalida() {
		ColeccionApuestas apuestas = new ColeccionApuestas(4);
		apuestas.establecerPosicionesFinales(null);
		// Debe romperse. Las posiciones finales no pueden ser nulas!
	}

	@Test(expected=IllegalArgumentException.class)
	public void tablaFinalInvalidaDifNroEquipos() {
		ColeccionApuestas apuestas = new ColeccionApuestas(4);
		int[] posicionesFinales = {1,2,3};
		apuestas.establecerPosicionesFinales(posicionesFinales);
		// Debe romperse. Número de equipos en pos finales no coinciden con los del sistema de apuestas
	}

	@Test(expected=IllegalStateException.class)
	public void obtengoGanadoresSinComputarlos() {
		int[] posiciones1 = {4,3,2,1};
		Apuesta apuesta1 = new Apuesta("usuario 1", 4, posiciones1);
		ColeccionApuestas apuestas = new ColeccionApuestas(4);
		apuestas.agregar(apuesta1);
		apuestas.ganadores();
		// Debe romperse. Deben computarse los ganadores antes de obtenerlos.
	}

	@Test
	public void obtengoGanadoresSinApuestas() {
		ColeccionApuestas apuestas = new ColeccionApuestas(4);
		int[] posicionesFinales = {1,2,3,4};
		apuestas.establecerPosicionesFinales(posicionesFinales);
		apuestas.calcularGanadores();
		assertTrue("no hay ganadores", apuestas.ganadores().isEmpty());
	}

	@Test
	public void inversionesSimple() {
		ColeccionApuestas apuestas = new ColeccionApuestas(3);
		int posicionesFinales [] = {4,6,2};
		apuestas.establecerPosicionesFinales(posicionesFinales);	
		int [] posiciones1 = {2,4,6};
		int [] b = {0,0,2,0,0,0,1};
		assertEquals(2,apuestas.countInversion(posiciones1,b));
	}
	
	@Test
	public void inversionesComplejo() {
		ColeccionApuestas apuestas = new ColeccionApuestas(20);
		int posicionesFinales [] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};		
		apuestas.establecerPosicionesFinales(posicionesFinales);	
		int [] b = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};	
		assertEquals(0,apuestas.countInversion(posicionesFinales,b));
	}
}
