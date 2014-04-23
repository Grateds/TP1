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
	public static int nroEquipos;
	private int[] posiciones;
		
	/**
	 * Constructor que crea una apuesta vacía, dado el nombre del apostador.
	 * 
	 * @param usuario
	 *            es el nombre del apostador.
	 */
	public Apuesta(String usuario) {
		if (usuario == null) throw new IllegalArgumentException("El Usuario no puede ser nulo"); 
		this.usuario = usuario;
		Apuesta.nroEquipos = 0;
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

		if (usuario == null) throw new IllegalArgumentException("El Usuario no puede ser nulo");
		if (posiciones == null) throw new IllegalArgumentException("La lista de posiciones deben ser nula");
		if (NroEquipos != posiciones.length) throw new IllegalArgumentException("El numero de equipos debe coincidir con la cantidad de posiciones");
		this.usuario = usuario;
		Apuesta.nroEquipos = NroEquipos;
		this.posiciones = posiciones;
	}

	/**
	 * Cambia el número de equipos de la apuesta.
	 * 
	 * @param es
	 *            el nuevo número de equipos de la apuesta
	 */
	public void cambiarNroEquipos(int i) {
		Apuesta.nroEquipos = i;
	}

	/**
	 * Número de equipos de la apuesta.
	 * 
	 * @return el número de equipos de la apuesta.
	 */
	public int nroEquipos() {
		return Apuesta.nroEquipos;
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
