package pt.simov.pl4;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private static final String TAG = "LogInActivity";
    protected EditText emailEditText;
    protected EditText passwordEditText;
    protected Button logInButton;
    protected Button signUpButton;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();

        if (currentUser == null){
            SendUserToHomeActivity();
        }


    }

    private void SendUserToHomeActivity() {
        Intent startNewActivity= new Intent(this, Home.class);
        startActivity(startNewActivity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();

        signUpButton = (Button) findViewById(R.id.signUpButton);

        emailEditText = (EditText) findViewById(R.id.emailField);
        passwordEditText = (EditText) findViewById(R.id.passwordField);
        logInButton = (Button) findViewById(R.id.loginButton);




        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                email = email.trim();
                password = password.trim();

                if (email.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
                    builder.setMessage("Please make sure you enter an email address and password!")
                            .setTitle("Error")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.i(TAG, "User credentials valid");
                                        Intent intent = new Intent(LogInActivity.this, Home.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        Log.i(TAG, "User credentials wrong");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
                                        builder.setMessage(task.getException().getMessage())
                                                .setTitle("Error")
                                                .setPositiveButton(android.R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }

                            });
                }
            }
        });
    }
}

