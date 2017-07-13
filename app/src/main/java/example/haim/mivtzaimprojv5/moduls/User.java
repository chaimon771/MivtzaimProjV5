package example.haim.mivtzaimprojv5.moduls;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by DELL e7440 on 13/07/2017.
 */

public class User {
    private String uid;
    private String email;

    public User() {
    }

    public User(FirebaseUser user) {
        this.uid = user.getUid();
        this.email = user.getEmail();
    }
}
