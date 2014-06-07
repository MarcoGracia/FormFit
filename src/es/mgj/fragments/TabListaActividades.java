package es.mgj.fragments;


import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import es.mgj.activitys.VerActividad;
import es.mgj.base.Actividad;
import es.mgj.database.BasedeDatos;
import es.mgj.formfit.AdaptadorActividades;
import es.mgj.formfit.R;

public class TabListaActividades extends Fragment implements OnCreateContextMenuListener{
	
	private ListView lista;
	private AdaptadorActividades adapter;
	private BasedeDatos bd;

	private ArrayList<Actividad> actividades;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		bd = new BasedeDatos(this.getActivity());
		
		View view = inflater.inflate(R.layout.listaactividades, container, false);
		
		lista = (ListView) view.findViewById(R.id.lvListaActividades);
		this.actividades = volcarDBaArray();
		adapter = new AdaptadorActividades(getActivity(), R.layout.modeloadaptador, actividades);
	    lista.setAdapter(adapter);
	    
		listarActividades();
		
		BasedeDatos bd = new BasedeDatos(this.getActivity());
		adapter.notifyDataSetChanged();
		registerForContextMenu(lista);

		
		return view;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);
		
		this.getActivity().getMenuInflater().inflate(R.menu.menu_contextual, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		switch (item.getItemId()) {
			case R.id.ctx_elminiar:
				
				bd.eleminiarActividad(actividades.get(info.position).getId());
				actividades.remove(info.position);
				lista.invalidateViews();
				listarActividades();
				
				
				return true;
			case R.id.ctx_ver:
				Intent i = new Intent(this.getActivity(), VerActividad.class);
				i.putExtra("actividad", actividades.get(info.position));
				startActivity(i);
				
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	public void listarActividades(){
		
	    adapter.notifyDataSetChanged();
	}
	
	private ArrayList<Actividad> volcarDBaArray(){
		
		ArrayList<Actividad> actividades = new ArrayList<Actividad>();
		Cursor c = bd.getActividades();
		Actividad a;

		while(c.moveToNext()){
			a=new Actividad();
			a.setId(c.getInt(0));
			a.setNombre(c.getString(1));
			a.setFecha(c.getString(2));
			a.setDuracion(c.getFloat(3));
			a.setCaloriasQuemadas(c.getFloat(4));
			a.setDistanciaRecorrida(c.getFloat(5));
			a.setMapa(c.getBlob(6));
			a.setTipo(c.getString(7));
			actividades.add(a);
		}
		
		return actividades;
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
}
