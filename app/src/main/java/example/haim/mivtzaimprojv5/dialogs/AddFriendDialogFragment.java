package example.haim.mivtzaimprojv5.dialogs;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.haim.mivtzaimprojv5.R;
import example.haim.mivtzaimprojv5.moduls.Friend;
import example.haim.mivtzaimprojv5.moduls.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFriendDialogFragment extends DialogFragment {


    @BindView(R.id.btnAddFriend)
    Button btnAddFriendList;
    Unbinder unbinder;
    @BindView(R.id.etFriendName)
    EditText etFriendName;
    private DatabaseReference ref;
    private FirebaseUser user;


    public AddFriendDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_friend_dialog, container, false);
        unbinder = ButterKnife.bind(this, v);




        assert user != null;

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnAddFriend)
    public void onViewClicked() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Log.e("NoUser", "No User!");
            return; //No User  -> no DB...
        }

        if (etFriendName.getText().length() == 0) {
            etFriendName.setError("הוסף שם חבר...!");
            return;
        }

        User u = new User(user);
        //2) add friend = ref FriendList Action > UID > push
        ref = FirebaseDatabase.getInstance().getReference("FriendsList").child(user.getUid());

        //3) set data from the edit text to the Friend module.
        String friendName = etFriendName.getText().toString();
        Friend friend = new Friend(friendName);

        //4) ref.setValue(userList) + Listener to the value correctness
        ref.setValue(friend).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "נשמר!", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        dismiss();

                    }
                });

    }

}
