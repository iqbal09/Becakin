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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by iqbalhood on 25/03/16.
 */
public class Help_Feedback extends AppCompatActivity {

    String pub_pesan;

    Button btnSubmit;

    // nama sharepreference login
    private static final String PREF_NAME = "Sesi";

    SharedPreferences sharedpreferences;

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();


    // url to membuat produk baru
    private static String url_tambah_pesan = "http://www.becak-in.com/androidapi/pendaftaran/create_help.php";


    private static final String TAG_SUCCESS = "success";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_feedback);

        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new CreateNewProduct().execute();
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
            pDialog = new ProgressDialog(Help_Feedback.this);
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
            params.add(new BasicNameValuePair("jemput", pub_pesan));




            // json object
            JSONObject json = jsonParser.makeHttpRequest(url_tambah_pesan,
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
