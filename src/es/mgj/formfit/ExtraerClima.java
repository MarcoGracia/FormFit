package es.mgj.formfit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import es.mgj.activitys.ActivityMostrarClima;
import es.mgj.base.Clima;
import es.mgj.base.Localizacion;

public class ExtraerClima extends AsyncTask<Void, Void, Void> {
	
	private String localizacion;
	private Clima clima;
	private boolean finished = false;
	private ActivityMostrarClima activityMostrarClima;
	
	public ExtraerClima(String localizacion, ActivityMostrarClima activityMostrarClima){
		this.activityMostrarClima = activityMostrarClima;
		this.localizacion = localizacion;
	}

	@Override
	protected Void doInBackground(Void... params) {

		clima = new Clima();
		String data = ( (new WeatherHttpClient()).getWeatherData(localizacion));
		
		try {
			leerJson(data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	@Override
	protected void onPostExecute(Void resultado) {
		super.onPostExecute(resultado);
		activityMostrarClima.cargarDatos();
		this.finished = true;
	}

	private void leerJson(String data) throws JSONException {
		
		JSONObject jObj = new JSONObject(data);
		
		JSONObject coordObj = getObject("coord", jObj);
		
		Localizacion loc = new Localizacion();
		
		loc.setLatitud(getFloat("lat", coordObj));
		
		loc.setLongitud(getFloat("lon", coordObj));
		
		JSONObject sysObj = getObject("sys", jObj);	
		
		loc.setPais(getString("country", sysObj));
		loc.setAmanecer(getInt("sunrise", sysObj));
		loc.setAnochecer(getInt("sunset", sysObj));
		loc.setCiudad(getString("name", jObj));
		
		this.clima.setLocalizacion(loc);
		
		JSONArray jArr = jObj.getJSONArray("weather");
		
		JSONObject JSONWeather = jArr.getJSONObject(0);
		
		this.clima.setDescripcion(getString("description", JSONWeather));
		this.clima.setCondicion(getString("main", JSONWeather));
		 
		JSONObject mainObj = getObject("main", jObj);
		this.clima.setHumedad(getInt("humidity", mainObj));
		this.clima.setPression(getInt("pressure", mainObj));
		this.clima.setTemperaturaMaxima(getFloat("temp_max", mainObj));
		this.clima.setTemepraturaMinima(getFloat("temp_min", mainObj));
		this.clima.setTemperatura(getFloat("temp", mainObj));
		 
		// Wind
		JSONObject wObj = getObject("wind", jObj);
		this.clima.setVelocidadViento(getFloat("speed", wObj));
		this.clima.setTemperaturaViento(getFloat("deg", wObj));
		 
		// Clouds
		JSONObject cObj = getObject("clouds", jObj);
		this.clima.setPorcentajeNubes(getInt("all", cObj));
			
	}
	
	private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
	    JSONObject subObj = jObj.getJSONObject(tagName);
	    return subObj;
	}
	 
	private static String getString(String tagName, JSONObject jObj) throws JSONException {
	    return jObj.getString(tagName);
	}
	 
	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
	    return (float) jObj.getDouble(tagName);
	}
	 
	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
	    return jObj.getInt(tagName);
	}

	public Clima getClima() {
		return clima;
	}
	
	
	
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	


}
