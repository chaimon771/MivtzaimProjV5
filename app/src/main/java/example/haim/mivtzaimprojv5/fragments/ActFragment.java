package example.haim.mivtzaimprojv5.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.haim.mivtzaimprojv5.MainActivity;
import example.haim.mivtzaimprojv5.R;
import example.haim.mivtzaimprojv5.moduls.Action;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActFragment extends Fragment {


    int count = 0;
    @BindView(R.id.etNumOfHanachot)
    EditText etNumOfHanachot;
    @BindView(R.id.btnMinus)
    Button btnMinus;
    @BindView(R.id.btnPlus)
    Button btnPlus;
    Unbinder unbinder;
    @BindView(R.id.tvHour)
    TextView tvHour;
    @BindView(R.id.tvMinutes)
    TextView tvMinutes;
    @BindView(R.id.btnStop)
    Button btnStop;
    @BindView(R.id.tvDifTime)
    TextView tvDifTime;
    @BindView(R.id.TvStopper)
    TextView TvStopper;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Hours, Seconds, Minutes;

    DatabaseReference ref;
    FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_act, container, false);


        unbinder = ButterKnife.bind(this, v);
        getHour();

        //  int dif = (int) (endTimeMillis - startTimeMillis);

        handler = new Handler();
        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Action").child(user.getUid());


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnMinus, R.id.btnPlus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMinus:
                if (count > 0) {
                    count--;
                    etNumOfHanachot.setText("" + count);
                }
                break;
            case R.id.btnPlus:
                this.count++;
                etNumOfHanachot.setText("" + count);
                break;
        }
    }


    public void getHour() {
        //  startTimeMillis = new Time(System.currentTimeMillis()).getTime();
        int hours = new Time(System.currentTimeMillis()).getHours();
        int minutes = new Time(System.currentTimeMillis()).getMinutes();

        if (hours < 10) {
            tvHour.setText("0" + hours);
        } else {
            tvHour.setText("" + hours);
        }


        if (minutes < 10) {
            tvMinutes.setText("0" + minutes);
        } else {
            tvMinutes.setText("" + minutes);
        }
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            Hours = (Minutes / 60);

            TvStopper.setText("" + Hours + ":"
                    + Minutes + ":"
                    + String.format("%02d", Seconds)
            );

            handler.postDelayed(this, 0);
        }

    };

    @OnClick(R.id.btnStop)
    public void onViewClicked() {
        // endTimeMillis = new Time(System.currentTimeMillis()).getTime();

        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("סיום").
                setMessage("הנחת תפילין ל-" + count + " יהודים, האם אתה בטוח שברצונך לסיים את הפעילות?")
                .setCancelable(true)
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.removeCallbacks(runnable); //Stop!

                        user = FirebaseAuth.getInstance().getCurrentUser();
//                        assert user != null;

                        int numOfHanachot = count;
                        String hour = tvHour.getText().toString();
                        String minutes = tvMinutes.getText().toString();

                        Date today = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                        String date = dateFormat.format(today);

                        int periodMinutes = Minutes;
                        int periodHours = Hours;

                        Action action = new Action(numOfHanachot, hour, minutes, date, periodMinutes, periodHours);

                        ref.child(String.valueOf(today)).setValue(action);

                       // ref.setValue(action);
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);


                        //Toast.makeText(getContext(), date, Toast.LENGTH_LONG).show();

                    }
                })
                .setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).show();
    }


}
