package services.practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, ServiceOnHandlerThread.class);
        intent.putExtra("msg", "message from MainActivity");
        startService(intent);
        //startService(intent); //called second time intentionally to see whats happening(using logcat)
    }
}
