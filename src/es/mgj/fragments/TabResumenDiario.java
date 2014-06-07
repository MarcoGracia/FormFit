package es.mgj.fragments;

import java.util.Calendar;
import java.util.GregorianCalendar;

import es.mgj.base.Actividad;
import es.mgj.database.BasedeDatos;
import es.mgj.formfit.R;
import es.mgj.formfit.R.layout;
import es.mgj.formfit.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class TabResumenDiario extends Fragment {
	
	private TextView tvActividades;
		private TextView tvAlimentos;
		private TextView tvNatural;
		private TextView tvTotal;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View view = inflater.inflate(R.layout.tab_resumen_diario, container, false);
		
		this.tvActividades = (TextView) view.findViewById(R.id.tvCaloriasTotalActividades);
		this.tvAlimentos = (TextView) view.findViewById(R.id.tvCaloriasTotalIngestaAlimentos);
		this.tvNatural = (TextView) view.findViewById(R.id.tvCaloriasQuemaNatural);
		this.tvTotal = (TextView) view.findViewById(R.id.tvCaloriasTotal);
		
		BasedeDatos bd = new BasedeDatos(this.getActivity());
		Calendar gc = GregorianCalendar.getInstance();
		
		String fecha = String.valueOf(DateFormat.format("dd/MM/yy", gc));
		
		
		float totalAlimentos = calculoTotalAlimentos(fecha, bd);
		float totalActividades = calculoTotalActividades(fecha, bd);
		float totalNatural = calculoTotalNatural();
		
		this.tvTotal.setText( String.valueOf(totalAlimentos - totalActividades-totalNatural ) );
		
		return view;

	}

	private float calculoTotalNatural() {
		float total = 0;
		
		this.tvNatural			
		
		.setText(String.valueOf(total) + "kc");
		
		return total;
	}

	private float calculoTotalActividades(String fecha, BasedeDatos bd) {
		
		float total = 0;
		
		Cursor c = bd.getActividades();
		
		while(c.moveToNext()){
			if( c.getString(2).equals(fecha) ){
				total+=c.getFloat(5);
			}
		}
		
		this.tvActividades.setText(String.valueOf(total) + "kc");
		return total;
	}

	private float calculoTotalAlimentos(String fecha, BasedeDatos bd) {
		
		float total = 0;
		
		Cursor c = bd.getAlimentos();
		
		while(c.moveToNext()){
			if( c.getString(2).equals(fecha) ){
				total+=c.getFloat(3);
			}
		}
		
		this.tvAlimentos.setText(String.valueOf(total) + "kc");
		return total;
	}

}
