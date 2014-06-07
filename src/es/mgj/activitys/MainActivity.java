package es.mgj.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import es.mgj.formfit.R;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btRun = (Button)findViewById(R.id.btRun);
		btRun.setOnClickListener(this);
		
		Button btAcercaDe = (Button)findViewById(R.id.btAcercade);
		btAcercaDe.setOnClickListener(this);
		
		Button btRest = (Button)findViewById(R.id.btRest);
		btRest.setOnClickListener(this);
		
		SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
		
		if(preferencias.getBoolean("saltar_inicio", false)){
			btRun.performClick();
			finish();
		}
				
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
			case(R.id.btRest):
				System.exit(0);
				break;
			case(R.id.btRun):
				Intent intent = new Intent(this, MainActivityTabs.class);
				startActivity(intent);
				break;
			case(R.id.btAcercade):
				about(this);
				break;
		}
		
	}
	
	public static void about(Context contexto){
		AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
		builder.setMessage(R.string.acerda_de_mensaje)
			.setTitle(R.string.acercade)
			.setPositiveButton(R.string.cancelar, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();						
				}
			});
		AlertDialog dialogo = builder.create();
		dialogo.show();
	}

}
