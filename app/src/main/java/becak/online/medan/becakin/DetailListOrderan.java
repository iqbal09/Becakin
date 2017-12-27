package becak.online.medan.becakin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by iqbalhood on 06/03/16.
 */
public class DetailListOrderan extends AppCompatActivity {

    // properti dari data detail list orderan
    TextView  txtJemput;
    TextView  txtShowJemput;
    TextView  txtTujuan;
    TextView  tvNoOrder;
    TextView  tv_waktu_order;
    TextView  tv_show_harga;

    String pid;



    // Progress Dialog
    private ProgressDialog pDialog;

    // buat class json parser
    JSONParser jsonParser = new JSONParser();

    // url untuk halaman single dari bukutamu
    private static final String url_pendaftaran_details = "http://www.becak-in.com/androidapi/pendaftaran/get_pendaftaran_details.php";



    // node node json
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ANTARIN = "antarin";
    private static final String TAG_PID = "pid";
    private static final String TAG_WAKTU = "waktu";
    private static final String TAG_JEMPUT = "jemput";
    private static final String TAG_TUJUAN = "tujuan";
    private static final String TAG_NO_ORDER = "no_order";
    private static final String TAG_HARGA = "harga";




    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list_orderan);


        //setting tampilan action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ab_maps);
        actionBar.setDisplayShowTitleEnabled(false);


        // get semua daftar buku tamu dari intent
        Intent i = getIntent();

        // get daftar buku tamu dari intent by pendaftaran id (pid)
        pid = i.getStringExtra(TAG_PID);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // get komplit pendaftaran dari thread di background
        new GetPendaftaranDetails().execute();


    }





    /**
     * jalankan get semua product di background
     * */
    class GetPendaftaranDetails extends AsyncTask<String, String, String> {

        /**
         * jika memulai get activity maka jalankan progress dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailListOrderan.this);
            pDialog.setMessage("Mengambil Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * mulai jalankan get semua daftar dan jalankan di background
         * */
        protected String doInBackground(String... params) {

            // pembaharuan ui dari thread yg dijalankan
            runOnUiThread(new Runnable() {
                public void run() {
                    // Cek tag success
                    int success;
                    try {
                        // buat paramater
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("pid", pid));

                        // get detail dari daftar menggunakan http request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_pendaftaran_details, "GET", params);

                        // cek log kita dg json response
                        Log.d("Single Product Details", json.toString());

                        // tag success json
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // sukses mendapat detail daftar
                            JSONArray productObj = json
                                    .getJSONArray(TAG_ANTARIN); // JSON
                            // Array

                            // get objek daftar pertama dari json array
                            JSONObject pendaftaran = productObj
                                    .getJSONObject(0);

                            // temukan daftar menggunakan pid
                            txtJemput       = (TextView)    findViewById(R.id.tvLokasiJemPut);
                            txtTujuan       = (TextView)    findViewById(R.id.tvLokasiTujuan);
                            txtShowJemput   = (TextView)    findViewById(R.id.tvShowJemput);
                            tvNoOrder       = (TextView)    findViewById(R.id.tvNoOrder);
                            tv_waktu_order  = (TextView)    findViewById(R.id.tv_waktu_order);
                            tv_show_harga   = (TextView)    findViewById(R.id.tv_show_harga);

                            // tampilkan di edit text
                            txtJemput.setText(pendaftaran.getString(TAG_JEMPUT));
                            txtTujuan.setText(pendaftaran.getString(TAG_TUJUAN));
                            txtShowJemput.setText(pendaftaran.getString(TAG_JEMPUT));
                            tv_waktu_order.setText(pendaftaran.getString(TAG_WAKTU));
                            tvNoOrder.setText("NO ORDER "+pendaftaran.getString(TAG_NO_ORDER));

                            //format RUPIAH
                            double hrg = Double.parseDouble(pendaftaran.getString(TAG_HARGA));
                            DecimalFormat df = new DecimalFormat("#,###,##0.00");
                            df = new DecimalFormat("#,###,##0.00");
                            String rupiah = "Rp."+df.format(hrg);

                            tv_show_harga.setText(rupiah);


                        } else {
                            // pid tidak ditemukan
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * jika pekerjaan di background selesai maka hentikan progress yg
         * berjalan
         * **/
        protected void onPostExecute(String file_url) {
            // hentikan progress dialog untuk get
            pDialog.dismiss();
        }
    }






    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(myIntent, 0);
        finish();
        return true;

    }



}
