package es.mgj.formfit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import es.mgj.base.Actividad;

public class VerActividad extends Activity {
	private Actividad actividad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_actividad);
		
		Intent i = this.getIntent();
		Bundle b = i.getExtras();
		this.actividad = (Actividad) b.getSerializable("actividad");
		TextView tvNombre = (TextView) this.findViewById(R.id.tvNombreActividad);
		TextView tvFecha = (TextView) this.findViewById(R.id.tvFechaActividad);
		TextView tvDuracion = (TextView) this.findViewById(R.id.tvDuracion);
		TextView tvCalorias = (TextView) this.findViewById(R.id.tvCalorias);
		TextView tvTipo = (TextView) this.findViewById(R.id.tvTipo);
		TextView tvDistancia = (TextView) this.findViewById(R.id.tvDistancia);
		ImageView ivMapa = (ImageView) this.findViewById(R.id.ivMapaActividad);
		
		tvNombre.setText("Nombre : " + actividad.getNombre());
		tvFecha.setText("Fecha: " + actividad.getFecha());
		tvDuracion.setText("Duracion: " + actividad.getDuracion() + " horas");
		tvCalorias.setText("Calorias: " + actividad.getCaloriasQuemadas());
		tvTipo.setText("Tipo: " + actividad.getTipo());
		tvDistancia.setText("Distancia: " + actividad.getDistanciaRecorrida());
		
		Drawable image = null;
		image =  new BitmapDrawable(BitmapFactory.decodeByteArray(actividad.getMapa(), 0, actividad.getMapa().length));
		
		ivMapa.setImageDrawable(image);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
