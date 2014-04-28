package main.java.winningBet;

import java.util.ArrayList;
import java.util.Arrays;
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
	private int[] posicionesFinales;
	private ArrayList<Apuesta> apuestas;
	private ArrayList<String> usuarios;
	private List<String> ganadores;
	private int banderaGanadores;

	/**
	 * Constructor por defecto. Setea el número de equipos en 2 (el mínimo posible). 
	 */
	public ColeccionApuestas() {
		this.apuestas = new ArrayList<Apuesta>();
		this.usuarios = new ArrayList<String>();
		this.nroEquipos = 2;
		this.ganadores = null;
		this.banderaGanadores = 0;
	}

	/**
	 * Constructor que toma como parámetro el número de equipos de las apuestas.
	 * @param nroEquipos, es el número de equipos del campeonato.				
	 */
	public ColeccionApuestas(int nroEquipos) {
		this.apuestas = new ArrayList<Apuesta>();
		this.usuarios = new ArrayList<String>();
		this.nroEquipos = nroEquipos;
		this.ganadores = null;
		this.banderaGanadores = 0;
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
	 * @param apuesta,	es la apuesta a agregar en el sistema.	 
	 */
	public void agregar(Apuesta apuesta) {
		if (apuesta.nroEquipos() != this.nroEquipos) throw new IllegalArgumentException("Número de equipos en apuesta no coincide con coleccion apuestas");
		if (usuarios.contains(apuesta.usuario())) throw new IllegalArgumentException ("Cada usuario puede apostar solo una vez");
		this.apuestas.add(apuesta);
		this.usuarios.add(apuesta.usuario());
	}

	/**
	 * Cambia el número de equipos participantes en el campeonato. Sólo puede cambiarse si aún no hay
	 * apuestas registradas.
	 * @param i, es el nuevo nímero de equipos en el campeonato.
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
		if (posiciones == null) throw new IllegalArgumentException("Las posiciones finales no pueden ser nulas!");
		if (posiciones.length != this.nroEquipos) throw new IllegalArgumentException("Número de equipos en posiciones finales no coinciden con los del sistema de apuestas");
		this.posicionesFinales = posiciones;
	}
	
	/**
	 * Retorna la lista de ganadores en el sistema. Si no hay apuestas, retorna la lista vacía.
	 * Si no se invocó previamente a calcularGanadores, lanza una excepción.
	 * @return la lista de ganadores del sistema.
	 */
	public List<String> ganadores() {
		if(this.banderaGanadores == 0) throw new IllegalStateException("Deben computarse los ganadores antes de obtenerlos");
		return this.ganadores;
	}

	/**
	 * Calcula los apostadores ganadores del sistema. Para poder computar apuestas, se debe contar
	 * con la tabla de posiciones final ya establecida en el sistema. No es necesario que haya apuestas
	 * registradas. Si no hay apuestas, no habrá ganadores. Los ganadores se obtienen con la rutina
	 * ganadores() (lista de ganadores, vacía si no hubo apuestas).
	 */
	public void calcularGanadores() {
		this.banderaGanadores = 1;
		this.ganadores = new ArrayList<String>();
		if (this.apuestas.size() > 0) {
			int[] aux = new int[21];
			for (int i = 0; i < this.posicionesFinales.length; i++) 
				aux[this.posicionesFinales[i]] = i;
			int minInv = this.countInversion(this.apuestas.get(0).posiciones(), aux);
			this.ganadores.add(this.apuestas.get(0).usuario());
			int count;
			
			for (int i = 1; i <	 this.apuestas.size(); i++) {
				count = this.countInversion(this.apuestas.get(i).posiciones(), aux);
				if (count < minInv) {
					minInv = count;
					this.ganadores = new ArrayList<String>();
					this.ganadores.add(this.apuestas.get(i).usuario());
				}
				else if (count == minInv) this.ganadores.add(this.apuestas.get(i).usuario());
			}
		}
	}
	

	/**
	 * Cuenta el número de inversiones utilizando divide y vencerás en tiempo nlogn
	 * @param arr
	 * @param order
	 * @return countLeft + countRight + countSplit
	 */
    public int countInversion(int[] arr, int[] order) {
        int len = arr.length;
        if (len < 2)
            return 0;
        else {
            int middle = len / 2;
            int[] left = Arrays.copyOfRange(arr, 0, middle);
            int[] right = Arrays.copyOfRange(arr, middle, len);

            int countLeft = countInversion(left,order);
            int countRight = countInversion(right,order);

            int[] result = new int[len];
            int countSplit = countSplitInversion(left, right, order, result);
            
            return countLeft + countRight + countSplit;
        }
    }

    /**
     * @param left
     * @param right
     * @param order
     * @param result
     * @return count
     */
    private int countSplitInversion(int[] left, int[] right, int[] order, int[] result) {
        int i = 0, j = 0, k = 0;
        int count = 0;
        while (i < left.length && j < right.length) {
            if (order[left[i]] <= order[right[j]])
                result[k++] = left[i++];
            else {
                result[k++] = right[j++];
                count += left.length - i;
            }
        }
        return count; 
    }	
}
