package example.haim.mivtzaimprojv5.moduls;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DELL e7440 on 14/08/2017.
 */

public class Friend implements Parcelable {
    private String friendName;

    public Friend() {
    }

    public Friend(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "friendName='" + friendName + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.friendName);
    }

    protected Friend(Parcel in) {
        this.friendName = in.readString();
    }

    public static final Parcelable.Creator<Friend> CREATOR = new Parcelable.Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}
