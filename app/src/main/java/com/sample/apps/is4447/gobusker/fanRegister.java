package com.sample.apps.is4447.gobusker;

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
import com.google.firebase.database.FirebaseDatabase;

public class fanRegister extends AppCompatActivity {
    //connection to firebase
    private FirebaseAuth mAuth;
    //declaring variables
    Button register;
    EditText email, password, firstname, secondname;

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

                //validates correct user input
                if (firstnamereg.isEmpty()) {
                    firstname.setError("Please enter first name");
                    firstname.requestFocus();
                }
                if (secondnamereg.isEmpty()) {
                    secondname.setError("Please enter second name");
                    secondname.requestFocus();
                }
                if (emailreg.isEmpty()) {
                    email.setError("Please enter email");
                    email.requestFocus();
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailreg).matches()) {
                    email.setError("Please enter valid email");
                    email.requestFocus();
                }
                if (passwordreg.isEmpty()) {
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                if (passwordreg.length() < 6) {
                    password.setError("Password must be longer than 6 characters");
                    password.requestFocus();
                }
                //connects to firebase
                //links busker class with new email+password in firebase
                mAuth.createUserWithEmailAndPassword(emailreg, passwordreg)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Busker busker = new Busker(firstnamereg, secondnamereg, emailreg);
                                    FirebaseDatabase.getInstance().getReference("Fans")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(busker).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //if success or failure, inform user
                                            if (task.isSuccessful()) {
                                                Toast.makeText(fanRegister.this, "Fan has been registered successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(fanRegister.this, "Registration has failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
            }
        });
    }
}