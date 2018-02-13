package example.haim.mivtzaimprojv5.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.haim.mivtzaimprojv5.GPSTracker;
import example.haim.mivtzaimprojv5.R;
import example.haim.mivtzaimprojv5.moduls.Friend;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenNewTableFragment extends Fragment {

    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.tvLocationFound)
    TextView tvLocationFound;
    Button btnFindLocation;

    RecyclerView rvFriends;
    @BindView(R.id.etFriendName)
    EditText etFriendName;
    @BindView(R.id.fabAddFriend)
    FloatingActionButton fabAddFriend;

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    private Unbinder unbinder;

    private FirebaseUser user;
    private DatabaseReference ref;

    GPSTracker gps;
    FusedLocationProviderClient mFusedLocationClient;


    Geocoder geocoder;
    List<Address> addresses;

    public OpenNewTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_open_new_table, container, false);
        unbinder = ButterKnife.bind(this, v);

        fabSave = (FloatingActionButton) v.findViewById(R.id.fabSave);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().
                        beginTransaction().
                        replace(R.id.container, new MainFragment()).
                        commit();
            }
        });

//        mFusedLocationClient = new FusedLocationProviderClient(getContext());


        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("FriendsList").child(user.getUid());



        rvFriends = (RecyclerView) v.findViewById(R.id.rvFriends);
        rvFriends.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rvFriends.setAdapter(new FriendsAdapter(ref));

        btnFindLocation = (Button) v.findViewById(R.id.btnFindLocation);
        btnFindLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                }


                gps = new GPSTracker(getActivity());
                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    Toast.makeText(getContext(), "Your Location is -\nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();


                } else {
                    gps.showSettingsAlert();
                }
            }
        });


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }






    //2) Adapter
    public static class FriendsAdapter extends FirebaseRecyclerAdapter<Friend, FriendsAdapter.FriendsViewHolder> {

        public FriendsAdapter(Query query) {
            super(
                    Friend.class,
                    R.layout.friend_item,
                    FriendsViewHolder.class,
                    query
            );
        }

        @Override
        protected void populateViewHolder(FriendsViewHolder viewHolder, Friend model, int position) {
            viewHolder.tvFriendName.setText(model.getFriendName());
        }

        //1) view holder
        public static class FriendsViewHolder extends RecyclerView.ViewHolder {
            TextView tvFriendName;

            public FriendsViewHolder(View itemView) {
                super(itemView);
                tvFriendName = (TextView) itemView.findViewById(R.id.tvFriendName);

            }
        }
    }

    @OnClick(R.id.fabAddFriend)
    public void onViewClicked() {
        //Current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        //gets the friend name from this fragment
        String friendName = etFriendName.getText().toString();
        Friend friend = new Friend(friendName);

        //Save the friend to the list:
        //1) ref to the DB
        ref.push().setValue(friend).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            etFriendName.setText(null);
                            Toast.makeText(getContext(), "נשמר!", Toast.LENGTH_SHORT).show();
                        } else {
                            Exception e = task.getException();
                            if (e != null)
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
