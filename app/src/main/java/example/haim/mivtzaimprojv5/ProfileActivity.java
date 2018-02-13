package example.haim.mivtzaimprojv5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.haim.mivtzaimprojv5.moduls.Profile;

public class ProfileActivity extends AppCompatActivity {


    PlaceAutocompleteFragment autocompleteFragment;
    FirebaseAuth mAuth;
    CharSequence myPlace;
    FirebaseDatabase database;
    DatabaseReference ref;
    private FirebaseUser user;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
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


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        ref = FirebaseDatabase.getInstance().getReference("profile").child(user.getUid());

        daySpinner();
        minuteSpinner();
        hourSpinner();

    }


    @OnClick(R.id.btnSaveProfile)
    public void onSaveProfileClicked() {
        //Get Current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        if (user.getEmail() == null) return;

        //gets        the Profile Inputes from this fragment

        String email = user.getEmail();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String phoneNumber = etPhone.getText().toString();
        final String address = myPlace.toString();
        String weekDay = daySpinner.getSelectedItem().toString();
        String hour = hourSpiner.getSelectedItem().toString();
        String minute = minuteSpiner.getSelectedItem().toString();

        Profile profile = new Profile(email, firstName, lastName, phoneNumber, address, weekDay, hour, true);
        //Save the inputes to the list:
        //1) ref to the DB
        ref.setValue(profile);
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);


    }


    private void hourSpinner() {
        String[] values = {"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"};
        Spinner hourSpiner = (Spinner) findViewById(R.id.hourSpiner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        hourSpiner.setAdapter(adapter);
    }

    private void minuteSpinner() {
        String[] values = {"00", "05", "10", "15", "20", "25", "30", "40", "50", "55"};
        Spinner minutesSpinner = (Spinner) findViewById(R.id.minuteSpiner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        minutesSpinner.setAdapter(adapter);
    }

    private void daySpinner() {
        String[] values = {"שישי", "ראשון", "שני", "שלישי", "רביעי", "חמישי"};
        Spinner daySpinner = (Spinner) findViewById(R.id.daySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        daySpinner.setAdapter(adapter);
    }


}
