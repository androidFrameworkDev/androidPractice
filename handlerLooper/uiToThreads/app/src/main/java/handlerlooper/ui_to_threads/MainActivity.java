package handlerlooper.ui_to_threads;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CountDownLatch uiThreadCountDownLatch = new CountDownLatch(2);
        Log.e(TAG, "UI thread id:"+String.valueOf(Thread.currentThread().getId()));

        LooperThread looperThread = new LooperThread(uiThreadCountDownLatch);
        MyHandlerThread myHandlerThread = new MyHandlerThread("myHandlerThread",uiThreadCountDownLatch);
        //BackgroundThread backgroundThread = new BackgroundThread(uiThreadCountDownLatch);

        looperThread.start();
        myHandlerThread.start();
        //backgroundThread.start();

        try {
            uiThreadCountDownLatch.await();
        } catch (InterruptedException e) {
            Log.e(TAG, e.toString());
        }

        String uiThreadID = "(id:"+String.valueOf(Thread.currentThread().getId())+")";
        looperThread.handler.sendMessage(getMessage("#msg from UI thread"+uiThreadID+" to looper thread#"));
        myHandlerThread.handler.sendMessage(getMessage("#msg from UI thread"+uiThreadID+" to handler thread#"));
        //backgroundThread.handler.sendMessage(getMessage("msg from UI thread to background thread"));
    }

    private Message getMessage(String msgContent)
    {
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("msg", msgContent);
        message.setData(bundle);
        return message;
    }
}























