package fisherdynamic.locationservicemcv;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private Double currentLatitude;
    private Double currentLongitude;
    private Button nextActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextActivityButton = findViewById(R.id.nextActivityButton);
        nextActivityButton.setOnClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            return;
        }
        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("personal", "on receive reached");
                // Get extra data included in the Intent
                currentLatitude = Double.parseDouble(intent.getStringExtra("ServiceLatitudeUpdate"));
                currentLongitude = Double.parseDouble(intent.getStringExtra("ServiceLongitudeUpdate"));
//                latLongView.setText(Double.toString(currentLatitude) + ", " + Double.toString(currentLongitude));
            }
        };
        IntentFilter intentFilter = new IntentFilter("locationServiceUpdates");
//        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(mMessageReceiver, intentFilter);

    }

    @Override
    public void onPause() {
        super.onPause();
        // This line will unregister your Activity.
        // It's a good practice to put this on the onPause() method
        // to make your event handling system tied to the Activity lifecycle.
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        // This line will register your Activity to the EventBus
        // making sure that all the methods annotated with @Subscribe
        // will be called if their specific Events are posted.
        EventBus.getDefault().register(this);
    }

    // The threadMode MAIN makes sure this method is called on the main Thread.
    // But you could also set it up to be called on other threads if needed. Check the docs for more info.
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        double lat = event.lat;
        double lng = event.lng;

        TextView latLongView = findViewById(R.id.latLongView);
        latLongView.setText(Double.toString(lat) + ", " + Double.toString(lng));
        // Do whatever you want with the data.
    };

    @Override
    public void onClick(View view) {
        if(view == nextActivityButton){
            Log.d("personal", "nextActivityButton clicked");
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        startLocationService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopLocationService();
    }

    public void startLocationService(){
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
        Log.d("personal", "location service started");
    }

    public void stopLocationService(){
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
    }
}
