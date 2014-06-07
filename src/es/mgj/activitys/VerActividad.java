package es.mgj.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import es.mgj.base.Actividad;
import es.mgj.formfit.R;

import com.facebook.*;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.model.*;



public class VerActividad extends Activity implements OnClickListener {
	
	private Actividad actividad;
	private Facebook facebook = new Facebook("300785456764524");
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;

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
		final TextView tvTipo = (TextView) this.findViewById(R.id.tvTipo);
		TextView tvDistancia = (TextView) this.findViewById(R.id.tvDistancia);
		ImageView ivMapa = (ImageView) this.findViewById(R.id.ivMapaActividad);
		Button btCompartir = (Button) this.findViewById(R.id.btCompartirActividad);
		
		tvNombre.setText("Nombre : " + actividad.getNombre());
		tvFecha.setText("Fecha: " + actividad.getFecha());
		tvDuracion.setText("Duracion: " + actividad.getDuracion() + " horas");
		tvCalorias.setText("Calorias: " + actividad.getCaloriasQuemadas());
		tvTipo.setText("Tipo: " + actividad.getTipo());
		tvDistancia.setText("Distancia: " + actividad.getDistanciaRecorrida());
		
		btCompartir.setOnClickListener(this);
		
		Drawable image = null;
		image =  new BitmapDrawable(BitmapFactory.decodeByteArray(actividad.getMapa(), 0, actividad.getMapa().length));
		
		ivMapa.setImageDrawable(image);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  	facebook.authorizeCallback(requestCode, resultCode, data);
	 }

	@Override
	public void onClick(View v) {
		/*Session.openActiveSession(this, true, new Session.StatusCallback() {

		    // callback when session changes state
		    @Override
		    public void call(Session session, SessionState state, Exception exception) {
		    	if (session.isOpened()) {
		    		Request.newMeRequest(session, new Request.GraphUserCallback() {

		    			  // callback after Graph API response with user object
		    			  @Override
		    			  public void onCompleted(GraphUser user, Response response) {
		    				  if (user != null) {
		    					  
		    				  }
		    			  }
		    			  
		    		}).executeAsync();
		    	}
		    }
		  });*/
		loginFacebook();
		postToWall();
		
	}
	
	@SuppressWarnings("deprecation")
	public void loginFacebook(){
		
		mPrefs = getPreferences(MODE_PRIVATE);
		  String access_token = mPrefs.getString("access_token", null);
		  long expires = mPrefs.getLong("access_expires", 0);
		 
		  if (access_token != null) {
		   facebook.setAccessToken(access_token);
		   
		  }
		 
		  if (expires != 0) {
		   facebook.setAccessExpires(expires);
		  }
		 
		  if (!facebook.isSessionValid()) {
			  
		   facebook.authorize(this,
		     new String[] { "email", "publish_stream" },
		     new DialogListener() {
		 
		      @Override
		      public void onCancel() {
		       // Function to handle cancel event
		      }
		 
		      @Override
		      public void onComplete(Bundle values) {
		       // Function to handle complete event
		       // Edit Preferences and update facebook acess_token
		       SharedPreferences.Editor editor = mPrefs.edit();
		       editor.putString("access_token",
		         facebook.getAccessToken());
		       
		       editor.putLong("access_expires",
		         facebook.getAccessExpires());
		       
		       editor.commit();
		      }
		 
		      @Override
		      public void onError(DialogError error) {
		       // Function to handle error
		 
		      }
		 
		      @Override
		      public void onFacebookError(FacebookError fberror) {
		       // Function to handle Facebook errors
		 
		      }
		 
		     });
		  }
	    
	}
	
	@SuppressWarnings("deprecation")
	 public void postToWall() {
		
		  Bundle params = new Bundle();
		  params.putString("name", "¡Mirad lo que he hecho!");
		  params.putString("caption", "He consumido " + actividad.getCaloriasQuemadas() + "calorías");
		  params.putString("description", "Y he recorrido un total de " + actividad.getDistanciaRecorrida() + "metros");
		  params.putString("link", "https://www.google.es/maps/preview");
		
		  facebook.dialog(this, "feed",params , new DialogListener() {
		 
		   @Override
		   public void onFacebookError(FacebookError e) {
		   }
		 
		   @Override
		   public void onError(DialogError e) {
		   }
		 
		   @Override
		   public void onComplete(Bundle values) {
		   }
		 
		   @Override
		   public void onCancel() {
		   }
		  });
	 
	 }

}
