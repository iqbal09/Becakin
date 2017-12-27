package becak.online.medan.becakin;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author creatorb
 *
 */

public class RegisterActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    String nama, email, phone, password;
    EditText inputName;
    EditText inputEmail;
    EditText inputPhone;
    EditText inputDesc;

    // url to membuat produk baru
    private static String url_tambah_pendaftaran = "http://www.becak-in.com/androidapi/pendaftaran/create_pendaftaran.php";

    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_register);

        // Edit Text
        inputName = (EditText) findViewById(R.id.registerName);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputDesc = (EditText) findViewById(R.id.registerPassword);
        inputPhone = (EditText) findViewById(R.id.registerPhone);

        // button untuk buat pendaftaran baru
        Button btnCreatePendaftaran = (Button) findViewById(R.id.btnRegister);

        // event jika di klik
        btnCreatePendaftaran.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // jalankan buat pendaftaran di background
                nama = inputName.getText().toString();
                email = inputEmail.getText().toString();
                password = inputDesc.getText().toString();
                phone  = inputPhone.getText().toString();
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
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Sedang membuat pendaftaran...");
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
            params.add(new BasicNameValuePair("name", nama));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("phone", phone));

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
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
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