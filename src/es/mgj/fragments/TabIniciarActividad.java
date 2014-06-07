package es.mgj.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import es.mgj.activitys.ActivityMostrarClima;
import es.mgj.activitys.MapActivity;
import es.mgj.base.Actividad;
import es.mgj.base.Clima;
import es.mgj.base.Rutina;
import es.mgj.database.BasedeDatos;
import es.mgj.formfit.ExtraerClima;
import es.mgj.formfit.R;

public class TabIniciarActividad extends Fragment implements OnClickListener {
	
	private Spinner sp;
	private Spinner spRutinas;
	private ArrayList<Rutina> rutinas;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.comenzaractividad, null, false);
		
		Button bt = (Button) v.findViewById(R.id.btIniciarActividad);
		Button bt2 = (Button) v.findViewById(R.id.btgps);
		Button btMusica = (Button) v.findViewById(R.id.btIniciarMusica);
		Button btClima = (Button) v.findViewById(R.id.btClima);
		sp = (Spinner) v.findViewById(R.id.spTipoActividad);
		spRutinas = (Spinner) v.findViewById(R.id.spRutinas);
		
		volcarDBaArray();
		
		bt.setOnClickListener(this);
		bt2.setOnClickListener(this);
		btMusica.setOnClickListener(this);
		btClima.setOnClickListener(this);
		
		return v;
	}

	private void volcarDBaArray(){
		
		rutinas = new ArrayList<Rutina>();
		Cursor c = new BasedeDatos( this.getActivity() ).getRutinas();
		Rutina r = new Rutina();
		r.setNombre("Sin rutina");
		
		rutinas.add(r);

		while(c.moveToNext()){
			r = new Rutina();
			r.setNombre(c.getString(1));
			r.setRepeticiones(c.getInt(2));
			r.setDuracionFase1(c.getFloat(3));
			r.setDuracionFase2(c.getFloat(4));
			r.setDuracionFase3(c.getFloat(5));
			
			rutinas.add(r);
		}
		
		ArrayAdapter adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, rutinas);
		
		this.spRutinas.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
			case(R.id.btgps):
				
				startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				break;
				
			case(R.id.btIniciarActividad):
				
				Intent i = new Intent(getActivity(), MapActivity.class);
				i.putExtra( "tipo", sp.getItemAtPosition(sp.getSelectedItemPosition()).toString() );
				i.putExtra( "rutina", rutinas.get( spRutinas.getSelectedItemPosition() ) );
				startActivity(i);
				SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
				if(preferencias.getBoolean("avisos_audio", true)){
					
					
					MediaPlayer mp = MediaPlayer.create(this.getActivity(), R.raw.started);
					mp.start();

				}
				
				break;
				
			case(R.id.btIniciarMusica):
			
				Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
			
				startActivity(intent);
				
				break;
				
			case(R.id.btClima):	
				
				Intent inte = new Intent(getActivity(), ActivityMostrarClima.class);
			
				startActivity(inte);
				break;
		}
		
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
}
