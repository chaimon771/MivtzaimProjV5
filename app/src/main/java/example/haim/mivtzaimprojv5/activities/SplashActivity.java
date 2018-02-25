package example.haim.mivtzaimprojv5.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import example.haim.mivtzaimprojv5.MainActivity;
import example.haim.mivtzaimprojv5.R;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


            setContentView(R.layout.activity_splash);
            Thread myThread = new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(1000);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();
        }

}
