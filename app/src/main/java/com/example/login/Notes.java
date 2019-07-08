package com.example.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Notes extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

private DatabaseReference databaseReference;
private EditText editTextTitle, editTextNotes;
private Button buttonSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }


        databaseReference= FirebaseDatabase.getInstance().getReference();
        editTextTitle =(EditText) findViewById(R.id.editTextTitle);
        editTextNotes = (EditText) findViewById(R.id.editTextNotes);
        buttonSave =(Button) findViewById(R.id.buttonSave);
        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void saveUserInformation(){
String title =editTextTitle.getText().toString().trim();
String notes = editTextNotes.getText().toString().trim();

UserInformation userInformation = new UserInformation(title,notes);
FirebaseUser user = firebaseAuth.getCurrentUser();
databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "Information Saved", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }
        if (view == buttonSave){
            saveUserInformation();
        }
    }
}