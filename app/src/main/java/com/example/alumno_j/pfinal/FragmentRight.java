package com.example.alumno_j.pfinal;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikip on 21/06/2017.
 */

public class FragmentRight extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,OnMapReadyCallback,GoogleMap.OnMapLoadedCallback ,LocationListener{

    public static final String ARG_PERSONA = "arg_persona";
    private final int REQUEST_LOCATION_CODE = 1;

    GoogleMap mGoogleMap;
    MapView mMapView;
    private Marker mMarker;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private Persona persona;
    EditText etNombre,etApellido,etDireccion, etEdad,etDocumento,etTip_doc,etFecha_nac;
    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        
        etNombre= (EditText) view.findViewById(R.id.etNombre);
        etApellido=(EditText)view.findViewById(R.id.etApellido); 
        etDireccion= (EditText) view.findViewById(R.id.etDireccion);
        etEdad= (EditText) view.findViewById(R.id.etEdad);
        etDocumento= (EditText) view.findViewById(R.id.etDocumento);
        etTip_doc= (EditText) view.findViewById(R.id.etTip_doc);
        etFecha_nac= (EditText) view.findViewById(R.id.etFecha_nac);
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getActivity(), R.array.tipos , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);

        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient
                    .Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();


        MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(this);
        getFragmentManager().beginTransaction()
                .replace(R.id.fMap, mapFragment)
                .commit();


        Bundle bundle = this.getArguments();
        if (bundle != null ) {

            persona= bundle.getParcelable(ARG_PERSONA);
            setData();
            Uneditable();
        }
        return view;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {


            case R.id.agregar:
                limpiarCampos();
                item.setVisible(false);

                return true;

            case R.id.editar:

                    item.setVisible(false);
                    Toast.makeText(getActivity(), "Editar puto", Toast.LENGTH_LONG).show();

                    Editable();

                return true;
            case R.id.guardar:
                Toast.makeText(getActivity(), "Guardar", Toast.LENGTH_LONG).show();

                //SI LA VARIABLE GLOBAL ES NULA QUEIRE DECIR QUE ES MODO INSERT
                if(persona ==null) {

                    //INICIALIZACION DE UNA NUEVA VARIABLE Y NO SE LE ASIGNA UN ID PORQUE ES AUTOGENERADO
                   Persona persona = new Persona();
                    persona.setNombre(etNombre.getText().toString().trim());
                    persona.setApellido(etApellido.getText().toString().trim());
                    persona.setDocumento(etDocumento.getText().toString().trim());
                    persona.setEdad(etEdad.getText().toString().trim());
                    persona.setDireccion(etDireccion.getText().toString().trim());
                    persona.setTip_doc(etTip_doc.getText().toString().trim());
                   // persona.setAge(etRegisterAge.getText().toString().trim());

                    //INICIALIZACION DE DAO DE ACCESO A DATOS
                    PersonaDao personaDAO = new PersonaDao(getActivity());
                    //INSERT EN LA TABLA STUDENT DE UN STUDENT
                    personaDAO.insert(persona);
                    //LOG QUE IMPRIME EL ID GENERADO
                    Log.d("persona", persona.getId() + "");

                }else{
                    persona.setNombre(etNombre.getText().toString().trim());
                    persona.setApellido(etApellido.getText().toString().trim());
                    persona.setEdad(etEdad.getText().toString().trim());
                    persona.setDocumento(etDocumento.getText().toString().trim());
                    persona.setDireccion(etDireccion.getText().toString().trim());
                    persona.setTip_doc(etTip_doc.getText().toString().trim());
                    //INICIALIZACION DE DAO DE ACCESO A DATOS
                    PersonaDao personaDao = new PersonaDao(getActivity());
                    //UPDATE EN LA TABLA STUDENT DE UN STUDENT, NO MODIFICAR EL ID PORQUE SINO NO ENCONTRARÍA EL REGISTRO
                    personaDao.update(persona);

                }
                limpiarCampos();
                Uneditable();
                return true;

            case R.id.cerrar:
                Toast.makeText(getActivity(), "Chau", Toast.LENGTH_LONG).show();
                logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        PreferencesHelper.signOut(getActivity());
        startActivity(new Intent(getActivity(), LoginActivity.class));

    }

    public  void Uneditable()
    {
        etDireccion.setFocusable(false);
        etDireccion.setClickable(false);
        etDireccion.setFocusableInTouchMode(false);
        
        etNombre.setFocusable(false);
        etNombre.setClickable(false);
        etNombre.setFocusableInTouchMode(false);
        
        etApellido.setFocusable(false);
        etApellido.setClickable(false);
        etApellido.setFocusableInTouchMode(false);

        etEdad.setFocusable(false);
        etEdad.setClickable(false);
        etEdad.setFocusableInTouchMode(false);

        etDocumento.setFocusable(false);
        etDocumento.setClickable(false);
        etDocumento.setFocusableInTouchMode(false);

        etTip_doc.setFocusable(false);
        etTip_doc.setClickable(false);
        etTip_doc.setFocusableInTouchMode(false);

        etFecha_nac.setFocusable(false);
        etFecha_nac.setClickable(false);
        etFecha_nac.setFocusableInTouchMode(false);
    }
    public  void Editable()
    {
        etDireccion.setFocusable(true);
        etDireccion.setClickable(true);
        etDireccion.setFocusableInTouchMode(true);
        
        etNombre.setFocusable(true);
        etNombre.setClickable(true);
        etNombre.setFocusableInTouchMode(true);

        etApellido.setFocusable(true);
        etApellido.setClickable(true);
        etApellido.setFocusableInTouchMode(true);

        etEdad.setFocusable(true);
        etEdad.setClickable(true);
        etEdad.setFocusableInTouchMode(true);

        etDocumento.setFocusable(true);
        etDocumento.setClickable(true);
        etDocumento.setFocusableInTouchMode(true);

        etTip_doc.setFocusable(true);
        etTip_doc.setClickable(true);
        etTip_doc.setFocusableInTouchMode(true);

        etFecha_nac.setFocusable(true);
        etFecha_nac.setClickable(true);
        etFecha_nac.setFocusableInTouchMode(true);
    }


    private void limpiarCampos()
    {Editable();
        etNombre.setText("");
        etApellido.setText("");
        etDireccion.setText("");
        etEdad.setText("");
        etDocumento.setText("");
        etTip_doc.setText("");
        etFecha_nac.setText("");
        etNombre.requestFocus();
      //  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
       // imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

    }


    private void setData() {
        etNombre.setText(persona.getNombre());
        etApellido.setText(persona.getApellido());
        etDireccion.setText(persona.getDireccion());
        etEdad.setText(persona.getEdad());
        etDocumento.setText(persona.getDocumento());

        etTip_doc.setText(persona.getTip_doc());

        etFecha_nac.setText(persona.getFec_nac());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mGoogleMap= googleMap;
        mGoogleMap.setOnMapLoadedCallback(this);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mGoogleMap.setMyLocationEnabled(false);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onLocationChanged(Location location) {


        if (location != null) {
            mLocation = location;
            Log.d("location", location.getLatitude() + ", " + location.getLongitude());
            if (mGoogleMap != null) {
                if (mMarker == null)
                  mMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Mi ubicacion").draggable(true).flat(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                else
                   mMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17f));
//                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),17f));
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
        } else
            Log.d("location", "null");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        createLocationRequest();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        if (location != null)
//            Log.d("location", location.getLatitude() + ", " + location.getLongitude());
//        else
//            Log.d("location","null");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
//                final LocationSettingsStatusCodes
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                    case LocationSettingsStatusCodes.SUCCESS:
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(getActivity(), REQUEST_LOCATION_CODE);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
    }
}
