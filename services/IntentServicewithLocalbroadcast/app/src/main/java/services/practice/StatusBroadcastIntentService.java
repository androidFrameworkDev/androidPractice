package services.practice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class StatusBroadcastIntentService extends IntentService {

    private static final String TAG1 = "StatusBroadcast";
    private static final String TAG2 = "IntentService";

    public StatusBroadcastIntentService() {
        super("StatusBroadcastIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Intent broadcastStatusIntent = new Intent(Constants.INTENT_ACTION_SERVICE_STATUS);
        broadcastStatusIntent.putExtra(Constants.INTENT_EXTRA_SERVICE_STATUS,
                TAG1+TAG2+" execution completed successfully");

        Log.e(TAG1, TAG2 + " onHandleIntent() - sending status via local broadcast from ## tid:" + String.valueOf(Thread.currentThread().getId()) + " ##");

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastStatusIntent);
    }
}
