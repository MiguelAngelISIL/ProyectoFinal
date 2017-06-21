package com.example.alumno_j.pfinal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mikip on 20/06/2017.
 */

public class Persona implements Parcelable {
    private long id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String edad;
    private String documento;
    private String tip_doc;
    private String fec_nac;

    protected Persona(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
        apellido = in.readString();
        direccion = in.readString();
        edad = in.readString();
        documento = in.readString();
        tip_doc = in.readString();
        fec_nac = in.readString();
    }

    public static final Creator<Persona> CREATOR = new Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };

    public Persona() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTip_doc() {
        return tip_doc;
    }

    public void setTip_doc(String tip_doc) {
        this.tip_doc = tip_doc;
    }

    public String getFec_nac() {
        return fec_nac;
    }

    public void setFec_nac(String fec_nac) {
        this.fec_nac = fec_nac;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(direccion);
        dest.writeString(edad);
        dest.writeString(documento);
        dest.writeString(tip_doc);
        dest.writeString(fec_nac);
    }
}
