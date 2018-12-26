package services.practice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    //Command to the service to display a message
    static final int MSG_SAY_HELLO = 1;

    //Target we publish for clients to send messages to MyHandler.
    Messenger sMessenger; //s - service

    //Handler of incoming messages from clients.
    static class MyHandler extends Handler {
        private Context applicationContext;

        MyHandler(Context context) {
            applicationContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(applicationContext, "hello!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "MyHandler's handleMessage() - msg received here. switch case : MSG_SAY_HELLO");
                    break;
                default:
                    super.handleMessage(message);
            }
        }
    }


     /* When binding to the service, we return an interface to our sMessenger
     * for sending messages to the service. */
    @Override
    public IBinder onBind(Intent intent) {
        sMessenger = new Messenger(new MyHandler(this));
        return sMessenger.getBinder();
    }
}




























