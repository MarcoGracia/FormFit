package es.mgj.activitys;

import es.mgj.base.Alimento;
import es.mgj.database.BasedeDatos;
import es.mgj.formfit.R;
import es.mgj.formfit.R.id;
import es.mgj.formfit.R.layout;
import es.mgj.formfit.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityAnadirAlimento extends Activity implements OnClickListener {
	
	private TextView tvInfo;
	private Button btGuardar;
	private Button btCancelar;
	private EditText etCantidadAlimento;
	private BasedeDatos bd = new BasedeDatos(this);
	private Bundle b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_anadir_alimento);
		
		Intent i = getIntent();
        b = i.getExtras();
        
        this.tvInfo = (TextView) findViewById(R.id.tvCantidadAlimento);
        this.tvInfo.setText( ((Alimento)b.getSerializable("info")).getNombre() );
        
        this.btGuardar = (Button) findViewById(R.id.btnAceptarCantidadAlimento);
        this.btCancelar = (Button) findViewById(R.id.btCancelarCantidadAlimento);
        this.etCantidadAlimento = (EditText) findViewById(R.id.EditTextCantidad);
        
        this.btGuardar.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_anadir_alimento, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		
		if(arg0.getId() == this.btGuardar.getId()){
			
			try{
				Float.valueOf( this.etCantidadAlimento.getText().toString() );
			}catch( NumberFormatException e ) {
				Toast toast = Toast.makeText(this, "Debes introducir una cantidad válida", 1);
				toast.show();
				return;
			}
			
			Alimento alimento = (Alimento)b.getSerializable("info");
			double calorias = ( alimento.getCalorias() / 100 )* Float.valueOf( this.etCantidadAlimento.getText().toString() );
			bd.nuevoAlimento( alimento.getNombre(), calorias );
		}
		
		finish();
		
	}

}
