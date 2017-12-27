package becak.online.medan.becakin;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by iqbalhood on 27/03/16.
 */
public class TOS  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tos);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setIcon(R.drawable.ab_maps);
        actionBar.setDisplayShowTitleEnabled(false);

        WebView webView = (WebView) findViewById(R.id.webview);

        webView.loadUrl("http://becak-in.com/toc.php");
    }






}
