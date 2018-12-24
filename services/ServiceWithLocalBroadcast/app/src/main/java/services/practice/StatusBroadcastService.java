package services.practice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class StatusBroadcastService extends Service {

    private static final String TAG = "StatusBroadcastService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand() - startID is " + String.valueOf(startId));

        Intent broadcastStatusIntent = new Intent(Constants.INTENT_ACTION_SERVICE_STATUS);
        broadcastStatusIntent.putExtra(Constants.INTENT_EXTRA_SERVICE_STATUS,
                "StatusBroadcastService execution completed successfully");

        Log.e(TAG, "onStartCommand() - sending status via local broadcast from tid:" + String.valueOf(Thread.currentThread().getId()));

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastStatusIntent);

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}





























