package es.mgj.activitys;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import es.mgj.formfit.Preferencias;
import es.mgj.formfit.R;
import es.mgj.fragments.FragmentIntroducirRutina;
import es.mgj.fragments.TabAlimentos;
import es.mgj.fragments.TabIniciarActividad;
import es.mgj.fragments.TabListaActividades;
import es.mgj.fragments.TabResumen;
import es.mgj.fragments.TabResumenDiario;
import es.mgj.fragments.TabsListener;


public class MainActivityTabs extends FragmentActivity {

	public static ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_tabs);
	}
	
	@Override
	protected void onResume(){
		super.onResume();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		cargarPestanas();
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()){
			case(R.id.preferencias):
				Intent i = new Intent(this, Preferencias.class);
				startActivity(i);
				break;
		
			case(R.id.configuracion):
				Intent intent = new Intent(this, ConfiguracionCuenta.class);
				startActivity(intent);
				break;
			
			case(R.id.comenzar_actividad):
				
				actionBar.setSelectedNavigationItem(0);
				
				break;
			case(R.id.acercademenu):
				MainActivity.about(this);
				break;
			
		}
		return true;
		
	}
	
	 private void cargarPestanas() {
	    	
	    	Resources res = getResources();
	    	actionBar = getActionBar();
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        

	        ActionBar.Tab tabActividad = actionBar.newTab().setText(res.getString(R.string.titulo_tab_actividad));
	        ActionBar.Tab tabListaActividades = actionBar.newTab().setText(res.getString(R.string.titulo_tab_lista_actividades));
	        ActionBar.Tab tabAlimentos = actionBar.newTab().setText(res.getString(R.string.titulo_tab_alimentos));
	        ActionBar.Tab tabResumenDiario = actionBar.newTab().setText(res.getString(R.string.titulo_tab_resumen_diario));
	        ActionBar.Tab tabResumen = actionBar.newTab().setText(res.getString(R.string.titulo_tab_resumen));
	        ActionBar.Tab tabRutina = actionBar.newTab().setText(res.getString(R.string.titulo_tab_rutina));
	        

	        Fragment fragmentoTab1 = new TabIniciarActividad();
	        Fragment fragmentoTab2 = new TabListaActividades();
	        Fragment fragmentoTab3 = new TabAlimentos();
	        Fragment fragmentoTab4 = new TabResumen();
	        Fragment fragmentoTab5 = new TabResumenDiario();
	        Fragment fragmentoTab6 = new FragmentIntroducirRutina();
	        

	        tabActividad.setTabListener(new TabsListener(fragmentoTab1));
	        tabListaActividades.setTabListener(new TabsListener(fragmentoTab2));
	        tabAlimentos.setTabListener(new TabsListener(fragmentoTab3));
	        tabResumen.setTabListener(new TabsListener(fragmentoTab4));
	        tabResumenDiario.setTabListener(new TabsListener(fragmentoTab5));
	        tabRutina.setTabListener(new TabsListener(fragmentoTab6));
	        

	        actionBar.addTab(tabActividad);
	        actionBar.addTab(tabListaActividades);
	        actionBar.addTab(tabRutina);
	        actionBar.addTab(tabAlimentos);
	        actionBar.addTab(tabResumenDiario);
	        actionBar.addTab(tabResumen);
	        
	    }
	 @Override
	 protected void onDestroy(){
			super.onDestroy();
		}


}
