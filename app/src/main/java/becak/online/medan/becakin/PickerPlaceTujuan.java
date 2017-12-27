package becak.online.medan.becakin;

/**
 * Created by iqbalhood on 13/01/16.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class PickerPlaceTujuan extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    AutoCompleteTextView av1;
    private GoogleMap map;
    Button pilihlokasi;
    Marker medan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lokasi_layout);

        //setting tampilan action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ab_maps);
        actionBar.setDisplayShowTitleEnabled(false);

        Intent myIntent = getIntent();
        String lat_real       = myIntent.getStringExtra("lat_real");
        String log_real       = myIntent.getStringExtra("log_real");

        Double LAT = Double.parseDouble(lat_real);
        Double LOG = Double.parseDouble(log_real);



        LatLng Medan = new LatLng(LAT, LOG);
        //LatLng Medan = new LatLng(3.567208, 98.654804);


        pilihlokasi = (Button)findViewById(R.id.PilihLokasi);
        av1 = (AutoCompleteTextView)findViewById(R.id.av1);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.maps))
                .getMap();


        medan = map.addMarker(new MarkerOptions().position(Medan));
        medan.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.markcak));


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Medan, 15));


        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                // Get the center of the Map.
                LatLng centerOfMap = map.getCameraPosition().target;
                // Update your Marker's position to the center of the Map.
                medan.setPosition(centerOfMap);
            }
        });



        pilihlokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double lt = medan.getPosition().latitude;
                double lg = medan.getPosition().longitude;

                String lat = String.valueOf(lt);
                String log = String.valueOf(lg);
                String nama_jalan = Jalan(medan.getPosition().latitude, medan.getPosition().longitude);


                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("lat_tujuan", lat);
                editor.putString("log_tujuan", log);
                editor.putString("alamat_tujuan", nama_jalan);

                editor.putString("save_lat_tujuan", lat);
                editor.putString("save_log_tujuan", log);
                editor.putString("save_alamat_tujuan", nama_jalan);
                editor.commit();

                //Toast.makeText(getApplicationContext(), nama_jalan, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PickerPlaceTujuan.this, Tentukan_Lokasi_Antar.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        av1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), nama_jalan, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PickerPlaceTujuan.this, Cari_Lokasi_Tujuan.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



        //Don't forget to Set draggable(true) to marker, if this not set marker does not drag.
        // map.addMarker(new MarkerOptions().position(crntLocationLatLng).icon(BitmapDescriptorFactory .fromResource(R.drawable.icon_my_location)).draggable(true));

    }

    // Ambil data jalan dari google map lang api
    public String Jalan(double lat , double logi){
        String street = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, logi, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();
                for (int j = 0; j < returnedAddress.getMaxAddressLineIndex(); j++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(j)).append(" ");
                }
                street = strReturnedAddress.toString();
            }
        } catch (IOException e) {
        }
        return street;
    }


}
