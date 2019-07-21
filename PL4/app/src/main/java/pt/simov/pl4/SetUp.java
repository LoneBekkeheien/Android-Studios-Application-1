package pt.simov.pl4;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SetUp extends AppCompatActivity {

    private EditText UserName;
    private Button saveButton;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;

    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        UserName = (EditText) findViewById(R.id.userNameField);
        saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAccountSetUpInformation();
            }
        });
    }



    private void SaveAccountSetUpInformation() {

        String username = UserName.getText().toString();

        if (TextUtils.isEmpty(username)){
            Toast.makeText(this, "please write your username..", Toast.LENGTH_SHORT).show();
        }else{
            HashMap userMap = new HashMap();
            userMap.put("username", username);
            UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        SendUserToHomeActivity();
                        Toast.makeText(SetUp.this, "Your account is created successfully!", Toast.LENGTH_LONG).show();
                    }else{
                        String message = task.getException().getMessage();
                        Toast.makeText(SetUp.this, "Error occured:" + message, Toast.LENGTH_LONG).show();

                    }
                }
            });




        }
    }

    private void SendUserToHomeActivity() {

        Intent startNewActivity= new Intent(this, Home.class);
        startActivity(startNewActivity);
    }

}
