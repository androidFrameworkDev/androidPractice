package handlerlooper.threads_to_ui;

import android.os.HandlerThread;
import android.util.Log;
import android.widget.TextView;

import static handlerlooper.threads_to_ui.MainActivity.uiHandler;

public class MyHandlerThread extends HandlerThread {

    private static final String TAG = "MyHandlerThread";
    TextView textView;

    MyHandlerThread(String name, TextView textView) {
        super(name);
        this.textView = textView;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        Log.e(TAG, "tid:" + String.valueOf(Thread.currentThread().getId()));
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "uiHandler in onLooperPrepared() running on tid:" + String.valueOf(Thread.currentThread().getId()));
                textView.append("#text from HandlerThread#");
            }
        });
    }
}


























