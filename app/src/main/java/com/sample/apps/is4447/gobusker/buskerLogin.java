package com.sample.apps.is4447.gobusker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class buskerLogin extends AppCompatActivity {
    //declaring variables
    Button register, login;
    EditText email, password;

    //connection to firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busker_login);
        //connection to firebase
        mAuth = FirebaseAuth.getInstance();

        //link variables with items from layour file
        register = (Button) findViewById(R.id.btnBuskerRegisterFromLogin);
        login = (Button) findViewById(R.id.btnbuskerLogin);
        email = (EditText) findViewById(R.id.etbuskerEmail);
        password = (EditText) findViewById(R.id.etbuskerPassword);

        //intent to register, if you havent already
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(buskerLogin.this,buskerRegister.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaillog = email.getText().toString();
                String passwordlog = password.getText().toString();

                //user validation
                if (emaillog.isEmpty()) {
                    email.setError("Please enter email");
                    email.requestFocus();
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emaillog).matches()) {
                    email.setError("Please enter valid email");
                    email.requestFocus();
                }
                if (passwordlog.isEmpty()) {
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                if (passwordlog.length() < 6) {
                    password.setError("Password must be longer than 6 characters");
                    password.requestFocus();
                }

                mAuth.signInWithEmailAndPassword(emaillog, passwordlog).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    //Code acquired and modified from this youtube video for firebase login functionality
                    //"https://www.youtube.com/watch?v=KB2BIm_m1Os&t=1s&ab_channel=John%27sAndroidStudioTutorialsJohn%27sAndroidStudioTutorials"
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser busker = FirebaseAuth.getInstance().getCurrentUser();
                            //Code acquired and modified from this youtube video for firebase email verification functionality
                            //"https://www.youtube.com/watch?v=15WRCpH-VG0&ab_channel=CodeWithMaznCodeWithMazn"
                            if(busker.isEmailVerified()) {
                                Intent i = new Intent(buskerLogin.this, buskerHomePage.class);
                                startActivity(i);
                            }else{
                                busker.sendEmailVerification();
                                Toast.makeText(buskerLogin.this, "Check your email to verify your account", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(buskerLogin.this, "Incorrect name or password, Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}