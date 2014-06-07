package es.mgj.fragments;


import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import es.mgj.formfit.R;


public class TabsListener implements TabListener {
		 
	    private Fragment fragmento;
	 
	    public TabsListener(Fragment fragmento)
	    {
	        this.fragmento = fragmento;
	    }


		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			

		}


		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			
			ft.replace(R.id.contenedor, fragmento);
		}


		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			
			ft.remove(fragmento);
		}
	 
	   
}

