package services.practice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MessengerActivity extends AppCompatActivity {

    private static final String TAG = "MessengerActivity";

    /* Messenger for communicating with the service. */
    Messenger cMessenger = null; //c - client

    /* Flag indicating whether we have called bind on the service. */
    boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    //Class for interacting with the main interface of the service.
    private ServiceConnection serviceConnection = new ServiceConnection() {

        /*onServiceConnected() is called when the connection with the service has been established,
        giving us the object we can use to interact with the service.  We are communicating with the
        service using a Messenger, so here we get a client-side representation of that from the raw IBinder object.*/
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            cMessenger = new Messenger(iBinder);
            isBound = true;
        }

        /*onServiceDisconnected() is called when the connection with the service has been
         unexpectedly disconnected -- that is, its process crashed.*/
        public void onServiceDisconnected(ComponentName className) {
            cMessenger = null;
            isBound = false;
        }
    };

    public void sendMessageToService(View view) {
        if (!isBound){
            return;
        }

        // Create and send a message to the service, using a supported 'what' value
        Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);

        try {
            cMessenger.send(msg);
            Log.e(TAG, "sendMessageToService() - sending msg to service by setting message.what as MSG_SAY_HELLO");
        } catch (RemoteException e) {
            Log.e(TAG, "sendMessageToService() - " + e.toString());
        }
    }
}


























