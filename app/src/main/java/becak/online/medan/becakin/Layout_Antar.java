package becak.online.medan.becakin;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by iqbalhood on 21/12/15.
 */
public class Layout_Antar  extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    Button btn_lokasi_jemput, btn_loaksi_tujuan , btn_selanjutnya;
    EditText ed_jemput, ed_tujuan;
    String Jarak =""; String Harga ="";
    TextView harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_antar);

        //setting tampilan action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ab_maps);
        actionBar.setDisplayShowTitleEnabled(false);


        //inisialisasi textview harga
        harga = (TextView)findViewById(R.id.tv_harga);
        harga.setVisibility(View.INVISIBLE);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // Nama Jalan Lokasi Jemput dan Tujuan
        String lokasi_jemput =  sharedpreferences.getString("alamat_antar", null);
        String lokasi_tujuan =  sharedpreferences.getString("alamat_tujuan", null);

        // kordinat jemput dan tujuan (string)
        final String lat_j = sharedpreferences.getString("lat_antar", null);
        final String log_j = sharedpreferences.getString("log_antar", null);

        final String lat_t = sharedpreferences.getString("lat_tujuan", null);
        final String log_t = sharedpreferences.getString("log_tujuan", null);



        if(lat_j == null || lat_j=="" || log_j == null || log_j == "" ||lat_t == null || lat_t=="" || log_t == null || log_t == ""  ){

            harga.setVisibility(View.INVISIBLE);


        }

        else{

            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(lat_j, log_j, lat_t, log_t);

            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);

            harga.setVisibility(View.VISIBLE);

        }


        btn_lokasi_jemput = (Button)findViewById(R.id.btn_lokasi_jemput);
        btn_loaksi_tujuan = (Button)findViewById(R.id.btn_lokasi_tujuan);
        btn_selanjutnya = (Button)findViewById(R.id.selanjutnya);


        //data ancar2....
        ed_jemput = (EditText)findViewById(R.id.ed_jemput);
        ed_tujuan = (EditText)findViewById(R.id.ed_tujuan);




        if(lokasi_jemput == null || lokasi_jemput == ""){
            btn_lokasi_jemput.setText("Pilih Lokasi Jemput");
        }else{
            btn_lokasi_jemput.setText(lokasi_jemput);
        }

        if(lokasi_tujuan == null || lokasi_tujuan == ""){
            btn_loaksi_tujuan.setText("Pilih Lokasi Tujuan");
        }else{
            btn_loaksi_tujuan.setText(lokasi_tujuan);
        }


        btn_lokasi_jemput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Layout_Antar.this, Lokasi_Jemput.class);
                startActivity(i);
            }
        });

        btn_loaksi_tujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Layout_Antar.this, Lokasi_Tujuan.class);
                startActivity(i);
            }
        });

        btn_selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Layout_Antar.this, DetailAntar.class);

                //ambil alamat jalan
                String lokasi_jemput_send = btn_lokasi_jemput.getText().toString();
                String lokasi_tujuan_send = btn_loaksi_tujuan.getText().toString();

                //ambil lokasi ancar2
                String ancar2_jemput = ed_jemput.getText().toString();
                String ancar2_tujuan = ed_tujuan.getText().toString();

                //kirim data alamat
                i.putExtra("lokasi_jemput" ,   lokasi_jemput_send );
                i.putExtra( "lokasi_tujuan" ,  lokasi_tujuan_send );


                //kirim data tujuan
                i.putExtra( "ancar2_jemput" ,  ancar2_jemput );
                i.putExtra( "ancar2_tujuan" ,  ancar2_tujuan );

                //kirim juga data koordinat

                i.putExtra( "lat_jemput" ,  lat_j );
                i.putExtra( "log_jemput" ,  log_j );

                i.putExtra( "lat_tujuan" ,  lat_t );
                i.putExtra( "log_tujuan" ,  log_t );

                i.putExtra( "jarak" ,  Jarak );
                i.putExtra( "harga" ,  Harga );
                startActivity(i);
            }
        });






    }  //ending fungsi onCreate


    //setting URL yang akan di kirim Ke Google API
    private String getDirectionsUrl(String lat_A , String log_A , String lat_B , String log_B){

        // Origin of route
        String str_origin = "origin="+lat_A+","+log_A;

        // Destination of route
        String str_dest = "destination="+lat_B+","+log_B;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;


        return url;
    }


    /** download  json data dari url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("gagal", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }


    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;

            String distance = "";




            if(result.size()<1){
                Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
                return;
            }


            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
                    if(j==0) {    // Get distance from the list
                        distance = (String) point.get("distance");
                        continue;
                    }
               }
            }

            String km_potong = removeLastChar(distance);
            String st_harga = Harga(km_potong);

            Jarak = distance;
            Harga = st_harga;

            double hrg = Double.parseDouble(st_harga);
            DecimalFormat df = new DecimalFormat("#,###,##0.00");
            df = new DecimalFormat("#,###,##0.00");
            String rupiah = "Rp."+df.format(hrg);

            harga.setText("Jarak:"+ distance + ",Harga:" + rupiah);



        }


        private String removeLastChar(String str) {
            return str.substring(0,str.length()-2);
        }

        private String Harga(String Jarak){

            int harga = 5000;
            float jarak = Float.parseFloat(Jarak);

            if(jarak >= 0 && jarak <= 2 ){

                harga = 7000;


            }else if(jarak >= 2 && jarak <= 3){
                harga = 10000;

            }else if(jarak >= 3 && jarak <= 4){
                harga = 15000;

            }else if(jarak >= 4 && jarak <= 5){
                harga = 20000;

            }else if(jarak >= 5 && jarak <= 6){
                harga = 20000;

            }else if(jarak >= 6 && jarak <= 7){
                harga = 25000;

            }else if(jarak >= 7 && jarak <= 8){
                harga = 25000;

            }else if(jarak >= 8 && jarak <= 9){
                harga = 30000;

            }else if(jarak >= 9 && jarak <= 10){
                harga = 30000;

            }else if(jarak >= 10 && jarak <= 11){
                harga = 35000;

            }else if(jarak >= 11 && jarak <= 12){
                harga = 35000;

            }else if(jarak >= 12 && jarak <= 13){
                harga = 40000;

            }else if(jarak >= 13 && jarak <= 14){
                harga = 45000;

            }else if(jarak >= 14 && jarak <= 15){
                harga = 45000;

            }else{
                int jrk = Integer.parseInt(Jarak);
                harga = (jrk - 14 ) * 3000;
            }

            String harga_string = String.valueOf(harga);

            return harga_string;

        }



    }







    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), PanelAntarin.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(myIntent, 0);
        finish();
        return true;

    }


















} //ending class utama