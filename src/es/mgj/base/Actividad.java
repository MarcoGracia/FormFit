package es.mgj.base;

import java.io.Serializable;

public class Actividad implements Serializable{

	private int id;
	private String fecha;
	private Float distanciaRecorrida;
	private Float caloriasQuemadas;
	private String nombre;
	private Float duracion;
	private byte[] mapa;
	private String tipo;
	
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Float getDistanciaRecorrida() {
		return distanciaRecorrida;
	}
	public void setDistanciaRecorrida(Float distanciaRecorrida) {
		this.distanciaRecorrida = distanciaRecorrida;
	}
	public Float getCaloriasQuemadas() {
		return caloriasQuemadas;
	}
	public void setCaloriasQuemadas(Float caloriasQuemadas) {
		this.caloriasQuemadas = caloriasQuemadas;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Float getDuracion() {
		return duracion;
	}
	public void setDuracion(Float duracion) {
		this.duracion = duracion;
	}
	public byte[] getMapa() {
		return mapa;
	}
	public void setMapa(byte[] mapa) {
		this.mapa = mapa;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
