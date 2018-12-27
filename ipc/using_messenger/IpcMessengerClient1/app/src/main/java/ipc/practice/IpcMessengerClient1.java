package ipc.practice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class IpcMessengerClient1 extends AppCompatActivity {

    private static final String TAG = "IpcMessengerClient1";
    private boolean isBound = false;

    public static final int MSG_BIND_CLIENT = 1;
    public static final int MSG_UNBIND_CLIENT = 2;
    public static final int MSG_SOME_INFO = 3;

    Messenger serverMessenger;
    final Messenger client1Messenger = new Messenger(new Client1Handler());

    static class Client1Handler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Log.e(TAG, "Client1Handler's handleMessage() has started. tid: " + String.valueOf(Thread.currentThread().getId()));
            switch (message.what) {
                case MSG_SOME_INFO:
                    Log.e(TAG, "Client1Handler's handleMessage() received ## " + message.obj.toString() + " ##");
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            //iBinder object passed to onServiceConnected() is the one returned by service's onBind() method

            Log.e(TAG, "ServiceConnection's onServiceConnected() called");
            serverMessenger = new Messenger(iBinder);

            try {
                Message _1stMessageToServer = Message.obtain();
                _1stMessageToServer.what = MSG_BIND_CLIENT;
                _1stMessageToServer.replyTo = client1Messenger;
                serverMessenger.send(_1stMessageToServer);

                Message _2ndMessageToServer = Message.obtain();
                _2ndMessageToServer.what = MSG_SOME_INFO;
                Bundle bundle = new Bundle();
                bundle.putCharSequence("msg", "ping from IpcMessengerClient1");
                _2ndMessageToServer.setData(bundle);
                serverMessenger.send(_2ndMessageToServer);
            }
            catch (RemoteException re) {
                Log.e(TAG, "ServiceConnection's onServiceConnected() - " + re.toString());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serverMessenger = null;
            Log.e(TAG, "ServiceConnection's onServiceDisconnected() called");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("ipc.practice", "ipc.practice.IpcMessengerServer");
        intent.setComponent(componentName);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        Message _3rdMessageToServer = Message.obtain();
        _3rdMessageToServer.what = MSG_UNBIND_CLIENT;
        _3rdMessageToServer.replyTo = client1Messenger;
        try {
            serverMessenger.send(_3rdMessageToServer);
        } catch (RemoteException re) {
            Log.e(TAG, "onDestroy() - " + re.toString());
        }
        unbindService(serviceConnection);
        super.onDestroy();
    }
}



























