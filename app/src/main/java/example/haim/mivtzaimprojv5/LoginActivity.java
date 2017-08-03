package example.haim.mivtzaimprojv5;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.tiEmail)
    TextInputLayout tiEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tiPassword)
    TextInputLayout tiPassword;
    @BindView(R.id.btnLogin)
    BootstrapButton btnLogin;
    @BindView(R.id.btnRegister)
    BootstrapButton btnRegister;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Object profileApplied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);


        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private String getEmail() {
        return etEmail.getText().toString();
    }

    private String getPassword() {
        return etPassword.getText().toString();
    }

    private boolean isEmailValid() {
        String email = getEmail();
        boolean valid = email.length() > 6 && email.contains("@");
        // regular expression pattern.

//        boolean valid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!valid)
            etEmail.setError("invalid email address");

        return valid;
    }

    private boolean isPasswordValid() {
        boolean valid = getPassword().length() > 5;

        if (!valid) etEmail.setError("Password must be at least 6 characters");

        return valid;
    }

    @OnClick({R.id.btnRegister})
    public void onViewClicked(View view) {
        //Client Side Validation:
        if (!isEmailValid() | !isPasswordValid()) return;
        showProgress(true);
        String email = getEmail();
        String password = getPassword();

        //Internet Permission
        mAuth.createUserWithEmailAndPassword(email, password).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        showProgress(true);

                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        startActivity(intent);

                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showProgress(false);
                        Snackbar.make(btnLogin, e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
                    }
                });
    }

    @OnClick(R.id.btnLogin)
    public void onBtnLoginClicked() {
        //client side validation
        if (!isEmailValid() | !isPasswordValid()) return;

        showProgress(true);
        String email = getEmail();
        String password = getPassword();
        //INTERNET PERMISSION
        Task<AuthResult> authResultTask = mAuth.signInWithEmailAndPassword(email, password).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        showProgress(true);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);


                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showProgress(false);
                        Snackbar.make(btnLogin, e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
                    }
                });
    }

    ProgressDialog dialog = null;

    public void showProgress(boolean show) {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            //title
            dialog.setTitle("Connecting to server");
            //message
            dialog.setMessage("Please Wait");
            //cancelable
            dialog.setCancelable(false);
        }
        if (show)
            dialog.show();
        else {
            dialog.dismiss();
        }
    }


}
