package es.mgj.formfit;



import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import es.mgj.base.Actividad;

public class AdaptadorActividades extends ArrayAdapter<Actividad>{
	
	private Context contexto;
	private int layoutId;
	private List<Actividad> datos;
	
	static class ItemActividad {
		
		TextView nombreActividad;
		TextView fechaActividad;
		TextView calorias;
		TextView distancia;
		ImageView mapa;
	}
	
	public AdaptadorActividades(Activity contexto, int layoutId, List<Actividad> datos) {
		super(contexto, layoutId, datos);
		this.contexto = contexto;
		this.layoutId = layoutId;
		this.datos = datos;
	}
	
	@Override
	public View getView(int posicion, View view, ViewGroup padre) {
		
		View fila = view;
		ItemActividad item = null;
		
		if (fila == null) {

			LayoutInflater inflater = ((Activity) contexto).getLayoutInflater();
			fila = inflater.inflate(layoutId, padre, false);
			
			item = new ItemActividad();
			item.mapa = (ImageView) fila.findViewById(R.id.imgBtnMapaLista);
			item.fechaActividad = (TextView) fila.findViewById(R.id.tvFechaLista);
			item.calorias = (TextView) fila.findViewById(R.id.tvCaloriasLista);
			item.nombreActividad = (TextView) fila.findViewById(R.id.tvTituloActividadLista);
			item.distancia = (TextView) fila.findViewById(R.id.tvDistanciaLista);
			fila.setTag(item);
		}
		else {
			item = (ItemActividad) fila.getTag();
		}
		
		Actividad actividad = datos.get(posicion);

		Drawable image = null;
		image =  new BitmapDrawable(BitmapFactory.decodeByteArray(actividad.getMapa(), 0, actividad.getMapa().length));
		
		item.mapa.setImageDrawable(image);
		item.fechaActividad.setText(actividad.getFecha());
		item.calorias.setText(String.valueOf(new DecimalFormat("#.##").format(actividad.getCaloriasQuemadas())) + "kc");
		item.nombreActividad.setText(actividad.getNombre());
		item.distancia.setText(String.valueOf(new DecimalFormat("#.##").format(actividad.getDistanciaRecorrida())) + "metros");
		return fila;
	}

}
