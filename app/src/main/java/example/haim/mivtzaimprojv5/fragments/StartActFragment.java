package example.haim.mivtzaimprojv5.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.haim.mivtzaimprojv5.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartActFragment extends Fragment {


    @BindView(R.id.btnGo)
    FloatingActionButton btnGo;
    Unbinder unbinder;

    public StartActFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    //Button b;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_start_act, container, false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.btnGo);

//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.btnGo)
    public void onViewClicked() {
    getFragmentManager()
            .beginTransaction()
            .replace(R.id.container, new ActFragment()).commit();


    }


}
