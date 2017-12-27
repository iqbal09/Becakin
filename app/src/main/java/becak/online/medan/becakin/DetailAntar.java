package becak.online.medan.becakin;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iqbalhood on 07/02/16.
 */
public class DetailAntar extends AppCompatActivity {

    TextView display_lokasi_jemput , display_lokasi_tujuan , display_harga, display_ancar2_jemput;
    ImageView lanjutkan;
    String pub_lokasi_jemput, pub_lokasi_tujuan, pub_jarak, pub_harga, pub_ancar2_jemput, pub_ancar2_tujuan,  pub_lat_jemput, pub_log_jemput, pub_lat_tujuan, pub_log_tujuan;
    String pub_email;

    // nama sharepreference login
    private static final String PREF_NAME = "Sesi";

    SharedPreferences sharedpreferences;

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();


    // url to membuat produk baru
    private static String url_tambah_pendaftaran = "http://www.becak-in.com/androidapi/pendaftaran/create_antarin.php";


    private static final String TAG_SUCCESS = "success";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_antar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.logobeta);
        actionBar.setDisplayShowTitleEnabled(false);

        Intent myIntent = getIntent(); // ambil data passing intent dari activty sebelumnya

        String lokasi_jemput    = myIntent.getStringExtra("lokasi_jemput"); // will return "lokasi_jemput"
        String lokasi_tujuan    = myIntent.getStringExtra("lokasi_tujuan"); // will return "lokasi_tujuan"

        String lat_jemput       = myIntent.getStringExtra("lat_jemput");
        String log_jemput       = myIntent.getStringExtra("log_jemput");
        String lat_tujuan       = myIntent.getStringExtra("lat_tujuan");
        String log_tujuan       = myIntent.getStringExtra("log_tujuan");

        String ancar2_jemput    = myIntent.getStringExtra("ancar2_jemput"); // will return "ancar2_jemput"
        String ancar2_tujuan    = myIntent.getStringExtra("ancar2_tujuan"); // will return "ancar2_tujuan"

        String jarak    = myIntent.getStringExtra("jarak");
        String harga    = myIntent.getStringExtra("harga"); // will return "lokasi_tujuan"

        //inisialisasi variabel dan id layout
        display_lokasi_jemput   = (TextView)findViewById(R.id.txt_detail_lokasi_jemput);
        display_lokasi_tujuan   = (TextView)findViewById(R.id.txt_detail_lokasi_tujuan);
        display_harga           = (TextView)findViewById(R.id.txt_detail_harga);
        display_ancar2_jemput   = (TextView)findViewById(R.id.display_ancar2_jemput);

        lanjutkan = (ImageView)findViewById(R.id.lanjutkan);

        //setting teks ke layout
        display_lokasi_jemput.setText(lokasi_jemput);
        display_lokasi_tujuan.setText(lokasi_tujuan);
        display_harga.setText(harga);
        display_ancar2_jemput.setText(ancar2_jemput);

        /// set nilai variabel global
        pub_lokasi_jemput   = lokasi_jemput;
        pub_lokasi_tujuan   = lokasi_tujuan;

        pub_lat_jemput      = lat_jemput;
        pub_log_jemput      = log_jemput;

        pub_lat_tujuan      = lat_tujuan;
        pub_log_tujuan      = log_tujuan;

        pub_jarak           = jarak;
        pub_harga           = harga;
        pub_ancar2_jemput   = ancar2_jemput;
        pub_ancar2_tujuan   = ancar2_tujuan;

        /* ambil data login email */
        sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String share_email =  sharedpreferences.getString("email", null);

        pub_email = share_email;



        //jika button lanjutkan di klik

        lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateNewProduct().execute();
            }
        });




    }



    /**
     * Background Async Task untuk membuat buku tamu baru
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * tampilkan progress dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailAntar.this);
            pDialog.setMessage("Sedang memproses pemesanan...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * buat bukutamu baru
         * */
        protected String doInBackground(String... args) {


            // parameter
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("jemput", pub_lokasi_jemput));
            params.add(new BasicNameValuePair("tujuan", pub_lokasi_tujuan));
            params.add(new BasicNameValuePair("koordinat_jemput", pub_lat_jemput + " , " +pub_log_jemput  ));
            params.add(new BasicNameValuePair("koordinat_tujuan",  pub_lat_tujuan + " , " +pub_log_tujuan));
            params.add(new BasicNameValuePair("ancar2_jemput",  pub_ancar2_jemput));
            params.add(new BasicNameValuePair("ancar2_tujuan",  pub_ancar2_tujuan));
            params.add(new BasicNameValuePair("jarak", pub_jarak));
            params.add(new BasicNameValuePair("harga", pub_harga));
            params.add(new BasicNameValuePair("email", pub_email));



            // json object
            JSONObject json = jsonParser.makeHttpRequest(url_tambah_pendaftaran,
                    "POST", params);

            // cek respon di logcat
            Log.d("Create Response", json.toString());

            // cek tag success
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // sukses buat pendaftaran
                    Intent i = new Intent(getApplicationContext(), OrderBerhasil.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    // tutup screen
                    finish();
                } else {
                    // jika gagal
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * jika proses selesai maka hentikan progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }

    }
























}
