package services.practice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class BindingActivity extends AppCompatActivity {
    private static final String TAG = "BindingActivity";
    BoundService boundService;
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to BoundService
        Intent intent = new Intent(this, BoundService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
        isBound = false;
    }

    /** Called when a button is clicked (the button in the layout file attaches to
     * this method with the android:onClick attribute) */
    public void onButtonClick(View view) {
        if (isBound) {
            if (view.getId() == R.id.button) {
                // Call a method from the BoundService.
                // However, if this call were something that might hang, then this request should
                // occur in a separate thread to avoid slowing down the activity performance.
                int num = boundService.getRandomNumber();
                Toast.makeText(view.getContext(), "random number is " + num, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        /* The binding is asynchronous, and bindService() returns immediately without returning the IBinder to the client.
         * To receive the IBinder, the client must create an instance of ServiceConnection and pass it to bindService().
         * The ServiceConnection includes a callback method onServiceConnected() that the system calls to deliver the IBinder.*/
        @Override
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            Log.e(TAG, "onServiceConnected() started");

            // We've bound to BoundService, cast the IBinder and get BoundService instance
            BoundService.MyBinder binder = (BoundService.MyBinder) iBinder;
            boundService = binder.getService();
            isBound = true;
        }

        /* The Android system calls onServiceDisconnected() when the connection to the service is unexpectedly lost,
        * such as when the service has crashed or has been killed. This is not called when the client unbinds."); */
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.e(TAG, "onServiceDisconnected() is called");
            isBound = false;
        }
    };
}


























