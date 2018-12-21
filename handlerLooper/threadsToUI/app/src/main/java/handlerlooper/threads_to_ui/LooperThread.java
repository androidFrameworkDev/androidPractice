package handlerlooper.threads_to_ui;

import android.os.Looper;
import android.util.Log;

public class LooperThread extends Thread
{
    private static final String TAG = "LooperThread";

    LooperThread(Runnable runnable) {
        super(runnable);
    }

    @Override
    public void run() {
        Looper.prepare();
        Log.e(TAG,"starts");
        Log.e(TAG,"tid:" + String.valueOf(Thread.currentThread().getId()));
        super.run(); //this line of code executes, run method of runnable passed to constructor above
        Log.e(TAG,"ends");
        Looper.loop(); //this must be the last line in run() method. Code below this line wont be executed
        Log.e(TAG, "run() - I am below Looper.loop()");
    }
}
