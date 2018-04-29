package org.ieselcaminas.alu53787365w.examjune;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener{
    private Button C1, C2, GPS;
    private EditText txfData1, txfData2;
    private double latitude, longitude;
    private String latitudeStr, longitudeStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        C1 = (Button) findViewById(R.id.btnC1);
        C2 = (Button) findViewById(R.id.btnC2);

        C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Calcula.class);
                txfData1 = (EditText) findViewById(R.id.txfData1);
                intent.putExtra("name", txfData1.getText().toString());
                intent.putExtra("id", 1234);
                startActivityForResult(intent, 1234);
            }
        });
        C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Calcula.class);
                txfData2 = (EditText) findViewById(R.id.txfData2);
                intent.putExtra("name", txfData2.getText().toString());
                intent.putExtra("id", 1235);
                startActivityForResult(intent, 1235);
            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, (LocationListener) this);

        IntentFilter filter = new IntentFilter(MyReceiver.PROCESS_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        MyReceiver receiver = new MyReceiver();
        registerReceiver(receiver, filter); //Registrem el objecte broadcast i el intentFilter
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode==1234 && resultCode==RESULT_OK) {
            Bundle extras = data.getExtras();
            String data1 = extras.getString("data");
            txfData1.setText(data1);
        }
        if (requestCode==1235 && resultCode==RESULT_OK) {
            Bundle extras = data.getExtras();
            String data2 = extras.getString("data2");
            txfData2.setText(data2);
        }
    }

    public class MyReceiver extends BroadcastReceiver {
        public static final String PROCESS_RESPONSE = "my.broadcast.address";
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView textView = (TextView) findViewById(R.id.address);
            String address = intent.getExtras().getString("address");
            textView.setText(address);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        //Obtenim la latitud i la longitud actual
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        latitudeStr = "" + latitude;
        longitudeStr = "" + longitude;
        final String URLStr = "http://maps.googleapis.com/maps/api/geocode/json?" +
                "latlng=" + latitudeStr + "," + longitudeStr + "&sensor=false";
        GPS = (Button) findViewById(R.id.GPS);
        GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    //Anem al servei per aconseguir al l'adreça
                    Intent intent = new Intent(MainActivity.this, GPSApp.class);
                    intent.putExtra("URL",URLStr); //Li passem la clau amb l'enllaç
                    startService(intent);
                } else {
                    Toast.makeText(MainActivity.this, "No internet connection available.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }
    @Override
    public void onProviderEnabled(String s) {

    }
    @Override
    public void onProviderDisabled(String s) {
    }
}
