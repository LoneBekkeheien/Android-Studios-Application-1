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
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {














    private static final String TAG = "SignUpActivity";
    protected EditText passwordEditText;
    protected EditText emailEditText;
    protected EditText usernameEditText;
    protected Button signUpButton;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    ListView lv;
    ListViewAdapter listViewAdapter;
    ArrayList<Users> itemsArrayList;
    LVAdapter1 adapter;

    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();

        passwordEditText = (EditText)findViewById(R.id.passwordField);
        emailEditText = (EditText)findViewById(R.id.emailField);
        usernameEditText = (EditText)findViewById(R.id.userNameField);
        signUpButton = (Button)findViewById(R.id.signupButton);


/*
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            Log.i(TAG, "User not log in");
            // Not logged in, launch the Log In activity

        }
        else {
            lv = (ListView) findViewById(R.id.listview);
            itemsArrayList = new ArrayList<>();
            adapter = new LVAdapter1(this, itemsArrayList);
            lv.setAdapter(adapter);
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            mDatabaseReference = database.getReference();


            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();
                            // Log and toast
                            String msg = "Users:token: " + token;
                            Log.d(TAG, msg);
                            // Toast.makeText(MyWishList.this, msg, Toast.LENGTH_LONG).show();
                        }
                    });

        }*/

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();

                password = password.trim();
                email = email.trim();

                if (password.isEmpty() || email.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage("Please make sure you enter an email address and password!")
                            .setTitle("Error!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.i(TAG, "User created");
                                        Intent intent = new Intent(SignUpActivity.this, Home.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        Log.i(TAG, "User not created");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                        builder.setMessage(task.getException().getMessage())
                                                .setTitle("Error!")
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
/*
    @Override
    protected void onResume() {
        super.onResume();
        if(mDatabaseReference!=null) {

            mDatabaseReference.child("Users").addValueEventListener(new ValueEventListener() { //valueEventListener is used to update the database in RT
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    itemsArrayList.clear();
                    for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                        Users user = userDataSnapshot.getValue(Users.class);
                        itemsArrayList.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDatabaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    itemsArrayList.clear();
                    for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                        Users user = userDataSnapshot.getValue(Users.class);
                        itemsArrayList.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }


*/

}
