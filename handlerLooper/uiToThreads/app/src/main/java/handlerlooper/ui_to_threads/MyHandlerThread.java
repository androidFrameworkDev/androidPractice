package handlerlooper.ui_to_threads;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class MyHandlerThread extends HandlerThread {

    private static final String TAG = "MyHandlerThread";
    private CountDownLatch countDownLatch;
    private static final HashMap<Integer, String> numWordMap = new HashMap<>();
    private int serialNumber = 0;
    Handler handler;

    static {
        numWordMap.put(1, "FIRST");
        numWordMap.put(2, "SECOND");
    }

    MyHandlerThread(String name, CountDownLatch countDownLatch) {
        super(name);
        this.countDownLatch = countDownLatch;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        serialNumber++;
        Log.e(TAG, "onLooperPrepared() method is called " + numWordMap.get(serialNumber));
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);
                Log.e(TAG,  msg.getData().getCharSequence("msg").toString()+ " is received in "
                        + "thread id:"+String.valueOf(Thread.currentThread().getId()));
            }
        };
        if (countDownLatch!=null)
            countDownLatch.countDown();
    }

    @Override
    public void run() {
        serialNumber++;
        Log.e(TAG, "run() method is called " + numWordMap.get(serialNumber));
        super.run();
    }
}


























