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
 * Created by iqbalhood on 24/03/16.
 */
public class Password  extends AppCompatActivity {

    // properti dari data detail list orderan
    EditText  OldPassword;
    EditText  NewPassword;
    EditText  RetypeNewPassword;

    String old_pass, new_pass, renew_pass, real_pass;


    ImageView btn_simpan_password;


    //ambil data email user untuk dicocokkan ke data pendaftaran
    String Email_User;

    // nama sharepreference login
    private static final String PREF_NAME = "Sesi";

    SharedPreferences sharedpreferences;


    // Progress Dialog
    private ProgressDialog pDialog;

    // buat json object
    JSONParser jParser = new JSONParser();

    // url untuk halaman single dari bukutamu
    private static final String url_ubah_password = "http://www.becak-in.com/androidapi/pendaftaran/ubah_password.php";


    // node node json
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PROFIL = "profil";
    private static final String TAG_PASSWORD = "password";





    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_action_logo_beta);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        btn_simpan_password = (ImageView)findViewById(R.id.btn_simpan_password);

         /* ambil data login email */
        sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String share_email =  sharedpreferences.getString("email", null);

        Email_User = share_email;



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);





        btn_simpan_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OldPassword         = (EditText) findViewById(R.id.OldPassword);
                NewPassword         = (EditText) findViewById(R.id.NewPassword);
                RetypeNewPassword   = (EditText) findViewById(R.id.RetypeNewPassword);

                old_pass = OldPassword.getText().toString();
                new_pass = NewPassword.getText().toString();
                renew_pass = RetypeNewPassword.getText().toString();


                if (new_pass != renew_pass){

                    Toast.makeText(getApplicationContext(), "Password Tidak Sama Silahkan Ulangi Pengetikan Password", Toast.LENGTH_SHORT).show();

                }
                else {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    real_pass = new_pass;
                    new UbahPassword().execute();

                }






            }
        });


    }






    /**
     * async task untuk menyimpan pendaftaran
     * */
    class UbahPassword extends AsyncTask<String, String, String> {

        /**
         * jika proses di background akan berjalan maka tampilkan progress
         * dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Password.this);
            pDialog.setMessage("Menyimpan data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * menyimpan data
         * */
        protected String doInBackground(String... args) {



            // buat parameter
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", Email_User));
            params.add(new BasicNameValuePair("pass", old_pass));
            params.add(new BasicNameValuePair("new_pass", real_pass));

            // kirim data pembaharuan melalui http request
            JSONObject json = jParser.makeHttpRequest(
                    url_ubah_password, "POST", params);

            // cek tag success json
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // sukses di perbaharui
                    Intent i = getIntent();
                    // jika sukses maka kirimkan kode 100
                    setResult(100, i);
                    finish();
                } else {
                    // jika tidak maka gagal
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * jika pekerjaan di background selesai maka hentikan progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // hentikan progress dialog untuk menympan data
            pDialog.dismiss();
        }
    }






















}
