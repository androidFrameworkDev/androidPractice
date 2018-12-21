package handlerlooper.threads_to_ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    LooperThread looperThread;
    BackgroundThread backgroundThread;
    static Handler uiHandler;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        Log.e(TAG, "UI thread id:" + String.valueOf(Thread.currentThread().getId()));

        uiHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.e(TAG, "uiHandler handleMessage() running on tid:" +
                        String.valueOf(Thread.currentThread().getId() +
                        " received " + msg.getData().getCharSequence("msg")));
            }
        };

        Runnable looperThreadRunnable = new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "looperThreadRunnable tid:" + String.valueOf(Thread.currentThread().getId()));
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "uiHandler post() in looperThreadRunnable tid:" + String.valueOf(Thread.currentThread().getId()));
                        textView.append("\n#text from looperThreadRunnable#");
                    }
                });
            }
        };

        looperThread = new LooperThread(looperThreadRunnable);
        looperThread.start();

        Runnable backgroundThreadRunnable = new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "backgroundThreadRunnable tid:" + String.valueOf(Thread.currentThread().getId()));
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "uiHandler post() in backgroundThreadRunnable tid:" + String.valueOf(Thread.currentThread().getId()));
                        textView.append("\n#text from backgroundThreadRunnable#");
                    }
                });
            }
        };

        backgroundThread = new BackgroundThread(backgroundThreadRunnable);
        backgroundThread.start();

        MyHandlerThread myHandlerThread = new MyHandlerThread("myHandlerThread", textView);
        myHandlerThread.start();

        //sending message to ui thread from other threads via uiHandler

        new BackgroundThread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putCharSequence("msg", "msg from 2nd_BackgroundThread (tid:" + String.valueOf(Thread.currentThread().getId()) + ")");
                message.setData(bundle);
                uiHandler.sendMessage(message);
            }
        }).start();

        new LooperThread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putCharSequence("msg", "msg from 2nd_LooperThread (tid:" + String.valueOf(Thread.currentThread().getId()) + ")");
                message.setData(bundle);
                uiHandler.sendMessage(message);
            }
        }).start();

        new MyHandlerThread("MyHandlerThread_2nd", textView) {
            @Override
            protected void onLooperPrepared() {
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putCharSequence("msg", "msg from 2nd_MyHandlerThread (tid:" + String.valueOf(Thread.currentThread().getId()) + ")");
                message.setData(bundle);
                uiHandler.sendMessage(message);
            }
        }.start();
    }
}





















