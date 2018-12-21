package handlerlooper.ui_to_threads;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class LooperThread extends Thread
{
    private static final String TAG = "LooperThread";
    Handler handler;
    private CountDownLatch countDownLatch;

    LooperThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        Looper.prepare();

        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);
                Log.e(TAG,  msg.getData().getCharSequence("msg").toString() + " is received in "
                        + "thread id:"+String.valueOf(Thread.currentThread().getId()));
            }
        };
        if (countDownLatch!=null)
            countDownLatch.countDown();
        super.run(); //this line of code executes, run method of runnable passed to constructor above
        Looper.loop(); //this must be the last line in run() method. Code below this line wont be executed
        Log.e(TAG, "run() - I am below Looper.loop()");
    }
}

























