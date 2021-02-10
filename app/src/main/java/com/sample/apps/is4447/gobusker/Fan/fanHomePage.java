package com.sample.apps.is4447.gobusker.Fan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.sample.apps.is4447.gobusker.MainActivity;
import com.sample.apps.is4447.gobusker.PayPalConfig;
import com.sample.apps.is4447.gobusker.R;

import java.math.BigDecimal;

public class fanHomePage extends AppCompatActivity {
    //declaring variables
    Button logout;

    private FirebaseUser fan;
    private DatabaseReference reference;
    private String FanID;
    private Button mPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_home_page);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        logout = (Button) findViewById(R.id.btnfanLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            //Code acquired and modified from this youtube video for firebase logout functionality
            //https://www.youtube.com/watch?v=DRBLazxi6Eg&ab_channel=CodeWithMazn
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(fanHomePage.this, MainActivity.class);
                startActivity(i);
            }
        });
        //Code acquired and modified from this youtube video for firebase User information display functionality
        //https://www.youtube.com/watch?v=-plgl1EQ21Q
        fan = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Fans");
        FanID = fan.getUid();

        //set variables that are connected to elements on the pages interface
        final TextView firstnameFan = (TextView) findViewById(R.id.tvFanShowFirst);
        final TextView surnameFan = (TextView) findViewById(R.id.tvFanShowSurname);
        final TextView emailFan = (TextView) findViewById(R.id.tvFanShowEmail);

        mPay = findViewById(R.id.btnPay);

        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payPalPayment();
            }
        });



        //search fan ID in Fan table
        reference.child(FanID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //fill info into single instance of fan class
                Fan fanProfile = snapshot.getValue(Fan.class);

                //if last part was successful, and busker class is filled
                if (fanProfile != null) {
                    //Fill new varibles with info from the busker class
                    String firstname = fanProfile.firstname;
                    String surname = fanProfile.secondname;
                    String email = fanProfile.email;

                    //Use these new varibales to set the text of the elements on the interface
                    firstnameFan.setText(firstname);
                    surnameFan.setText(surname);
                    emailFan.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(fanHomePage.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

    }
String testPayment = "10";
private int PAYPAL_REQUEST_CODE = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);
    private void payPalPayment(){
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(testPayment)), "USD", "Donation",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, PAYPAL_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){

            } else{
                Toast.makeText(getApplicationContext(), "Payment unsuccessful", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}