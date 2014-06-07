package es.mgj.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import es.mgj.base.Actividad;
import es.mgj.database.BasedeDatos;
import es.mgj.formfit.R;

public class TabResumen extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.tabresumen, container, false);
		TextView etMetros = (TextView) view.findViewById(R.id.tvKilometrosRecorridos);
		TextView etCalorias = (TextView) view.findViewById(R.id.tvCalorasQuemadas);
		TextView etBalance = (TextView) view.findViewById(R.id.tvBalanceDiario);
		TextView etPeso = (TextView) view.findViewById(R.id.tvPeso);
		ProgressBar pbIMC = (ProgressBar) view.findViewById(R.id.scBalanceIMC);
		
		ArrayList<Actividad> actividades = volcarDBaArray();
		
		float metros = 0, calorias = 0, balancediario = 0, peso, altura = 0;
		
		for(Actividad a : actividades){
			metros += a.getDistanciaRecorrida();
			calorias+=a.getCaloriasQuemadas();
			
		}
		if(actividades.size()>0)
			balancediario=calorias/actividades.size();
		
		SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		
		BasedeDatos bd = new BasedeDatos(this.getActivity());
		Cursor c = bd.getDatos();
		c.moveToFirst();
		if(c.getString(1).equals("sin configurar"))
			peso = 0;
		else{
			peso = c.getFloat(5);
			altura = c.getFloat(6);
		}
		
		etMetros.setText("Distancia recorrida: " + String.valueOf(metros) + " " + preferencias.getString("unidades_distancia", " metros"));
		etCalorias.setText("Caloráis quemadas: " + String.valueOf(calorias) + " calorias");
		etBalance.setText("Balance de calorías quemadas por actividad: " + String.valueOf(balancediario));
		
		if(peso == 0)
			etPeso.setText("Sin configurar");
		else{
			etPeso.setText("Peso: " + String.valueOf(peso));
			pbIMC.setProgress(0);
			pbIMC.setProgress((int)((peso/(altura*altura))*10000)-13);
			
		}
			
		
		
		return view;
	}
	
	private ArrayList<Actividad> volcarDBaArray(){
		ArrayList<Actividad> actividades = new ArrayList<Actividad>();
		BasedeDatos bd = new BasedeDatos(this.getActivity());
		Cursor c = bd.getActividades();
		Actividad a;

		while(c.moveToNext()){
			a=new Actividad();
			a.setFecha(c.getString(2));
			a.setCaloriasQuemadas(c.getFloat(5));
			a.setDistanciaRecorrida(c.getFloat(4));
			actividades.add(a);
		}
		
		return actividades;
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
}
