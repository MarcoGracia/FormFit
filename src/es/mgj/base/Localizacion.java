package es.mgj.base;

public class Localizacion {
	
	float latitud;
	
	float longitud;
	
	String pais;
	
	String ciudad;
	
	int amanecer;
	
	int anochecer;

	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public int getAmanecer() {
		return amanecer;
	}

	public void setAmanecer(int amanecer) {
		this.amanecer = amanecer;
	}

	public int getAnochecer() {
		return anochecer;
	}

	public void setAnochecer(int anochecer) {
		this.anochecer = anochecer;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	
	
}
