package services.practice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

public class ServiceOnHandlerThread extends Service {

    private static final String TAG =  "ServiceOnHandlerThread";
    Handler myServiceThreadHandler;

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate() is executing on tid:"+String.valueOf(Thread.currentThread().getId()));

        HandlerThread myServiceThread = new HandlerThread("MyServiceThread");
        myServiceThread.start();
        Looper myServiceThreadLooper = myServiceThread.getLooper();
        myServiceThreadHandler = new Handler(myServiceThreadLooper) {
            @Override
            public void handleMessage(Message message) {
                int startServiceId = message.arg1;
                Log.e(TAG,"startServiceId is " +String.valueOf(startServiceId));
                Log.e(TAG, "handleMessage() is executing on tid:"+String.valueOf(Thread.currentThread().getId()));
                Log.e(TAG, message.obj + " is received on tid:"+String.valueOf(Thread.currentThread().getId()));
                stopSelf(startServiceId);
            }
        };
        Log.e(TAG, "myServiceThread tid:"+String.valueOf(myServiceThread.getId()));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startServiceId) {
        Log.e(TAG, "onStartCommand() is executing on tid:"+String.valueOf(Thread.currentThread().getId()));
        String msgContent = "MSG::INTENT_NULL_OR_HAS_NO_EXTRA";
        if (intent!=null) {
            if (intent.hasExtra("msg")) {
                msgContent = intent.getStringExtra("msg");
            }

            //You can check if the service was restarted using intent.getFlags() method.
            int flag = intent.getFlags();
            if (flag == START_FLAG_REDELIVERY){
                Log.e(TAG, "inside onStartCommand() - service was re-started with START_REDELIVER_INTENT flag");
            }
            else if(flag == START_FLAG_RETRY) {
                Log.e(TAG, "inside onStartCommand() - service was re-started with START_STICKY flag");
            }
        }
        Message message = Message.obtain();
        message.obj = msgContent;
        message.arg1 = startServiceId;
        myServiceThreadHandler.sendMessage(message);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind() is executing on tid:"+String.valueOf(Thread.currentThread().getId()));
        return null;
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy() is executing on tid:"+String.valueOf(Thread.currentThread().getId()));
        super.onDestroy();
    }
}























