package com.sample.apps.is4447.gobusker.Fan;

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
import com.sample.apps.is4447.gobusker.Busker.buskerRegister;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.R;

import java.util.HashMap;

public class fanRegister extends AppCompatActivity {
    //connection to firebase
    private FirebaseAuth mAuth;
    //declaring variables
    Button register;
    EditText email, password, firstname, secondname, verifypassword;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_register);

        mAuth = FirebaseAuth.getInstance();

        //link variables with items from layour file
        email = (EditText) findViewById(R.id.etFanEmailRegister);
        firstname = (EditText) findViewById(R.id.etFanFirtsName);
        secondname = (EditText) findViewById(R.id.etFanecondName);
        password = (EditText) findViewById(R.id.etFanPasswordRegister);
        verifypassword = (EditText) findViewById(R.id.etFanPasswordRegister2);

        register = (Button) findViewById(R.id.btnfanRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            //Code acquired and modified from this youtube video for firebase register functionality
            //"https://www.youtube.com/results?search_query=%231+login+and+registration+android+app+tutorial+using+firebase+authentication+-+create+user"
            public void onClick(View v) {
                String emailreg = email.getText().toString();
                String firstnamereg = firstname.getText().toString();
                String secondnamereg = secondname.getText().toString();
                String passwordreg = password.getText().toString();
                String verpassword = verifypassword.getText().toString();

                //validates correct user input
                if (firstnamereg.isEmpty()) {
                    firstname.setError("Please enter first name");
                    firstname.requestFocus();
                } else if (secondnamereg.isEmpty()) {
                    secondname.setError("Please enter second name");
                    secondname.requestFocus();
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
                } else {
                    register(firstnamereg, secondnamereg, emailreg, passwordreg);
                }
            }
    });
}

    private void register(String firstname, String username, String email, String password){
        //connects to firebase
        //links busker class with new email+password in firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            //https://www.youtube.com/watch?v=WOmBT_N1mKY&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=2&ab_channel=KODDev
                            //I went back and added hashmap functionality using this video

                            reference = FirebaseDatabase.getInstance().getReference().child("Fans").child(userId);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("username", username.toLowerCase());
                            hashMap.put("firstname", firstname);
                            hashMap.put("email", email);


                     /*   Busker busker = new Busker(firstnamereg, bioreg, emailreg, userId);
                        FirebaseDatabase.getInstance().getReference("Buskers")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                                .setValue(busker).addOnCompleteListener(new OnCompleteListener<Void>()
                       */

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(fanRegister.this, "Busker has been registered successfully", Toast.LENGTH_LONG).show();


                                    }
                                }
                            });
                        } else {
                            Toast.makeText(fanRegister.this, "Registration has failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}