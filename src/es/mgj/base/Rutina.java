package es.mgj.base;

import java.io.Serializable;

public class Rutina implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;
	
	private int repeticiones;
	
	private float duracionFase1;
	
	private float duracionFase2;
	
	private float duracionFase3;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}

	public float getDuracionFase1() {
		return duracionFase1;
	}

	public void setDuracionFase1(float duracionFase1) {
		this.duracionFase1 = duracionFase1;
	}

	public float getDuracionFase2() {
		return duracionFase2;
	}

	public void setDuracionFase2(float duracionFase2) {
		this.duracionFase2 = duracionFase2;
	}

	public float getDuracionFase3() {
		return duracionFase3;
	}

	public void setDuracionFase3(float duracionFase3) {
		this.duracionFase3 = duracionFase3;
	}
	
	@Override
	public String toString() {
	    return this.nombre;
	}
	
	
}
