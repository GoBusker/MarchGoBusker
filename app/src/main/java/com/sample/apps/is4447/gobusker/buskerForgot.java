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
import com.google.firebase.auth.FirebaseAuth;

public class buskerForgot extends AppCompatActivity {
    //declaring variables
    private EditText etEmail;
    private Button btnReset;

    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busker_forgot);

        //set variables to elements on interface
        etEmail = (EditText) findViewById(R.id.etBuskerForgotEmail);
        btnReset = (Button) findViewById(R.id.btnBuskerForgotEmail);

        //connect to firebase, get current user details
        Auth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }
    //Code acquired and modified from this youtube video for firebase password reset functionality
    //https://www.youtube.com/watch?v=w-Uv-ydX_LY&ab_channel=CodeWithMazn
        private void resetPassword(){
        String email = etEmail.getText().toString().trim();

            //validate edittext boxes
            if (email.isEmpty()) {
                etEmail.setError("Email is required");
                etEmail.requestFocus();
                return;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Please provide valid email");
                etEmail.requestFocus();
                return;
            }
            //Use firebase command to send reset email, give feedback to let user know if it was successful
            Auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(buskerForgot.this, "Check your email for password reset", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(buskerForgot.this, "Oops, something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
    }
