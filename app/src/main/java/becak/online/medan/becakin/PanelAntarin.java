package becak.online.medan.becakin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by iqbalhood on 12/01/16.
 */
public class PanelAntarin extends AppCompatActivity implements LocationListener {

    ImageView antar_orang, antar_barang;
    TextView koordinat;


    //location fucking initialitation
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    ImageView logo_antarin_orang_barang, txt_antarin_orang_barang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilih_layanan_antarin);


        //setting tampilan action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ab_maps);
        actionBar.setDisplayShowTitleEnabled(false);

        antar_orang = (ImageView) findViewById(R.id.btn_panel_antar_orang);
        logo_antarin_orang_barang = (ImageView) findViewById(R.id.logo_antarin_orang_barang);
        txt_antarin_orang_barang = (ImageView) findViewById(R.id.txt_antarin_orang_barang);


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






        antar_orang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(PanelAntarin.this, Tentukan_Lokasi_Antar.class);
                startActivity(i);
            }
        });


        logo_antarin_orang_barang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //display in short period of time
                Toast.makeText(getApplicationContext(), "Coming Soon !!!", Toast.LENGTH_SHORT).show();


            }
        });

        txt_antarin_orang_barang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //display in short period of time
                Toast.makeText(getApplicationContext(), "Coming Soon !!!", Toast.LENGTH_SHORT).show();


            }
        });














    }




    @Override
    public void onLocationChanged(Location location) {

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
        Log.d("Latitude","status");
    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(myIntent, 0);
        finish();
        return true;

    }






}
