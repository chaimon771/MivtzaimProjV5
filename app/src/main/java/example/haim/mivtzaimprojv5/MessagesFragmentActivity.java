package example.haim.mivtzaimprojv5;

import android.os.Bundle;

import example.haim.mivtzaimprojv5.fragments.ProfileFragment;

/**
 * Created by DELL e7440 on 12/07/2017.
 */

public class MessagesFragmentActivity extends LoginActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ProfileFragment()).commit();}
    }
}
