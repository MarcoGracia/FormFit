package es.mgj.base;


public class Clima {
	
	Localizacion localizacion;
	
	float temperaturaMaxima;
	
	float temepraturaMinima;
	
	float temperatura;
	
	String descripcion;
	
	String condicion;
	
	int humedad;
	
	int pression;
	
	float velocidadViento;
	
	float temperaturaViento;
	
	int porcentajeNubes;

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}

	public float getTemperaturaMaxima() {
		return temperaturaMaxima;
	}

	public void setTemperaturaMaxima(float temperaturaMaxima) {
		this.temperaturaMaxima = temperaturaMaxima;
	}

	public float getTemepraturaMinima() {
		return temepraturaMinima;
	}

	public void setTemepraturaMinima(float temepraturaMinima) {
		this.temepraturaMinima = temepraturaMinima;
	}

	public float getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(float temperatura) {
		this.temperatura = temperatura;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getHumedad() {
		return humedad;
	}

	public void setHumedad(int humedad) {
		this.humedad = humedad;
	}

	public int getPression() {
		return pression;
	}

	public void setPression(int pression) {
		this.pression = pression;
	}

	public float getVelocidadViento() {
		return velocidadViento;
	}

	public void setVelocidadViento(float velocidadViento) {
		this.velocidadViento = velocidadViento;
	}

	public float getTemperaturaViento() {
		return temperaturaViento;
	}

	public void setTemperaturaViento(float temperaturaViento) {
		this.temperaturaViento = temperaturaViento;
	}

	public int getPorcentajeNubes() {
		return porcentajeNubes;
	}

	public void setPorcentajeNubes(int porcentajeNubes) {
		this.porcentajeNubes = porcentajeNubes;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	
}
