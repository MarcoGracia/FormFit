package es.mgj.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import es.mgj.activitys.ActivityListaAlimentos;
import es.mgj.base.Alimento;
import es.mgj.base.TipoAlimento;
import es.mgj.formfit.R;


public class TabAlimentos extends Fragment implements OnItemClickListener{
	
	public static ArrayList<TipoAlimento> listaTipo = new ArrayList<TipoAlimento>();
	private ArrayList<String> titulosTiposAlimento;
	private ArrayAdapter ada;
	private ListView lista;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.tabalimentos, container, false);
		
		titulosTiposAlimento = new ArrayList<String>();
		lista = (ListView) view.findViewById(R.id.lvTipoAlimento);
    	ada = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, titulosTiposAlimento);
    	lista.setOnItemClickListener(this);
		TareaDescargaDatos tarea = new TareaDescargaDatos();
    	tarea.execute("http://videosdeinformatica.com/alimentos.json");
    	
 
    	lista.setAdapter(ada);
		return view;
	}
	
	private class TareaDescargaDatos extends AsyncTask<String, Void, Void> {

    	private boolean error = false;
    	

		@Override
		protected Void doInBackground(String... urls) {
			
			InputStream is = null;
	    	JSONArray jsonArray = null;
	    	
	    	try {

		    	HttpClient clienteHttp = new DefaultHttpClient();
		    	HttpPost httpPost = new HttpPost(urls[0]);
		    	HttpResponse respuesta = clienteHttp.execute(httpPost);
		    	HttpEntity entity = respuesta.getEntity();
		    	is = entity.getContent();
		    	

		    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    	StringBuilder sb = new StringBuilder();
		    	String linea = null;
		    	
		    	while ((linea = br.readLine()) != null) {
		    		sb.append(linea + "\n");
		    	}
		    	
		    	is.close();
		    	
		    	jsonArray = new JSONArray(sb.toString());
		    	JSONArray jsonAlimentos;
		    	
		    	ArrayList<Alimento> listaAlimento;
		    	TipoAlimento tipoalimento;
		    	Alimento alimento;
		    	
		    	for (int i = 0; i < jsonArray.length(); i++) {
		    		tipoalimento = new TipoAlimento();
		    		listaAlimento = new ArrayList<Alimento>();
		    		
		    		tipoalimento.setNombre(jsonArray.getJSONObject(i).getString("tipo"));
		    		titulosTiposAlimento.add(tipoalimento.getNombre());
		    		publishProgress();
		    		jsonAlimentos = jsonArray.getJSONObject(i).getJSONArray("lista");

		    		for(int l = 0; l < jsonAlimentos.length(); l++){
		    			alimento = new Alimento();
		    			alimento.setNombre(jsonAlimentos.getJSONObject(l).getString("nombre"));
		    			alimento.setCalorias(jsonAlimentos.getJSONObject(l).getDouble("kcal"));
		    			listaAlimento.add(alimento);
		    		}
		    		tipoalimento.setListaAlimentos(listaAlimento);
		    		listaTipo.add(tipoalimento);
		    		
		    	}

		    	
	    	} catch (ClientProtocolException cpe) {
	    		cpe.printStackTrace();
	    		error = true;
	    	} catch (IOException ioe) {
	    		ioe.printStackTrace();
	    		error = true;
	    	} catch (JSONException jse) {
	    		jse.printStackTrace();
	    		error = true;
	    	}
	    	
	    		
	    	return null;
		}
		
		@Override
		protected void onPostExecute(Void resultado) {
			super.onPostExecute(resultado);
			ada.notifyDataSetChanged();
			
		}
		
}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Intent i = new Intent(this.getActivity(),ActivityListaAlimentos.class);
		i.putExtra("posicion", arg2);
		startActivity(i);
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
	
}

