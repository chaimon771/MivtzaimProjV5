package example.haim.mivtzaimprojv5.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.haim.mivtzaimprojv5.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends android.app.Fragment {


    public WelcomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

}
