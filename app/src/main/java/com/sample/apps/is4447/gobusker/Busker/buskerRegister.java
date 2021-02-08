package com.sample.apps.is4447.gobusker.Busker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.R;

import java.util.HashMap;

public class buskerRegister extends AppCompatActivity {
    //declaring variables
    //connection to firebase
    FirebaseAuth mAuth;

    Button register;
    EditText email, password, firstname, bio, verifypassword;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busker_register);
        //connection to firebase
        mAuth = FirebaseAuth.getInstance();

        //link variables with items from layour file
        email = (EditText) findViewById(R.id.etbuskerEmailRegister);
        firstname = (EditText) findViewById(R.id.etBuskerFirtsName);
        bio = (EditText) findViewById(R.id.etbuskerSecondName);
        password = (EditText) findViewById(R.id.etbuskerPasswordRegister);
        verifypassword = (EditText) findViewById(R.id.etbuskerPasswordRegister2);

        register = (Button) findViewById(R.id.btnbuskerRegister);

        register.setOnClickListener(new View.OnClickListener() {
                                        @Override
                public void onClick(View v) {
                    //Code acquired and modified from this youtube video for firebase register functionality
                    //"https://www.youtube.com/results?search_query=%231+login+and+registration+android+app+tutorial+using+firebase+authentication+-+create+user"

                    String emailreg = email.getText().toString();
                    String firstnamereg = firstname.getText().toString();
                    String bioreg = bio.getText().toString();
                    String passwordreg = password.getText().toString();
                    String verpassword = verifypassword.getText().toString();


                    //validates user input
                    if (firstnamereg.isEmpty()) {
                        firstname.setError("Please enter first name");
                        firstname.requestFocus();
                    } else if (bioreg.isEmpty()) {
                        bio.setError("Please enter bio");
                        bio.requestFocus();
                    } else if (emailreg.isEmpty()) {
                        email.setError("Please enter email");
                        email.requestFocus();
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(emailreg).matches()) {
                        email.setError("Please enter valid email");
                        email.requestFocus();
                    } else if (passwordreg.isEmpty()) {
                        password.setError("Please enter password");
                        password.requestFocus();
                    } else if (passwordreg.length() < 6) {
                        password.setError("Password must be longer than 6 characters");
                        password.requestFocus();
                    } else{
register(bioreg, firstnamereg, emailreg, passwordreg);
                }
            }
    });
}

private void register(String username, String firstname, String email, String password){
    //connects to firebase
    //links busker class with new email+password in firebase
    mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String userId = firebaseUser.getUid();

                        reference = FirebaseDatabase.getInstance().getReference().child("Buskers").child(userId);
                        HashMap<String, Object>hashMap = new HashMap<>();
                        hashMap.put("id", userId);
                        hashMap.put("username", username.toLowerCase());
                        hashMap.put("firstname", firstname);
                        hashMap.put("bio", "");
                        hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/gobusker-e1749.appspot.com/o/IMG_20180107_173528_102.jpg?alt=media&token=7b6f5d36-e67b-41f4-8878-f9f6708be83b");
                     /*   Busker busker = new Busker(firstnamereg, bioreg, emailreg, userId);
                        FirebaseDatabase.getInstance().getReference("Buskers")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                                .setValue(busker).addOnCompleteListener(new OnCompleteListener<Void>()
                       */

                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(buskerRegister.this, "Busker has been registered successfully", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(buskerRegister.this, "Registration has failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
