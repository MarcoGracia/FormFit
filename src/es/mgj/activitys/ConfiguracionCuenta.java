package es.mgj.activitys;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import es.mgj.database.BasedeDatos;
import es.mgj.formfit.Preferencias;
import es.mgj.formfit.R;
import es.mgj.fragments.DatePickerFragment;

public class ConfiguracionCuenta extends Activity implements OnClickListener{

	private BasedeDatos bd = new BasedeDatos(this);
	private EditText etNombre;
	private Button btFechaNacimiento;
	private RadioButton rbHombre;
	private RadioButton rbMujer;
	private EditText etPeso;
	private EditText etAltura;
	private RadioButton rbGanancia;
	private RadioButton rbMantenimiento;
	private RadioButton rbPerdida;
	private Button btGuardar;
	private Button btCancelar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracion_cuenta);
		
		etNombre = (EditText) findViewById(R.id.etNombre);
		btFechaNacimiento = (Button) findViewById(R.id.btFechaNacimiento);
		rbHombre = (RadioButton) findViewById(R.id.rbHombre);
		rbMujer = (RadioButton) findViewById(R.id.rbMujer);
		rbGanancia = (RadioButton) findViewById(R.id.rbGananciaPeso);
		rbMantenimiento = (RadioButton) findViewById(R.id.rbMantenimiento);
		rbPerdida = (RadioButton) findViewById(R.id.rbPerdidaPeso);
		btGuardar = (Button) findViewById(R.id.btGuardar);
		btGuardar.setOnClickListener(this);
		btCancelar = (Button) findViewById(R.id.btCancelar);
		btCancelar.setOnClickListener(this);
		etPeso = (EditText) findViewById(R.id.etPeso);
		etAltura = (EditText) findViewById(R.id.etAltura);
		cargarDatos();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case(R.id.preferencias):
				Intent i = new Intent(this, Preferencias.class);
				startActivity(i);
				break;
		
			case(R.id.comenzar_actividad):
				finish();
				break;
			case(R.id.acercademenu):
				MainActivity.about(this);
				break;
			
		}
		return true;
		
	}
	
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public void cargarDatos(){
		Cursor cursor = bd.getDatos();
		cursor.moveToFirst();

		if(cursor.getString(1).equalsIgnoreCase("sin configurar"))
			return;
		
		this.etNombre.setText(cursor.getString(1));
		
		this.btFechaNacimiento.setText(cursor.getString(2));
		
		if(cursor.getString(3).equals("hombre"))
			this.rbHombre.setChecked(true);
		else
			this.rbMujer.setChecked(true);
		
		if(cursor.getString(4).equals("ganancia"))
			this.rbGanancia.setChecked(true);
		
		else if(cursor.getString(4).equals("perdida"))
			this.rbPerdida.setChecked(true);
		
		else
			this.rbMantenimiento.setChecked(true);
			
		this.etPeso.setText(String.valueOf(cursor.getFloat(5)));
		
		this.etAltura.setText(String.valueOf(cursor.getFloat(6)));
		
	}
	
	public void guardarDatos(){
		String sexo, objetivo;
		if(this.rbHombre.isChecked())
			sexo="hombre";
		else
			sexo="mujer";
		
		if(this.rbGanancia.isChecked())
			objetivo="ganancia";
		
		else if(this.rbPerdida.isChecked())
			objetivo="perdida";
		
		else
			objetivo="mantenimiento";
		
		bd.nuevoDatos(this.etNombre.getText().toString(), 
				sexo, 
				this.btFechaNacimiento.getText().toString(), 
				objetivo, 
				Float.parseFloat(this.etPeso.getText().toString()), 
				Float.parseFloat(this.etAltura.getText().toString()));
		
	}

	@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.btGuardar){
			guardarDatos();
			finish();
		}
			
		if(arg0.getId()== R.id.btCancelar)
			finish();
		
	}

}
