package becak.online.medan.becakin;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {
	String mail , pass ;
	Button btnLogin;
	Button btnLinkToRegister;
	EditText inputEmail;
	EditText inputPassword;
	TextView loginErrorMsg;
	Intent a;
	SessionManager session;
	String url, success;

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();


	// url to membuat produk baru
	private static String url_tambah_pendaftaran = "http://www.becak-in.com/androidapi/pendaftaran/login.php";
	private static final String TAG_SUCCESS = "success";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.panel_login);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setIcon(R.drawable.ic_action_logo_beta);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		
		
		session = new SessionManager(getApplicationContext());
		Toast.makeText(getApplicationContext(),
				"User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG)
				.show();
		

		
		
		// Importing all assets like buttons, text fields
		inputEmail = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				mail = inputEmail.getText().toString();
				pass  = inputPassword.getText().toString();

				new CreateNewProduct().execute();
								
				
			}
		});

		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
				finish();
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
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Silahkan Tunggu, Proses Login...");
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

			params.add(new BasicNameValuePair("email", mail));
			params.add(new BasicNameValuePair("password", pass));




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
					Intent i = new Intent(getApplicationContext(), Cominghome.class);
					startActivity(i);
					session.createLoginSession(mail, pass);
					Log.e("ok", " ambil data");

					// tutup screen
					finish();
				} else {
					Toast.makeText(getApplicationContext(), " maaf email/password salah", Toast.LENGTH_LONG).show();
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