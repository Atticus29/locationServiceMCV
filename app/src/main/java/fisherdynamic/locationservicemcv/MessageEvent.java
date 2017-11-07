package fisherdynamic.locationservicemcv;

/**
 * Created by mf on 11/6/17.
 */

public class MessageEvent {
    public double lat;
    public double lng;
    // Add additional fields here if needed

    public MessageEvent(double lat, double lng) {
        this.lat= lat;
        this.lng = lng;
    }
}
