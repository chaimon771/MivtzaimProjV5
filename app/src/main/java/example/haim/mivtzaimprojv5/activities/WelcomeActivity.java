package example.haim.mivtzaimprojv5.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import example.haim.mivtzaimprojv5.R;

public class WelcomeActivity extends AppCompatActivity {



    RelativeLayout introMessage;
    LinearLayout appContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        introMessage = (RelativeLayout) findViewById(R.id.welcome_message_layout);
        appContent = (LinearLayout) findViewById(R.id.app_content_layout);
    }

    public void dismisWelcomeMessageBox(View view) {
        introMessage.setVisibility(View.INVISIBLE);
        appContent.setVisibility(View.VISIBLE);
    }

    public void onClickGotIt(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
