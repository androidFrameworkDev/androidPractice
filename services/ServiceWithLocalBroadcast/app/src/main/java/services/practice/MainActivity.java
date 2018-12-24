package services.practice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceStatusReceiver serviceStatusReceiver = new ServiceStatusReceiver();
        IntentFilter serviceStatusIntentFilter = new IntentFilter(Constants.INTENT_ACTION_SERVICE_STATUS);
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceStatusReceiver, serviceStatusIntentFilter);

        Intent intent = new Intent(this, StatusBroadcastService.class);
        startService(intent);
    }

    class ServiceStatusReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "started ServiceStatusReceiver's onReceive()");

            if (intent==null)
                return;

            String action = intent.getAction();

            if (!TextUtils.isEmpty(action) && action.equals(Constants.INTENT_ACTION_SERVICE_STATUS)) {
                Log.e(TAG, "## received status as local broadcast on tid:" + String.valueOf(Thread.currentThread().getId())
                        + " ## status msg:: " + intent.getStringExtra(Constants.INTENT_EXTRA_SERVICE_STATUS));
            }

            Log.e(TAG, "finished ServiceStatusReceiver's onReceive()");
        }
    }
}





























