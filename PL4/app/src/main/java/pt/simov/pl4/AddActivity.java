package pt.simov.pl4;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


//import javax.security.auth.Subject;

public class AddActivity extends AppCompatActivity {
    EditText title, description;
    Button btAdd;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference();

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        btAdd = (Button) findViewById(R.id.add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Subject subject = new Subject();
                subject.setUid(mDatabaseReference.child("subjects").push().getKey());
                subject.setTitle(title.getText().toString());
                subject.setDescription(description.getText().toString());
                mDatabaseReference.child("subjects").child(subject.getUid()).setValue(subject);
                finish();
            }
        });


    }
}

/*
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText setName;
    EditText setSurName;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        setName = (EditText) findViewById(R.id.editView);
        setSurName = (EditText) findViewById(R.id.editView2);

        extras = getIntent().getExtras();

        Button btAdd = (Button) findViewById(R.id.buttonAdd);
        Button btCancle = (Button) findViewById(R.id.buttonCancel);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] contact = {"name","surename"};
                contact[0] = setName.getText().toString();
                contact[1] = setSurName.getText().toString();
                Intent i = new Intent();
                extras.putStringArray("NAME", contact);
                i.putExtras(extras);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });

        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                setResult(Activity.RESULT_CANCELED,i);
                i.putExtras(extras);
                finish();
            }
        });
    }
}


XML FILE:

<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:paddingBottom="@dimen/activity_vertical_margin"
    tools:context=".AddActivity">


    <EditText
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/editView"
        android:layout_alignParentTop="true"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true" />

    <EditText
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/editView2"
        android:layout_below="@+id/editView"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_below="@id/editView2">

        <Button
            android:id="@+id/buttonAdd"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/buttonAdd"
            android:layout_weight="1"/>

        <Button
            android:id="@+id/buttonCancel"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/buttonCancle"
            android:layout_weight="1"/>
    </LinearLayout>

</RelativeLayout>


*/