package com.sample.apps.is4447.gobusker.Busker;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.sample.apps.is4447.gobusker.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BuskerPost extends AppCompatActivity {

    //I used this Youtube video as a reference for creating posts with Images
// https://www.youtube.com/watch?v=GV1qbi59rgc&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=5&ab_channel=KODDev

    Uri imageUri;
    String myUri = "";
    StorageTask uploadTask;
    StorageReference storageReference;

    ImageView close, image_added;
    TextView post;
    EditText description, location;

    CalendarView calendar;

    String timezone;

    String calendarstring, timestring;

    private FirebaseUser firebaseBusker;

    List<String> idList;

    Button date, time;

    Button test;
    RelativeLayout  timeframe;
    TimePicker timepicker;
    CalendarView calview;
    RelativeLayout calendarframe;
    Boolean cal = true;
    Boolean tim = false;

    TextView testing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busker_post);

        close = findViewById(R.id.close);
        image_added = findViewById(R.id.image_added);
        post = findViewById(R.id.post);
        description = findViewById(R.id.description);

        location = findViewById(R.id.location);

        calendar = findViewById(R.id.calendarView);

        date = findViewById(R.id.btnDate);
        time = findViewById(R.id.btnTime);


        timeframe = findViewById(R.id.timeframe);

        timepicker = findViewById(R.id.timepicker);
        timepicker.setIs24HourView(true);



        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendarstring = year + "/" + month + "/" + dayOfMonth;
            }
        });

        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                if (i >12){
                    timezone = "PM";
                    i-=12;
                } else {
                    timezone = "AM";
                }
                if (i1 < 10){
                    timestring = i + ":0" +i1 + timezone;
                } else {
                    timestring = i + ":" + i1 + timezone;
                }
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cal == false) {
                    calendar.setVisibility(View.VISIBLE);
                    cal = true;
                    timepicker.setVisibility(View.INVISIBLE);
                    tim = false;
                } else if (cal == true) {
                    calendar.setVisibility(View.GONE);
                    cal = false;
                    timepicker.setVisibility(View.VISIBLE);
                    tim = true;
                }
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tim == false) {
                    timepicker.setVisibility(View.VISIBLE);
                    tim = true;
                    calendar.setVisibility(View.GONE);
                    cal = false;
                } else if (tim == true) {
                    timepicker.setVisibility(View.INVISIBLE);
                    tim = false;
                    calendar.setVisibility(View.VISIBLE);
                    cal = true;
                }
            }
        });




        storageReference = FirebaseStorage.getInstance().getReference("post");

        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();

        idList = new ArrayList<>();


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuskerPost.this, BuskerFeed.class));
                finish();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String des = description.getText().toString();
                String loc = location.getText().toString();
                long cale = calendar.getDate();
                String timep = time.toString();
                if (des.isEmpty()) {
                   description.setError("Please enter busk location");
                   description.requestFocus();
                } else if (loc.isEmpty()) {
                    location.setError("Please enter busk location");
                    location.requestFocus();
//                }  else if (cale.is()){
//                    Toast.makeText(getApplicationContext(), "Please enter a date", Toast.LENGTH_SHORT).show();
//                } else if (timep.isEmpty()){
//                    Toast.makeText(getApplicationContext(), "Please enter a time", Toast.LENGTH_SHORT).show();
//                }
                }
                else {
                    uploadImage();
                }
            }
        });

        CropImage.activity()
                .setAspectRatio(1, 1)
                .start(BuskerPost.this);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting");
        progressDialog.show();

        if (imageUri != null) {
            StorageReference filereference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = filereference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        myUri = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                        String postId = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("postid", postId);
                        hashMap.put("postimage", myUri);
                        hashMap.put("description", description.getText().toString());

                        hashMap.put("location", location.getText().toString());
                        hashMap.put("date", calendarstring);

                        hashMap.put("time", timestring);

                        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

                        reference.child(postId).setValue(hashMap);

                        progressDialog.dismiss();

                        startActivity(new Intent(BuskerPost.this, BuskerFeed.class));
                        addNotifications(FirebaseAuth.getInstance().getCurrentUser().getUid(), postId);
                        finish();
                    } else {
                        Toast.makeText(BuskerPost.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(BuskerPost.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            image_added.setImageURI(imageUri);


        }else {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BuskerPost.this, BuskerFeed.class));
            finish();
        }
    }

    private void addNotifications(String userid, String postid){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Follow")
                .child(firebaseBusker.getUid()).child("followers");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //String uID = (String) snapshot.child("id").getValue();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(snapshot.getKey());

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("userid", firebaseBusker.getUid());
                    hashMap.put("text", " just posted an event");
                    hashMap.put("postid", postid);
                    hashMap.put("ispost", true);

                    reference.push().setValue(hashMap);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}