package becak.online.medan.becakin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by iqbalhood on 09/03/16.
 */
public class Pengaturan extends AppCompatActivity {

    Button btnProfil, btnGantiPassword, btnCredit, btnHELP, btnTOS, btnRate, btnLOGOUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pengaturan);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setIcon(R.drawable.ab_maps);
        actionBar.setTitle("PENGATURAN");
        actionBar.setDisplayShowTitleEnabled(false);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fontbecak.ttf");


        btnProfil           = (Button)findViewById(R.id.btnProfil);
        btnGantiPassword    = (Button)findViewById(R.id.btnGantiPassword);
        btnCredit           = (Button)findViewById(R.id.btnCredit);
        btnHELP             = (Button)findViewById(R.id.btnHELP);
        btnTOS              = (Button)findViewById(R.id.btnTOS);
        btnRate             = (Button)findViewById(R.id.btnRate);
        btnLOGOUT           = (Button)findViewById(R.id.btnLOGOUT);

        btnProfil.setTypeface(myTypeface);
        btnGantiPassword.setTypeface(myTypeface);
        btnCredit.setTypeface(myTypeface);
        btnHELP.setTypeface(myTypeface);
        btnTOS.setTypeface(myTypeface);
        btnRate.setTypeface(myTypeface);
        btnLOGOUT.setTypeface(myTypeface);

        //btnGantiPassword

        btnProfil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean koneksi = isNetworkConnected();
                if(koneksi == true){
                    Intent i = new Intent(Pengaturan.this, Profil.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Device tidak terkoneksi dengan internet" +
                            " \n Silahkan cek koneksi internet anda :)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGantiPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean koneksi = isNetworkConnected();
                if(koneksi == true){
                    Intent i = new Intent(Pengaturan.this, Password.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Device tidak terkoneksi dengan internet" +
                            " \n Silahkan cek koneksi internet anda :)", Toast.LENGTH_SHORT).show();
                }


            }





        });

        btnTOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean koneksi = isNetworkConnected();
                if(koneksi == true){
                    Intent i = new Intent(Pengaturan.this, TOS.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Device tidak terkoneksi dengan internet" +
                            " \n Silahkan cek koneksi internet anda :)", Toast.LENGTH_SHORT).show();
                }


            }





        });

        btnCredit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                    Toast.makeText(getApplicationContext(), "Coming Soon !!!", Toast.LENGTH_SHORT).show();
                }


        });

        btnHELP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                boolean koneksi = isNetworkConnected();
                if(koneksi == true){
                    Intent i = new Intent(Pengaturan.this, Help_Feedback.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Device tidak terkoneksi dengan internet" +
                            " \n Silahkan cek koneksi internet anda :)", Toast.LENGTH_SHORT).show();
                }


            }


        });




    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
