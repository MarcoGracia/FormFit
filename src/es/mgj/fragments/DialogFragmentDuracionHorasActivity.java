package es.mgj.fragments;


import es.mgj.formfit.R;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;


import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class DialogFragmentDuracionHorasActivity extends DialogFragment implements OnClickListener {
		
	private EditText etHoras;
	private EditText etMinutos;
	private String horas;
	private String minutos;
	private TextView tv;
	
	public DialogFragmentDuracionHorasActivity(){
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, 0);        
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	    	
		View view = inflater.inflate(R.layout.dialog_fragment_duracion_horas, container);
		
		etHoras = (EditText) view.findViewById(R.id.etHoras);
		etMinutos = (EditText) view.findViewById(R.id.etMinutos);
		
		Button btAceptar = (Button) view.findViewById(R.id.btAceptarTiempofase);
		Button btCancelar = (Button) view.findViewById(R.id.btCancelarTiempoFase);
		
		btAceptar.setOnClickListener(this);
		btCancelar.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btAceptarTiempofase:
				
				if(!this.etHoras.getText().toString().isEmpty()
						&& !this.etMinutos.getText().toString().isEmpty()){
					
					this.tv.setText(this.etHoras.getText().toString()
							+ " : " + this.etMinutos.getText().toString());
					
				}else if (this.etHoras.getText().toString().isEmpty()
						&& !this.etMinutos.getText().toString().isEmpty() ){
					
					this.tv.setText(this.etHoras.getText().toString()
							+ " : " + "00 min" );
					
				}
				else if (!this.etHoras.getText().toString().isEmpty()
						&& this.etMinutos.getText().toString().isEmpty()){
					
					this.tv.setText("1"
							+ " : " + this.etMinutos.getText().toString());
					
				}else{
					
					this.tv.setText("1"
							+ " : " + "00");
				}
				
				
			case R.id.btCancelarTiempoFase:
				dismiss();
				break;
		}
			
	}

	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	public String getMinutos() {
		return minutos;
	}

	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}

	public TextView getTv() {
		return tv;
	}

	public void setTv(TextView tv) {
		this.tv = tv;
	}
	
}
