package es.mgj.activitys;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import es.mgj.base.Actividad;
import es.mgj.database.BasedeDatos;
import es.mgj.formfit.R;
import es.mgj.fragments.TabIniciarActividad;

public class MapActivity extends FragmentActivity implements OnClickListener,
		LocationListener, OnMarkerClickListener, ConnectionCallbacks,
		OnConnectionFailedListener,
		com.google.android.gms.location.LocationListener, OnAudioFocusChangeListener  {

	private GoogleMap mapa;
	private BasedeDatos bd = new BasedeDatos(this);
	private Bundle b;
	private double latitud;
	private double longitud;
	private double latitudInicial;
	private double longitudInicial;
	private long comienzoMilis;
	private long finMilis;
	private long ultimoAviso;
	private Button btMusica;
	private boolean playing = true;
	private List<LatLng> locations = new ArrayList<LatLng>();
	final Context c = this;
	private SharedPreferences preferencias;
	private LocationClient locationClient;

	private static final LocationRequest LOC_REQUEST = LocationRequest.create()
			.setInterval(5000).setFastestInterval(16)
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		Intent i = getIntent();
		b = i.getExtras();
		preferencias = PreferenceManager.getDefaultSharedPreferences(c);
		Button btParar = (Button) findViewById(R.id.btParar);
		btMusica = (Button) findViewById(R.id.btMusica);
		btParar.setOnClickListener(this);
		btMusica.setOnClickListener(this);

		mapa = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapa)).getMap();

		MapsInitializer.initialize(this);

		mapa.setMyLocationEnabled(true);

		configuraLocalizador();

		mapa.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
			
			public void onMapLoaded() {
				Location location = mapa.getMyLocation();
				if (location != null) {

					comienzoMilis = System.currentTimeMillis();
					ultimoAviso = System.currentTimeMillis();
					CameraUpdate camara = CameraUpdateFactory
							.newLatLng(new LatLng(location.getLatitude(),
									location.getLongitude()));

					mapa.moveCamera(camara);
					mapa.animateCamera(CameraUpdateFactory.zoomTo(18.0f), 2000,
							null);
					latitudInicial = location.getLatitude();
					longitudInicial = location.getLongitude();

					mapa.addMarker(new MarkerOptions().position(
							new LatLng(location.getLatitude(), location
									.getLongitude())).title("Inicio"));
				}

			}
		});

	}

	@Override
	protected void onStart() {

		super.onStart();
		locationClient.connect();
	}

	@Override
	protected void onStop() {

		locationClient.disconnect();
		super.onStop();
	}

	@Override
	public void onConnected(Bundle arg0) {
		locationClient.requestLocationUpdates(LOC_REQUEST, this);
	}

	private void configuraLocalizador() {
		if (locationClient == null) {
			locationClient = new LocationClient(this, this, this);
		}
	}
	
	private void pausarMusica(){
		
		Intent i = new Intent("com.android.music.musicservicecommand");
		
		i.putExtra("command", "pause");
		this.sendBroadcast(i);
		
		this.btMusica.setText("Reanudar Musica");
		
		this.playing = !this.playing;

	}
	
	private void reanudarMusica(){
		
		Intent i = new Intent("com.android.music.musicservicecommand");
		
		i.putExtra("command", "play");
		this.sendBroadcast(i);
		
		
		this.btMusica.setText("Pausar Musica");
		
		
		this.playing = !this.playing;
		
	}
	

	@Override
	public void onClick(final View v) {
		mapa.snapshot(new GoogleMap.SnapshotReadyCallback() {
			@Override
			public void onSnapshotReady(Bitmap arg0) {
				
				if(v.getId() == btMusica.getId() && playing){
					
					pausarMusica();
					return;
				}
				
				if(v.getId() == btMusica.getId() && !playing){
					reanudarMusica();
					return;
				}
				
				if (preferencias.getBoolean("avisos_audio", true)) {
					MediaPlayer mp = MediaPlayer.create(c, R.raw.finished);
					mp.start();
				}
				
				mapa.addMarker(new MarkerOptions().position(
						new LatLng(latitud, longitud)).title("Fin"));

				Location yo = new Location("fin");
				yo.setLatitude(latitud);
				yo.setLongitude(longitud);

				Location yoInicio = new Location("inicio");
				yoInicio.setLatitude(latitudInicial);
				yoInicio.setLongitude(longitudInicial);

				finMilis = System.currentTimeMillis();

				Actividad a = calculos();
				a.setDistanciaRecorrida(yo.distanceTo(yoInicio));

				if (preferencias.getString("uidades_distancia", "Metros")
						.equals("Millas")) {
					a.setDistanciaRecorrida((float) (a.getDistanciaRecorrida() * 0.00062137));
				}

				bd.nuevaActividad(
						b.getString("tipo") + "\n" + Math.floor(a.getDistanciaRecorrida() * 100) / 100,
						a.getDistanciaRecorrida(), a.getDuracion(),
						a.getCaloriasQuemadas(), arg0, b.getString("tipo"),
						a.getFecha());
				finish();
			}
		});

	}

	public Actividad calculos() {

		Calendar gc = GregorianCalendar.getInstance();

		Actividad a = new Actividad();
		long duraciontemp = finMilis - comienzoMilis;

		float duracion = Float.valueOf(String.format(
				"%d.%d",
				TimeUnit.MILLISECONDS.toMinutes(duraciontemp),
				TimeUnit.MILLISECONDS.toSeconds(duraciontemp)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(duraciontemp))));

		a.setDuracion(duracion);
		float calorias = 0;

		Cursor c = bd.getDatos();
		c.moveToFirst();
		if (b.getString("tipo").equals("Footing")) {
			calorias = (float) (0.092 * (c.getFloat(5) * 2.2) * duracion);
		}
		if (b.getString("tipo").equals("Ciclismo")) {
			calorias = (float) (0.071 * (c.getFloat(5) * 2.2) * duracion);
		}
		if (b.getString("tipo").equals("Patinaje")) {
			calorias = (float) (0.049 * (c.getFloat(5) * 2.2) * duracion);
		}

		a.setCaloriasQuemadas(calorias);
		a.setFecha(String.valueOf(DateFormat.format("dd/MM/yy", gc)));
		return a;

	}

	@Override
	public void onLocationChanged(Location arg0) {
		
		if( ultimoAviso  >= System.currentTimeMillis() + 600000 &&  preferencias.getBoolean("avisos_audio", true) ){
			ultimoAviso = System.currentTimeMillis();
			MediaPlayer mp = MediaPlayer.create(c, R.raw.sample);
			mp.start();	
		}

		LatLng posicion = new LatLng(arg0.getLatitude(), arg0.getLongitude());

		this.locations.add(posicion);

		mapa.moveCamera(CameraUpdateFactory.newLatLng(posicion));

		mapa.addCircle(new CircleOptions()
				.center(new LatLng(arg0.getLatitude(), arg0.getLongitude()))
				.radius(1).strokeColor(Color.TRANSPARENT).fillColor(Color.BLUE));

		latitud = arg0.getLatitude();
		longitud = arg0.getLongitude();

	}

	@Override
	public void onProviderDisabled(String arg0) {

	}

	@Override
	public void onProviderEnabled(String arg0) {

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {

	}

	@Override
	public void onDisconnected() {

	}

	@Override
	public boolean onMarkerClick(Marker arg0) {

		return false;
	}

	@Override
	public void onAudioFocusChange(int focusChange) {
		// TODO Auto-generated method stub
		
	}

}
