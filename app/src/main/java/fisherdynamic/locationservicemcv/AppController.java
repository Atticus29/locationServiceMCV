package fisherdynamic.locationservicemcv;

import android.app.Application;

/**
 * Created by mf on 11/6/17.
 */

public class AppController extends Application {
    private static AppController mInstance;
    private LocationService service;
    public static synchronized AppController getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate(){
        //TODO Auto-generated method stub
        super.onCreate();
    }

    public void startService(){
        //start your service
    }

    public void stopService(){
        //stop service
    }

    public LocationService getService(){
        //TODO stuff here
    }

}
