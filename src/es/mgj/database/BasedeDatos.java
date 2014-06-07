package es.mgj.database;

import static android.provider.BaseColumns._ID;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import es.mgj.base.Rutina;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.text.format.DateFormat;

public class BasedeDatos extends SQLiteOpenHelper{
	
	Context contexto;

	public BasedeDatos(Context context) {
		super(context, "actividades.db", null, 1);
		this.contexto = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE actividad (" 
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,fecha TEXT, calorias REAL"
						+ ",distancia REAL,duracion REAL, mapa BLOB, nombre TEXT, tipo TEXT);");
		
		db.execSQL("CREATE TABLE datos (ID INTEGER,nombre TEXT,fecha_nacimiento TEXT"
						+ ",sexo TEXT, altura REAL, objetivo TEXT, peso TEXT);");
		
		db.execSQL("CREATE TABLE alimento (" 
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,fecha TEXT, calorias REAL"
						+ ", nombre TEXT);");
		
		db.execSQL("CREATE TABLE rutina (" 
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT, repeticiones REAL"
						+ ", duracionfase1 REAL, duracionfase2 REAL, duracionfase3 REAL);");
		
		
		crearDatos(db);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS actividad");
		db.execSQL("DROP TABLE IF EXISTS datos");
		db.execSQL("DROP TABLE IF EXISTS alimento");
		db.execSQL("DROP TABLE IF EXISTS rutina");
		onCreate(db);
		
	}
	
	public void nuevaActividad(String nombre, float distancia, float duracion, float calorias, Bitmap mapa, String tipo, String fecha) {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		mapa.compress(Bitmap.CompressFormat.PNG, 100, out);

		byte[] buffer=out.toByteArray();
		
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues valores = new ContentValues();
    	valores.put("nombre", nombre);
    	valores.put("distancia", distancia);
    	valores.put("mapa", buffer);
    	valores.put("calorias", calorias);
    	valores.put("duracion", duracion);
    	valores.put("tipo", tipo);
    	valores.put("fecha", fecha);
    	db.insertOrThrow("actividad", null, valores);
    }
	
	public Cursor getActividades() {

    	SQLiteDatabase db = this.getReadableDatabase();
    	String campos[] = {"_ID, nombre, fecha, duracion, distancia,calorias, mapa, tipo"};
    	Cursor cursor = db.query("actividad", campos, null, null, null, null, "fecha DESC");
    	return cursor;
    }
	
	public void eleminiarActividad(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("actividad", "_ID=" + id, null);
	}
	
	public void crearDatos(SQLiteDatabase db){
		
    	ContentValues valores = new ContentValues();
    	valores.put("nombre", "sin configurar");
    	valores.put("ID", 1);
    	db.insertOrThrow("datos", null, valores);
	}
	
	public void nuevoDatos(String nombre, String sexo, String fecha, String objetivo, float peso, float altura ){
		
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues valores = new ContentValues();
    	
    	valores.put("nombre", nombre);
    	valores.put("sexo", sexo);
    	valores.put("fecha_nacimiento", fecha);
    	valores.put("objetivo", objetivo);
    	valores.put("peso", peso);
    	valores.put("altura", altura);
    	db.update("datos", valores, "ID=1", null);
	}
	
	public Cursor getDatos() {

    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	String campos[] = {"ID, nombre, fecha_nacimiento, sexo, objetivo,peso, altura"};
    	Cursor cursor = db.query("datos", campos, null, null, null, null, "fecha_nacimiento DESC");
    	return cursor;
    }
	
	public void nuevoAlimento(String nombre, double calorias) {
					
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues valores = new ContentValues();
		
		Calendar gc = GregorianCalendar.getInstance();
		
		valores.put("fecha", String.valueOf(DateFormat.format("dd/MM/yy", gc)));
		valores.put("calorias", calorias);
		valores.put("nombre", nombre);
		db.insertOrThrow("alimento", null, valores);
	    
	}
	
	public Cursor getAlimentos() {

    	SQLiteDatabase db = this.getReadableDatabase();
    	String campos[] = {"_ID, nombre, fecha, calorias"};
    	Cursor cursor = db.query("alimento", campos, null, null, null, null, "fecha DESC");
    	return cursor;
    }
	
	
	public void eliminiarAlimento(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("alimento", "_ID=" + id, null);
	}
	
	public void nuevaRutina(Rutina rutina) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues valores = new ContentValues();
		
		valores.put("nombre", rutina.getNombre());
		valores.put("repeticiones", rutina.getRepeticiones());
		valores.put("duracionfase1", rutina.getDuracionFase1());
		valores.put("duracionfase2", rutina.getDuracionFase2());
		valores.put("duracionfase3", rutina.getDuracionFase3());
		db.insertOrThrow("rutina", null, valores);
	    
	}
	
	public Cursor getRutinas() {

    	SQLiteDatabase db = this.getReadableDatabase();
    	String campos[] = {"_ID, nombre, repeticiones, duracionfase1, duracionfase2, duracionfase3"};
    	Cursor cursor = db.query("rutina", campos, null, null, null, null, "nombre DESC");
    	return cursor;
    }
	
	
}
