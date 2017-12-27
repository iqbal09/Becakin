package becak.online.medan.becakin;

import android.location.Location;

import com.google.android.gms.location.LocationListener;

/**
 * Created by iqbalhood on 13/02/16.
 */
public class LokasiGPS implements LocationListener {

    double lat_gps, log_gps;

    @Override
    public void onLocationChanged(Location location) {

    lat_gps = location.getLatitude();
    log_gps = location.getLongitude();





    }
}
