package handlerlooper.ui_to_threads;

import android.os.Handler;

import java.util.concurrent.CountDownLatch;

class BackgroundThread extends Thread {

    private static final String TAG = "BackgroundThread";
    Handler handler;
    private CountDownLatch countDownLatch;

    BackgroundThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        /*java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()

        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);
                Log.e(TAG,  msg.getData().getCharSequence("msg").toString());
            }
        };*/

        if (countDownLatch!=null)
            countDownLatch.countDown();
        super.run();
    }
}
