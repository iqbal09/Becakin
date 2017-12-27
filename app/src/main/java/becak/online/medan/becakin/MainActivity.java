package becak.online.medan.becakin;

import android.net.ConnectivityManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    ImageView antar , belanja , riwayat;
    ImageView klik_antar, klik_belanja;


    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    ///tambahan script untuk login
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        session = new SessionManager(getApplicationContext());
        String sts = String.valueOf(session.isLoggedIn());

        if(sts.equals("true")) {

            setContentView(R.layout.pilihan_layanan);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setIcon(R.drawable.ab_maps);
            actionBar.setDisplayShowTitleEnabled(false);


            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("lat_antar", "");
            editor.putString("log_antar", "");
            editor.putString("alamat_antar", "");


            editor.putString("lat_tujuan", "");
            editor.putString("log_tujuan", "");
            editor.putString("alamat_tujuan", "");
            editor.commit();

            antar = (ImageView) findViewById(R.id.btn_antar);
            belanja = (ImageView) findViewById(R.id.btn_belanja);
            riwayat = (ImageView) findViewById(R.id.btn_riwayat);

            klik_antar =(ImageView)findViewById(R.id.klik_antarin);
            klik_belanja = (ImageView)findViewById(R.id.klik_belanjain);


            antar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, PanelAntarin.class);
                    startActivity(i);
                }
            });

            klik_antar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, PanelAntarin.class);
                    startActivity(i);
                }
            });

            belanja.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Coming Soon !!!", Toast.LENGTH_SHORT).show();
                }
            });

            klik_belanja.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Toast.makeText(getApplicationContext(), "Coming Soon !!!", Toast.LENGTH_SHORT).show();

                }

            });

            riwayat.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                   boolean koneksi = isNetworkConnected();
                       if(koneksi == true){
                           Intent i = new Intent(MainActivity.this, RiwayatOrderan.class);
                           startActivity(i);
                        }else {
                           Toast.makeText(getApplicationContext(), "Device tidak terkoneksi dengan internet" +
                                   " \n Silahkan cek koneksi internet anda :)", Toast.LENGTH_SHORT).show();
                        }


                }

            });

        }

        else {
            // user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            //	 Closing dashboard screen
            finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_front, menu);



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            // action with ID action_settings was selected
            case R.id.action_settings:
                Intent k = new Intent(MainActivity.this, Pengaturan.class);
                startActivity(k);
                break;

            default:
                break;
        }

        return true;
    }




    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }












}
