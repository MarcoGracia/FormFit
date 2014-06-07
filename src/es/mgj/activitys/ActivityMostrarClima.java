package es.mgj.activitys;

import java.text.DecimalFormat;

import es.mgj.base.Clima;
import es.mgj.formfit.ExtraerClima;
import es.mgj.formfit.R;
import es.mgj.formfit.R.id;
import es.mgj.formfit.R.layout;
import es.mgj.formfit.R.menu;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class ActivityMostrarClima extends Activity {
	
	private Clima clima;
	private ImageView image;
	private TextView tvTMaxima;
	private TextView tvTMinima;
	private TextView tvTViento;
	private TextView tvTNubes;
	private TextView tvTDescripcion;
	private ExtraerClima eClima;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity_mostrar_clima);
		
		eClima = new ExtraerClima("Zaragoza,Spain", this);
		
		eClima.execute();

		image = (ImageView) findViewById(R.id.ivIconoClima);
		
		this.tvTDescripcion = (TextView) this.findViewById(R.id.tvDescripcionClima);
		this.tvTMaxima = (TextView) this.findViewById(R.id.tvTemperaturaMaxima);
		this.tvTMinima = (TextView) this.findViewById(R.id.tvTemperaturaMinima);
		this.tvTNubes = (TextView) this.findViewById(R.id.tvNubes);
		this.tvTViento = (TextView) this.findViewById(R.id.tvViento);
		
	}
	

	public void cargarDatos() {
		
		clima = eClima.getClima();
		
		this.tvTDescripcion.setText(this.clima.getDescripcion());
		this.tvTMaxima.setText(String.valueOf(new DecimalFormat("#.##").format(clima.getTemperaturaMaxima() - 273.0)) + "Cº");
		this.tvTMinima.setText(String.valueOf(new DecimalFormat("#.##").format(clima.getTemepraturaMinima() - 273.0))+ "Cº");
		this.tvTNubes.setText(String.valueOf("Clouds: " + clima.getPorcentajeNubes() + "%" ));
		this.tvTViento.setText(String.valueOf("Wind speed: " + clima.getVelocidadViento() +" km/h"));
		
		if(clima.getDescripcion().equals("sky is clear")){
			
			this.image.setImageResource(R.drawable.weezle_sun);
			
		}else if(clima.getDescripcion().equals("few clouds")){
			
			this.image.setImageResource(R.drawable.weezle_minimal_cloud);
			
		}else if(clima.getDescripcion().equals("scattered clouds")){
			
			this.image.setImageResource(R.drawable.weezle_medium_cloud);
			
		}else if(clima.getDescripcion().equals("broken clouds")){
			
			this.image.setImageResource(R.drawable.weezle_cloud_sun);
			
		}else if(clima.getDescripcion().equals("light rain")){
			
			this.image.setImageResource(R.drawable.weezle_medium_rain);
			
		}else if(clima.getDescripcion().equals("moderate rain")){
			
			this.image.setImageResource(R.drawable.weezle_rain);
			
		}else if(clima.getDescripcion().equals("thunderstorm with rain")){
			
			this.image.setImageResource(R.drawable.weezle_cloud_thunder_rain);
			
		}else if(clima.getDescripcion().equals("snow")){
			
			this.image.setImageResource(R.drawable.weezle_snow);
			
		}else if(clima.getDescripcion().equals("mist")){
			
			this.image.setImageResource(R.drawable.weezle_fog);
		}
		
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_mostrar_clima, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
