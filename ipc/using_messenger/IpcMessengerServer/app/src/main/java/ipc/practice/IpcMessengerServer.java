package ipc.practice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class IpcMessengerServer extends Service {

    private static final String TAG = "IpcMessengerServer";

    static final int MSG_BIND_CLIENT = 1;
    static final int MSG_UNBIND_CLIENT = 2;
    static final int MSG_SOME_INFO = 3;

    final private Messenger serverMessenger = new Messenger(new ServerHandler());
    private static ArrayList<Messenger> clientMessengersList;

     static class ServerHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Log.e(TAG, "ServerHandler's handleMessage() started. tid: " + String.valueOf(Thread.currentThread().getId()));
            switch (message.what) {
                case MSG_BIND_CLIENT:
                    clientMessengersList.add(message.replyTo);
                    break;
                case MSG_UNBIND_CLIENT:
                    clientMessengersList.remove(message.replyTo);
                    break;
                case MSG_SOME_INFO:
                    String msgFromClient = message.getData().getCharSequence("msg").toString();
                    Log.e(TAG, "ServerHandler's handleMessage() received ## " + msgFromClient + " ##");
                    for (Messenger cMessenger : clientMessengersList) {
                        Message messageToAllClients = Message.obtain();
                        message.obj = "I received a ping";
                        try {
                            cMessenger.send(messageToAllClients);
                        } catch (RemoteException re) {
                            Log.e(TAG, "ServerHandler's handleMessage() - " + re.toString());
                        }
                    }
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
            Log.e(TAG, "ServerHandler's handleMessage() ended");
        }
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate() started");
        clientMessengersList = new ArrayList<>();
        Log.e(TAG, "onCreate() ended");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind() started and returned");
        return serverMessenger.getBinder();
    }
}































