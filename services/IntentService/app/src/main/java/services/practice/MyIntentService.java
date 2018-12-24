package services.practice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG, "onHandleIntent() is being executed on tid:"+Thread.currentThread().getId());
        if (intent!=null) {
            Log.e(TAG, "intent is delivered to tid:"+Thread.currentThread().getId());
            if (intent.hasExtra("msg")) {
                String message = intent.getStringExtra("msg");
                Log.e(TAG, message + " is received on tid:"+Thread.currentThread().getId());
            }
        }
    }
}

























































