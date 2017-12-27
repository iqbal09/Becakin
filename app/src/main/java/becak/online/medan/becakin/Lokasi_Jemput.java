package becak.online.medan.becakin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by iqbalhood on 22/12/15.
 */
public class Lokasi_Jemput extends AppCompatActivity implements LocationListener  {


    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    Button btn_peta ;
    ImageView pilih_dari_peta, btn_cari_lokasi;

    //location fucking initialitation
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    EditText ed_input_lokasi;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    public String  LATI , LOGI ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lokasi_antar);

        //setting tampilan action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ab_maps);
        actionBar.setDisplayShowTitleEnabled(false);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        ed_input_lokasi = (EditText) findViewById(R.id.ed_input_lokasi);
        btn_cari_lokasi = (ImageView) findViewById(R.id.btn_cari_lokasi);

        ed_input_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    // Toast.makeText(getApplicationContext(), "LOKASI TIDAK DITEMUKAN SILAHKAN HIDUPKAN GPS ANDA", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Lokasi_Jemput.this, Cari_Lokasi_Jemput.class);
                    startActivity(i);





            }
        });

        btn_cari_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Toast.makeText(getApplicationContext(), "LOKASI TIDAK DITEMUKAN SILAHKAN HIDUPKAN GPS ANDA", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Lokasi_Jemput.this, Cari_Lokasi_Jemput.class);
                startActivity(i);





            }
        });



        pilih_dari_peta = (ImageView) findViewById(R.id.pilih_dari_peta);

        pilih_dari_peta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(LATI != null && !LATI.isEmpty()){

                    Intent i = new Intent(Lokasi_Jemput.this, PickerPlace.class);
                    i.putExtra( "lat_real" ,  LATI );
                    i.putExtra( "log_real",   LOGI);
                    //i.putExtra( "lat_real" ,  "3.567208" );
                   // i.putExtra( "log_real",   "98.654804");
                    startActivity(i);

                }
                else{
                   // Toast.makeText(getApplicationContext(), "LOKASI TIDAK DITEMUKAN SILAHKAN HIDUPKAN GPS ANDA", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Lokasi_Jemput.this, PickerPlace.class);

                    i.putExtra( "lat_real" ,  "3.567208" );
                    i.putExtra( "log_real",   "98.654804");

                    startActivity(i);



                }

            }
        });

    }


    @Override
    public void onLocationChanged(Location location) {

        LATI = String.valueOf(location.getLatitude());
        LOGI = String.valueOf(location.getLongitude());

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), PanelAntarin.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(myIntent, 0);
        finish();
        return true;

    }








}
