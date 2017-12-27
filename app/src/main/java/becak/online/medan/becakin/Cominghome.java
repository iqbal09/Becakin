package becak.online.medan.becakin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by iqbalhood on 13/02/16.
 */
public class Cominghome extends Activity {

    Button next;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comingsoon);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fontbecak.ttf");


        next = (Button) findViewById(R.id.tombolnext);

        next.setTypeface(myTypeface);

        // Login button Click Event
        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                // sukses buat pendaftaran
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });
    }
}
