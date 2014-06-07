package es.mgj.base;

import java.util.ArrayList;

public class TipoAlimento {
	
	private String nombre;
	
	private ArrayList<Alimento> listaAlimentos;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Alimento> getListaAlimentos() {
		return listaAlimentos;
	}
	public void setListaAlimentos(ArrayList<Alimento> listaAlimentos) {
		this.listaAlimentos = listaAlimentos;
	}
	
	
}
