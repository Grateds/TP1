package main.java.winningBet;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa una colección de apuestas, para un sistema de apuestas futbolísticas simples.
 * Los equipos son identificados por valores numéricos (e.g., 1..20). El sistema de apuestas consta de
 * un conjunto de apuestas, y las posiciones finales (tabla de posiciones al finalizar el campeonato)
 * Cada apuesta consiste de un usuario y el orden en que el usuario considera que los equipos quedarán
 * al finalizar el campeonato. 
 * El sistema permite calcular los ganadores, es decir, aquellos cuya elección se diferencie en menor medida
 * respecto de la tabla de posiciones final (la "distancia" de una apuesta respecto de la tabla de 
 * posiciones final se mide en términos de cantidad de "inversiones" de la apuesta con respecto a la tabla
 * de posiciones obtenida).
 * 
 * @author Nazareno Aguirre
 * @version 0.1 14/04/2014
 */
public class ColeccionApuestas {

	private int nroEquipos;
	private int[] posiciones;
	private ArrayList<Apuesta> apuestas;
	private ArrayList<String> usuarios;
	private List<String> ganadores;
	
	/**
	 * Constructor por defecto. Setea el número de equipos en 2 (el mínimo posible). 
	 */
	public ColeccionApuestas() {
		this.apuestas = new ArrayList<Apuesta>();
		this.usuarios = new ArrayList<String>();
		this.nroEquipos = 2;
		this.ganadores = null;
	}

	
	/**
	 * Constructor que toma como parámetro el número de equipos de las apuestas.
	 * @param nroEquipos 
	 * 				es el número de equipos del campeonato.
	 */
	public ColeccionApuestas(int nroEquipos) {
		if(Apuesta.nroEquipos != nroEquipos) throw new IllegalArgumentException("Número de equipos en apuesta no coincide con coleccion apuestas");
		this.apuestas = new ArrayList<Apuesta>();
		this.usuarios = new ArrayList<String>();
		this.nroEquipos = nroEquipos;
		this.ganadores = null;
	}

	/**
	 * Número de apuestas registradas en el sistema.
	 * @return número de apuestas registradas en el sistema.
	 */
	public int numApuestas() {
		return this.apuestas.size();
	}

	/**
	 * Permite registrar una nueva apuesta en el sistema. Debe corresponder a un usuario no registrado 
	 * previamente, es decir, que no tenga una apuesta previa en el sistema (cada usuario puede apostar
	 * exactamente una vez).
	 * @param apuesta 
	 * 			es la apuesta a agregar en el sistema. 
	 */
	public void agregar(Apuesta apuesta) {
		if (usuarios.contains(apuesta.usuario())) throw new IllegalArgumentException ("Cada usuario puede apostar solo una vez");
		this.apuestas.add(apuesta);
		this.usuarios.add(apuesta.usuario());
	}

	/**
	 * Cambia el número de equipos participantes en el campeonato. Sólo puede cambiarse si aún no hay
	 * apuestas registradas.
	 * @param i 
	 * 		es el nuevo nímero de equipos en el campeonato.
	 */
	public void cambiarNroEquipos(int i) {
		if(this.apuestas.size()>0) throw new IllegalStateException("No es posible cambiar el numero de equipos en colección no vacía");
		this.nroEquipos = i;
	}

	/**
	 * Establece cuáles son las posiciones de los equipos en la tabla de posiciones final. Una vez
	 * establecida la tabla de posiciones final, se puede calcular el o los ganadores.
	 * @param posiciones es la tabla de posiciones final del torneo.
	 */
	public void establecerPosicionesFinales(int[] posiciones) {
		if(posiciones == null) throw new IllegalArgumentException("Las posiciones finales no pueden ser nulas!");
		if(this.nroEquipos != posiciones.length) throw new IllegalArgumentException("Número de equipos en posiciones finales no coinciden con los del sistema de apuestas");
		this.posiciones = posiciones;
	}
	
	/**
	 * Retorna la lista de ganadores en el sistema. Si no hay apuestas, retorna la lista vacía.
	 * Si no se invocó previamente a calcularGanadores, lanza una excepción.
	 * @return la lista de ganadores del sistema.
	 */
	public List<String> ganadores() {
		if(this.ganadores == null) throw new IllegalStateException("Deben computarse los ganadores antes de obtenerlos");
		return this.ganadores;
	}

	/**
	 * Calcula los apostadores ganadores del sistema. Para poder computar apuestas, se debe contar
	 * con la tabla de posiciones final ya establecida en el sistema. No es necesario que haya apuestas
	 * registradas. Si no hay apuestas, no habrá ganadores. Los ganadores se obtienen con la rutina
	 * ganadores() (lista de ganadores, vacía si no hubo apuestas).
	 */
	public void calcularGanadores() {
		this.ganadores = new ArrayList<String>();
	}
}
