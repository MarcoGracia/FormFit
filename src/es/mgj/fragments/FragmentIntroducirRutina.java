package es.mgj.fragments;

import es.mgj.base.Rutina;
import es.mgj.database.BasedeDatos;
import es.mgj.formfit.R;
import es.mgj.formfit.R.id;
import es.mgj.formfit.R.layout;
import es.mgj.formfit.R.menu;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class FragmentIntroducirRutina extends Fragment implements OnClickListener{
	
	private TextView tvFase1;
	private TextView tvFase2;
	private TextView tvFase3;
	private EditText etNombre;
	private NumberPicker numberPicker;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

	View v = inflater.inflate(R.layout.fragment_fragment_introducir_rutina, null, false);
	
	numberPicker = (NumberPicker) v.findViewById(R.id.numberPicker1);
	
	Button btGuardar = (Button) v.findViewById(R.id.btGuardarRutina);
	Button btCancelar = (Button) v.findViewById(R.id.btCancelarGuardarRutina);
	
	Button btFase1 = (Button) v.findViewById(R.id.btDuracionRutina1);
	Button btFase2 = (Button) v.findViewById(R.id.btDuracionFase2);
	Button btFase3 = (Button) v.findViewById(R.id.btDuracionFase3);
	
	tvFase1 = (TextView) v.findViewById(R.id.tvFase1);
	tvFase2 = (TextView) v.findViewById(R.id.tvFase2);
	tvFase3 = (TextView) v.findViewById(R.id.tvFase3);
	
	etNombre = (EditText) v.findViewById(R.id.etNombreRutina);
	
	btFase1.setOnClickListener(this);
	btFase2.setOnClickListener(this);
	btFase3.setOnClickListener(this);
	btGuardar.setOnClickListener(this);
	btCancelar.setOnClickListener(this);
	
	numberPicker.setMaxValue(100);
	numberPicker.setMinValue(1);
	return v;
	
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }

	@Override
	public void onClick(View v) {
		
		DialogFragmentDuracionHorasActivity newFragment = new DialogFragmentDuracionHorasActivity();
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        
        if (prev != null) {
            ft.remove(prev);
        }
        
        ft.addToBackStack(null);
		
		switch (v.getId()) {
		
			case R.id.btDuracionRutina1:
				
		        newFragment.setTv( this.tvFase1 );

		        newFragment.show(ft, "dialog");
		       
			    break;
			    
			case R.id.btDuracionFase2:
				 
		        newFragment.setTv( this.tvFase2 );

		        newFragment.show(ft, "dialog");
		       
			    break;
			    
			case R.id.btDuracionFase3:
	    
				newFragment.setTv( this.tvFase3 );
				
				newFragment.show(ft, "dialog");
	   
				break;
				
			case R.id.btCancelarGuardarRutina:
				//TODO
				break;
			
			case R.id.btGuardarRutina:
				guardarRutina();
				break;
			
		}
		
	}

	private void guardarRutina() {
		Rutina rutina = new Rutina();
		
		rutina.setNombre( this.etNombre.getText().toString() );
		
		String[] tiempos;
		tiempos = this.tvFase1.getText().toString().split(" : ");
		String tiempo = tiempos[0] + "." +  tiempos[1];
		
		rutina.setDuracionFase1(Float.parseFloat(tiempo));
		
		tiempos = this.tvFase2.getText().toString().split(" : ");
		tiempo = tiempos[0] + "." +  tiempos[1];
		
		rutina.setDuracionFase2(Float.parseFloat(tiempo));
		
		tiempos = this.tvFase3.getText().toString().split(" : ");
		tiempo = tiempos[0] + "." +  tiempos[1];
		
		rutina.setDuracionFase3(Float.parseFloat( tiempo ));
		
		rutina.setRepeticiones( numberPicker.getValue() );
		
		new BasedeDatos( this.getActivity() ).nuevaRutina(rutina);
		
		Toast.makeText(this.getActivity(), "Rutina guardada", 2).show();
		
	}

	public TextView getTvFase1() {
		return tvFase1;
	}

	public void setTvFase1(TextView tvFase1) {
		this.tvFase1 = tvFase1;
	}

	public TextView getTvFase2() {
		return tvFase2;
	}

	public void setTvFase2(TextView tvFase2) {
		this.tvFase2 = tvFase2;
	}

	public TextView getTvFase3() {
		return tvFase3;
	}

	public void setTvFase3(TextView tvFase3) {
		this.tvFase3 = tvFase3;
	}

	
}
