package example.haim.mivtzaimprojv5.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import example.haim.mivtzaimprojv5.R;
import example.haim.mivtzaimprojv5.activities.OpenNewTableActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartTableFragment extends Fragment {


    @BindView(R.id.ivUserProfile)
    CircleImageView ivUserProfile;
    Unbinder unbinder;

    public StartTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start_table, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ivUserProfile)
    public void onPlusClicked() {
//        getFragmentManager().
//                beginTransaction().
//                replace(R.id.container, OpenNewTableActivity.class).
//                commit();
        Intent intent = new Intent(getContext(), OpenNewTableActivity.class);
        startActivity(intent);
    }
}
