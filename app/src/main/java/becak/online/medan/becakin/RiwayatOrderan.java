package becak.online.medan.becakin;

/**
 * Created by iqbalhood on 05/03/16.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class RiwayatOrderan  extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    //ambil data email user untuk dicocokkan ke data pendaftaran
    String Email_User;

    // nama sharepreference login
    private static final String PREF_NAME = "Sesi";

    SharedPreferences sharedpreferences;




    // buat json object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> bukutamuList;

    // url untuk get semua buku tamu
    private static String url_semua_bukutamu = "http://www.becak-in.com/androidapi/pendaftaran/get_all_pendaftaran.php";

    // JSON Node
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ANTARIN = "antarin";
    private static final String TAG_PID = "pid";
    private static final String TAG_WAKTU = "waktu";
    private static final String TAG_TUJUAN = "tujuan";

    // pendaftaran JSONArray
    JSONArray pendaftaran = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riwayat_orderan);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_action_logo_beta);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        /* ambil data login email */
        sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String share_email =  sharedpreferences.getString("email", null);

        Email_User = share_email;



        // Hashmap untuk ListView
        bukutamuList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadSemuaBukuTamu().execute();

        //lvItem = (ListView)findViewById(R.id.lv_item);

        // Get listview
        ListView lv = (ListView)findViewById(R.id.lv_item);

        // select single bukutamu
        // Jalankan tampilan edit buku tamu
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // dapatkan nilai dari list pendaftaran
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();

                // Memulai aktifitas baru
                Intent in = new Intent(getApplicationContext(),
                        DetailListOrderan.class);
                // kirimkan pid ke activity selanjutnya
                in.putExtra(TAG_PID, pid);

                // memulai activity baru dengan mnghrap bbrapa kembalian response

                startActivityForResult(in, 100);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            // action with ID action_settings was selected
            case R.id.action_favorite:
                new LoadSemuaBukuTamu().execute();
                break;
            case R.id.action_settings:
                Intent c = new Intent(getApplicationContext(), Pengaturan.class);
                c.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(c, 0);
                finish();
                break;
            default:
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(myIntent, 0);
                finish();
                break;
        }

        return true;
    }








    /**
     * Background Async Task untuk menampilkan semua daftar bukutamu menggunakan http request
     * */
    class LoadSemuaBukuTamu extends AsyncTask<String, String, String> {

        /**
         * sebelum melakukan thread di background maka jalankan progres dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RiwayatOrderan.this);
            pDialog.setMessage("Mohon tunggu, Loading Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * dapetkan semua produk dari get url di background
         * */
        protected String doInBackground(String... args) {
            // Buat Parameter
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id_user", Email_User));
            // ambil json dari url
            JSONObject json = jParser.makeHttpRequest(url_semua_bukutamu, "GET", params);


            // cek logcat untuk response dari json
            Log.d("Semua Buku Tamu: ", json.toString());

            try {
                // cek jika tag success
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // data ditemukan
                    // ambil array dari bukutamu
                    pendaftaran = json.getJSONArray(TAG_ANTARIN);

                    // tampilkan perulangan semua produk
                    for (int i = 0; i < pendaftaran.length(); i++) {
                        JSONObject c = pendaftaran.getJSONObject(i);

                        // simpan pada variabel
                        String id = c.getString(TAG_PID);
                        String waktu = c.getString(TAG_WAKTU);
                        String tujuan = c.getString(TAG_TUJUAN);

                        // buat new hashmap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_WAKTU, waktu);
                        map.put(TAG_TUJUAN, tujuan);

                        // masukan HashList ke ArrayList
                        bukutamuList.add(map);
                    }
                } else {
                    // jika tidak ada data
                    // maka jalankan tambahkan buku tamu
                   // Intent i = new Intent(getApplicationContext(),
                     //       TambahBukuTamu.class);
                    // tutup semua proses sebelumnya
                    //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * jika pekerjaan di belakang layar selesai maka hentikan progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // hentikan progress ketika semua data didapat
            pDialog.dismiss();
            // perbarui screen
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * perbarui json ke arraylist
                     * */
                    ListView lvItem;
                    lvItem = (ListView)findViewById(R.id.lv_item);
                    ListAdapter adapter = new SimpleAdapter(
                            RiwayatOrderan.this, bukutamuList,
                            R.layout.list_order, new String[] { TAG_PID,
                            TAG_WAKTU, TAG_TUJUAN},
                            new int[] { R.id.pid, R.id.waktu , R.id.tujuan });
                    // perbarui list pendaftaran
                    lvItem.setAdapter(adapter);
                }
            });

        }

    }





























}
