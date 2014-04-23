package main.java.winningBet;

/**
 * Esta clase representa una apuesta del sistema de apuestas. Una apuesta tiene
 * un usuario, el que realiza la apuesta, y una "elección" (la apuesta en sí).
 * La elección consiste de una lista que define el orden que el apostador supone
 * que tendrán los equipos en la tabla de posiciones final. Los equipos son
 * representados por valores numéricos (e.g., 1..20).
 * 
 * @author Nazareno Aguirre
 * @version 0.1 14/04/2014
 */
public class Apuesta {
	
	private String usuario;
	private int nEquipos;
	private int[] posiciones;
	
	/**
	 * Constructor que crea una apuesta vacía, dado el nombre del apostador.
	 * 
	 * @param usuario
	 *            es el nombre del apostador.
	 */
	public Apuesta(String usuario) {
		if(usuario == null) throw new IllegalArgumentException("El nombre del apostador no puede ser nulo");
		this.usuario = usuario;
		this.nEquipos = 0;
		this.posiciones = null;
	}

	/**
	 * Constructor que crea una apuesta, dados el nombre del apostador, número
	 * de equipos y apuesta. La longitud de la apuesta debe coincidir con el
	 * número de equipos.
	 * 
	 * @param usuario
	 *            es el nombre del apostador.
	 * @param NroEquipos
	 *            es el número de equipos en la apuesta.
	 * @param posiciones
	 *            es la apuesta.
	 */
	public Apuesta(String usuario, int NroEquipos, int[] posiciones) {
		if(usuario == null) throw new IllegalArgumentException("El nombre del apostador no puede ser nulo");
		if(posiciones == null) throw new IllegalArgumentException("Las posiciones no pueden ser nulas");
		if(NroEquipos != posiciones.length) throw new IllegalArgumentException("El número de equipos debe coincidir con la cantidad de posiciones");
		this.usuario = usuario;
		this.nEquipos = NroEquipos;
		this.posiciones = posiciones;
	}

	/**
	 * Cambia el número de equipos de la apuesta.
	 * 
	 * @param es
	 *            el nuevo número de equipos de la apuesta
	 */
	public void cambiarNroEquipos(int i) {
		this.nEquipos = i;
	}

	/**
	 * Número de equipos de la apuesta.
	 * 
	 * @return el número de equipos de la apuesta.
	 */
	public int nroEquipos() {
		return this.nEquipos;
	}

	/**
	 * Posiciones definidas por el usuario para su apuesta.
	 * 
	 * @return el orden de los equipos seleccionado por el usuario en su
	 *         apuesta.
	 */
	public int[] posiciones() {
		return this.posiciones;
	}

	/**
	 * Cambia el orden establecido por el usuario para su apuesta.
	 * 
	 * @param posiciones
	 *            es el nuevo orden establecido por el usuario para los equipos
	 *            del torneo.
	 */
	public void cambiarPosiciones(int[] posiciones) {
		this.posiciones = posiciones;
	}

	/**
	 * Nombre del usuario, i.e, del cliente que emite la apuesta.
	 * 
	 * @return el nombre del usuario que emite la apuesta.
	 */
	public String usuario() {
		return this.usuario;
	}
}
