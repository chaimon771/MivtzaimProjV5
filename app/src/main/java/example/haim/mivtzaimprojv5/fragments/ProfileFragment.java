package example.haim.mivtzaimprojv5.fragments;


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.haim.mivtzaimprojv5.R;
import example.haim.mivtzaimprojv5.moduls.Profile;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends android.app.Fragment {


    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.daySpinner)
    Spinner daySpinner;
    @BindView(R.id.hourSpiner)
    Spinner hourSpiner;
    @BindView(R.id.minuteSpiner)
    Spinner minuteSpiner;
    @BindView(R.id.btnSaveProfile)
    Button btnSaveProfile;
    Unbinder unbinder;

    PlaceAutocompleteFragment autocompleteFragment;
    FirebaseAuth mAuth;
    CharSequence myPlace;
    FirebaseDatabase database;
    DatabaseReference ref;

    private Spinner spinner;
    private AdapterView adapterView;
    private FragmentManager fragmentManager;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        autocompleteFragment = (PlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                myPlace = place.getAddress();
            }

            @Override
            public void onError(Status status) {
                Log.i("Place", "An error occurred: " + status);
            }
        });


        daySpinner(view);
        minuteSpinner(view);
        hourSpinner(view);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        ref = FirebaseDatabase.getInstance().getReference("profile").child(user.getUid());


        return view;
    }

    private void daySpinner(View view) {
        String[] values = {"שישי", "ראשון", "שני", "שלישי", "רביעי", "חמישי"};
        Spinner daySpinner = (Spinner) view.findViewById(R.id.daySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        daySpinner.setAdapter(adapter);
    }
    private void minuteSpinner(View view) {
        String[] values = {"00", "05", "10", "15", "20", "25", "30", "40", "50", "55"};
        Spinner minutesSpinner = (Spinner) view.findViewById(R.id.minuteSpiner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        minutesSpinner.setAdapter(adapter);
    }
    private void hourSpinner(View view) {
        String[] values = {"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"};
        Spinner hourSpiner = (Spinner) view.findViewById(R.id.hourSpiner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        hourSpiner.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSaveProfile)
    public void onSaveProfileClicked() {
        //Get Current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        //gets the Profile Inputes from this fragment
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String phoneNumber = etPhone.getText().toString();
        String address = myPlace.toString();
        String weekDay = daySpinner.getSelectedItem().toString();
        String hour = hourSpiner.getSelectedItem().toString();
        String minute = minuteSpiner.getSelectedItem().toString();

        Profile profile = new Profile(firstName, lastName, phoneNumber, address, weekDay, hour, true);
        //Save the inputes to the list:
        //1) ref to the DB
        FirebaseDatabase.getInstance().getReference("profile")
                .child(user.getUid())
                .push()
                .setValue(profile)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                                ProgressDialog dialog = new ProgressDialog(getContext());
                                    //title
                                    dialog.setTitle("Connecting to server");
                                    //message
                                    dialog.setMessage("Please Wait");
                                    //cancelable
                                    dialog.setCancelable(false);

                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.container, new WelcomeFragment())
                                        .commit();


                            }
                        } else {
                            Exception e = task.getException();
                            if (e != null)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        }

                    }
                });
    }


}
