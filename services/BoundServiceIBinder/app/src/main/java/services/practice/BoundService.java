package services.practice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class BoundService extends Service {

    private static final String TAG = "BoundService";

    // Binder given to clients
    private final IBinder iBinder = new MyBinder();

    /* system CACHES the IBinder service communication channel. In other words,
     * the system calls the service's onBind() method to generate the IBinder only when the first client binds.
     * The system then delivers that same IBinder to all additional clients that bind to that same service,
     * without calling onBind() again. */

    // Random number generator
    private final Random random = new Random();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class MyBinder extends Binder {
        // Return this instance of BoundService so clients can call public methods
        BoundService getService() {
            return BoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind() started");

        //this iBinder object is passed to ServiceConnection's onServiceConnected() method
        //in the client binding to this service
        return iBinder;
    }

    /** method for clients */
    public int getRandomNumber() {
        return random.nextInt(100);
    }
}






























