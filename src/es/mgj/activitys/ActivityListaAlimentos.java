package es.mgj.activitys;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import es.mgj.base.Alimento;
import es.mgj.base.TipoAlimento;
import es.mgj.formfit.R;
import es.mgj.formfit.VerActividad;
import es.mgj.fragments.TabAlimentos;

public class ActivityListaAlimentos extends Activity implements OnCreateContextMenuListener{
	
	private ArrayAdapter ada;
	private ListView lista;
	private Alimento alimentoSeleccionado;
	private TipoAlimento ta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_lista_alimentos);
		ArrayList<String> fila = new ArrayList<String>();
		Intent i = getIntent();
        Bundle b = i.getExtras();
        
        ta = TabAlimentos.listaTipo.get(b.getInt("posicion"));
        
        Alimento a;
        for(int l = 0 ; l<ta.getListaAlimentos().size() ; l++){
        	
        	a = ta.getListaAlimentos().get(l);
        	fila.add(a.getNombre() + "  Calorías: "+a.getCalorias());
        }
        
		lista = (ListView) findViewById(R.id.lvListaAlimentos);
		registerForContextMenu(lista);
		
    	ada = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fila);
    	lista.setAdapter(ada);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);
		
		this.alimentoSeleccionado = ta.getListaAlimentos().get(((AdapterContextMenuInfo)menuInfo).position);  
		
		this.getMenuInflater().inflate(R.menu.menu_contextual_anadir_alimento, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		Intent i = new Intent(this, ActivityAnadirAlimento.class);
		i.putExtra("info", this.alimentoSeleccionado);
		startActivity(i);
		return false;

	}

}
