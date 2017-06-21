package com.example.alumno_j.pfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mikip on 20/06/2017.
 */

public class PersonaDao {

    private static final String TABLE = "Persona";
    private static final String COL_ID = "Id";
    private static final String COL_NOMBRE = "Nombre";
    private static final String COL_APELLIDO = "Apellido";
    private static final String COL_DIRECCION = "Direccion";
    private static final String COL_EDAD = "Edad";
    private static final String COL_DOCUMENTO = "Documento";
    private static final String COL_TIP_DOC = "Tip_doc";
    private static final String COL_FEC_NAC ="Fec_nac" ;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;
    
    
    public PersonaDao(Context context) {
        mContext = context;
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public void insert(Persona persona) {
        //INICIALIZANDO EL CONTENDOR DE LA DATA DEL STUDENT
        ContentValues cv = new ContentValues();
        cv.put(COL_NOMBRE, persona.getNombre());
        cv.put(COL_APELLIDO, persona.getApellido());
        cv.put(COL_DIRECCION, persona.getDireccion());
        cv.put(COL_EDAD,persona.getEdad());
        cv.put(COL_DOCUMENTO,persona.getDocumento());
        cv.put(COL_TIP_DOC,persona.getTip_doc());
        cv.put(COL_FEC_NAC,persona.getFec_nac());

        //INSERTANDOLA A LA BASE DE DATOS
        long id = mDatabaseHelper.openDataBase().insert(TABLE, null, cv);
        persona.setId(id);
    }
    public void update(Persona persona) {
        //INICIALIZANDO EL CONTENDOR DE LA DATA DEL STUDENT
        ContentValues cv = new ContentValues();
        cv.put(COL_NOMBRE, persona.getNombre());
        cv.put(COL_APELLIDO, persona.getApellido());
        cv.put(COL_DIRECCION, persona.getDireccion());
        cv.put(COL_EDAD,persona.getEdad());
        cv.put(COL_DOCUMENTO,persona.getDocumento());
        cv.put(COL_TIP_DOC,persona.getTip_doc());
        cv.put(COL_FEC_NAC,persona.getFec_nac());
        //ACTUALIZANDO LA BASE DE DATOS HACIENDO UN WHERE POR LA COLUMNA STUDENT_ID
        mDatabaseHelper.openDataBase().update(TABLE, cv, COL_ID + " = ?", new String[]{String.valueOf(persona.getId())});
    }
    public void delete(Persona persona) {
        mDatabaseHelper.openDataBase().delete(TABLE, COL_ID + " = ?", new String[]{String.valueOf(persona.getId())});
    }
    public Persona get(Persona persona) {
        //OBTENIENDO UN STUDENT ESPECÍFICO
        Cursor cursor = mDatabaseHelper.openDataBase().query(TABLE, null, COL_ID + " = ?", new String[]{String.valueOf(persona.getId())}, null, null, null, "1");
        Persona personaBD = null;
        if (cursor.moveToFirst())
            personaBD = transformCursorToPersona(cursor);

        //SIEMPRE SE DEBE CERRAR EL CURSOR
        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return personaBD;
    }

    public ArrayList<Persona> getAll() {
        //OBTENIENDO TODOS LOS STUDENT
        Cursor cursor = mDatabaseHelper.openDataBase().query(TABLE, null, null, null, null, null, null);
        ArrayList<Persona> lstPersona = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                lstPersona.add(transformCursorToPersona(cursor));
            } while (cursor.moveToNext());
        }

        //SIEMPRE SE DEBE CERRAR EL CURSOR
        if (cursor != null && !cursor.isClosed())
            cursor.close();

        return lstPersona;
    }

    private Persona transformCursorToPersona(Cursor cursor) {
        Persona persona = new Persona();
        persona.setId(cursor.isNull(cursor.getColumnIndex(COL_ID)) ? 0 : cursor.getLong(cursor.getColumnIndex(COL_ID)));
        persona.setNombre(cursor.isNull(cursor.getColumnIndex(COL_NOMBRE)) ? null : cursor.getString(cursor.getColumnIndex(COL_NOMBRE)));
        persona.setApellido(cursor.isNull(cursor.getColumnIndex(COL_APELLIDO)) ? null : cursor.getString(cursor.getColumnIndex(COL_APELLIDO)));
        persona.setDireccion(cursor.isNull(cursor.getColumnIndex(COL_DIRECCION)) ? null : cursor.getString(cursor.getColumnIndex(COL_DIRECCION)));
        persona.setEdad(cursor.isNull(cursor.getColumnIndex(COL_EDAD)) ? null : cursor.getString(cursor.getColumnIndex(COL_EDAD)));
        persona.setDocumento(cursor.isNull(cursor.getColumnIndex(COL_DOCUMENTO)) ? null : cursor.getString(cursor.getColumnIndex(COL_DOCUMENTO)));
        persona.setTip_doc(cursor.isNull(cursor.getColumnIndex(COL_TIP_DOC)) ? null : cursor.getString(cursor.getColumnIndex(COL_TIP_DOC)));
        persona.setFec_nac(cursor.isNull(cursor.getColumnIndex(COL_FEC_NAC)) ? null : cursor.getString(cursor.getColumnIndex(COL_FEC_NAC)));

        return persona;
        
    }
}
