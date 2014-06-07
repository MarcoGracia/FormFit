package es.mgj.activitys;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
import android.widget.ImageButton;
import android.widget.TextView;

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
import es.mgj.base.Rutina;
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
	private ImageButton btMusica;
	private boolean playing = true;
	private List<LatLng> locations = new ArrayList<LatLng>();
	final Context c = this;
	private SharedPreferences preferencias;
	private LocationClient locationClient;
	private Rutina rutina;
	private TextView tvFase1Total;
	private TextView tvFase2Total;
	private TextView tvFase3Total;
	private TextView tvFase1;
	private TextView tvFase2;
	private TextView tvFase3;
	private TextView tvTotalTiempo;
	private Button btParar;
	

	private static final LocationRequest LOC_REQUEST = LocationRequest.create()
			.setInterval(5000).setFastestInterval(5)
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mapa);
		Intent i = getIntent();
		
		b = i.getExtras();
		rutina = (Rutina) b.get("rutina");
		
		this.tvFase1Total = (TextView)this.findViewById(R.id.tvFase1MapaTiempoTotal);
		this.tvFase2Total = (TextView)this.findViewById(R.id.tvFase2MapaTiempoTotal);
		this.tvFase3Total = (TextView)this.findViewById(R.id.tvFase3MapaTiempoTotal);
		this.tvFase1 = (TextView)this.findViewById(R.id.tvFase1TiempoActual);
		this.tvFase2 = (TextView)this.findViewById(R.id.tvFase2TiempoActual);
		this.tvFase3 = (TextView)this.findViewById(R.id.tvFase3TiempoActual);
		this.tvTotalTiempo = (TextView)this.findViewById(R.id.tvTiempoTotalMarcador);
		
        Timer timer = new Timer("Printer");
 
        MyTask t = new MyTask(this);
        
        timer.schedule(t, 0, 1000);
		
		if(!rutina.getNombre().equals("Sin rutina")){
			cargarRutina();
		}
		
		
		preferencias = PreferenceManager.getDefaultSharedPreferences(c);
		btParar = (Button) findViewById(R.id.btParar);
		btMusica = (ImageButton) findViewById(R.id.btMusica);
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

	private void cargarRutina() {

		this.tvFase1Total.setText(String.valueOf( this.rutina.getDuracionFase1()) + "0" );
		this.tvFase2Total.setText(String.valueOf( this.rutina.getDuracionFase2()) + "0" );
		this.tvFase3Total.setText(String.valueOf( this.rutina.getDuracionFase3()) + "0" );
		
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

		this.playing = !this.playing;

	}
	
	private void reanudarMusica(){
		
		Intent i = new Intent("com.android.music.musicservicecommand");
		
		i.putExtra("command", "play");
		this.sendBroadcast(i);
		
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
	
	class MyTask extends TimerTask {
	    //times member represent calling times.
		int minutosTotal = 0;
		
		int minutosTotalFase1 = 0;
		int minutosTotalFase2 = 0;
		int minutosTotalFase3 = 0;
		float segundosTotalFase1 = 0;
		float segundosTotalFase2 = 0;
		float segundosTotalFase3 = 0;
		
	    private int timesTotal = 0;

	    private MapActivity mapActivity;
	    boolean fase1 = true;
	    boolean fase2 = false;
	    boolean fase3 = false;
	    
	    public MyTask( MapActivity mapActivity ){
	    	this.mapActivity = mapActivity;
	    }

	    public void run() {
	    	this.mapActivity.runOnUiThread(new Runnable(){

				@Override
				public void run() {
					
					if( timesTotal - minutosTotal * 60 == 60 ){
			    		minutosTotal++;
			    	}
					
			    	timesTotal++;
			        mapActivity.tvTotalTiempo.setText( 
			        		String.valueOf( minutosTotal ) + "." + String.valueOf( timesTotal - minutosTotal * 60 ));
			        
			        if(!rutina.getNombre().equals("Sin rutina")){
			        	
						if( fase1 ){
							
							segundosTotalFase1 ++ ;
							
							if( segundosTotalFase1 - minutosTotalFase1 * 60 == 60 ){
								minutosTotalFase1++;
					    	}
							
							float dec = segundosTotalFase1 / 60f;
							
							if( dec >= rutina.getDuracionFase1() ){
								
								mapActivity.tvFase1.setText( String.valueOf( rutina.getDuracionFase1() +"0" ) );
								fase1=false;
								fase2=true;
								
							}else {
								
								mapActivity.tvFase1.setText( 
										String.valueOf( minutosTotalFase1 ) + "." + String.valueOf( (int)segundosTotalFase1 - minutosTotalFase1 * 60 ));
							}
	    							
						}
						
						if( fase2 ){
							
							segundosTotalFase2 ++ ;
							
							float dec = segundosTotalFase2 / 60;
							
							if( dec >= rutina.getDuracionFase2() ){
								
								mapActivity.tvFase2.setText( String.valueOf( rutina.getDuracionFase2() )+"0"  );
								
								fase2=false;
								fase3=true;
								
							}else {
								mapActivity.tvFase2.setText( 
										String.valueOf( minutosTotalFase2 ) + "." + String.valueOf( (int)segundosTotalFase2 - minutosTotalFase2 * 60 ));
							}
	    						    					
						}
						
						if( fase3 ){
							
							segundosTotalFase3 ++ ;
							
							float dec = segundosTotalFase3 / 60;
							
							if( dec >= rutina.getDuracionFase3() ){
								
								mapActivity.tvFase3.setText( String.valueOf( rutina.getDuracionFase3() )+"0"  );
								fase3=true;
								mapActivity.btParar.performClick();
								
							}else {
								mapActivity.tvFase3.setText( 
										String.valueOf( minutosTotalFase3 ) + "." + String.valueOf( (int)segundosTotalFase3 - minutosTotalFase3 * 60 ));
							}

						}
						
					}
					
				}
	    		
	    	});
	    	
	    }
	}

}
