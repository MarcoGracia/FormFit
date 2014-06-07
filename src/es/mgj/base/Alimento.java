package es.mgj.base;

import java.io.Serializable;

public class Alimento implements Serializable {
	private String nombre;
	private double calorias;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public double getCalorias() {
		return calorias;
	}
	public void setCalorias(double calorias) {
		this.calorias = calorias;
	}
	
	
}
