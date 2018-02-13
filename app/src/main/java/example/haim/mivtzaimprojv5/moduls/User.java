package example.haim.mivtzaimprojv5.moduls;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by DELL e7440 on 13/07/2017.
 */

public class User {
    private String uid;
    private String email;
    private Object friendImage;

    public User() {
    }

    public User(FirebaseUser user) {
        this.uid = user.getUid();
        this.email = user.getEmail();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getFriendImage() {
        return friendImage;
    }
}
