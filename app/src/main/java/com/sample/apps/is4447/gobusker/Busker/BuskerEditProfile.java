package com.sample.apps.is4447.gobusker.Busker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class BuskerEditProfile extends AppCompatActivity {

    //I adapted this Youtube video for edit profile functionality
    //    https://www.youtube.com/watch?v=3NYIwEpYbOA&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=17&ab_channel=KODDev

    ImageView close;
     CircleImageView image_profile;
    TextView save, tv_change;
    MaterialEditText firstname, username, bio, facebook, instagram;

    private String image;

    FirebaseUser firebaseBusker;

    CheckBox musicianbox, jazzbox, rockbox, professionalbox, dancerbox;

    private Uri mImageUri;
    private StorageTask uploadTask;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busker_edit_profile);

        close = findViewById(R.id.close);
        image_profile = findViewById(R.id.image_profile);
        save = findViewById(R.id.save);
        tv_change = findViewById(R.id.tv_change);
        firstname = findViewById(R.id.firstname);
        username = findViewById(R.id.username);
        bio = findViewById(R.id.bio);

        facebook = findViewById(R.id.facebook);
        instagram = findViewById(R.id.instagram);

        musicianbox = findViewById(R.id.musiciancheck);
        jazzbox = findViewById(R.id.jazzcheck);
        rockbox = findViewById(R.id.rockcheck);
        professionalbox = findViewById(R.id.professionalcheck);
        dancerbox = findViewById(R.id.dancercheck);


        firebaseBusker = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference("uploads");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Busker busker = dataSnapshot.getValue(Busker.class);
                firstname.setText(busker.getFirstname());
                username.setText(busker.getUsername());
                bio.setText(busker.getBio());
                facebook.setText(busker.getFacebook());
                instagram.setText(busker.getInstagram());
                if (busker.isMusician() == true){
                    musicianbox.setChecked(true);
                }
                if (busker.isDancer() == true){
                    dancerbox.setChecked(true);
                }
                if (busker.isJazz() == true){
                    jazzbox.setChecked(true);
                }
                if (busker.isProfessional() == true){
                    professionalbox.setChecked(true);
                }
                if (busker.isRock() == true){
                    rockbox.setChecked(true);
                }

                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                Glide.clear(image_profile);
                if (map.get("imageurl") != null) {
                    image = map.get("imageurl").toString();
                    Glide.with(getApplicationContext()).load(image).into(image_profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(BuskerEditProfile.this);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Changes made!", Toast.LENGTH_SHORT).show();
                updateProfile(firstname.getText().toString(),
                        username.getText().toString(),
                        bio.getText().toString(),
                        facebook.getText().toString(),
                        instagram.getText().toString());

            }
        });
    }

    private void updateProfile(String firstname, String username, String bio, String facebook, String instagram) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("firstname", firstname);
        hashMap.put("username", username);
        hashMap.put("bio", bio);
        hashMap.put("instagram", instagram);
        hashMap.put("facebook", facebook);

        if (musicianbox.isChecked()){
            hashMap.put("musician", true);
        } else if (musicianbox.isChecked() == false){
            hashMap.put("musician", false);
        }
        if (jazzbox.isChecked()){
            hashMap.put("jazz", true);
        } else if (jazzbox.isChecked() == false){
            hashMap.put("jazz", false);
        }
        if (dancerbox.isChecked()){
            hashMap.put("dancer", true);
        } else if (dancerbox.isChecked() == false){
            hashMap.put("dancer", false);
        }
        if (rockbox.isChecked()){
            hashMap.put("rock", true);
        } else if (rockbox.isChecked() == false){
            hashMap.put("rock", false);
        }
        if (professionalbox.isChecked()){
            hashMap.put("professional", true);
        } else if (professionalbox.isChecked() == false){
            hashMap.put("professional", false);
        }



        reference.updateChildren(hashMap);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (mImageUri != null) {
            final StorageReference filereference = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            uploadTask = filereference.putFile(mImageUri);
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
                        String myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers").child(firebaseBusker.getUid());

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("imageurl", "" + myUrl);

                        reference.updateChildren(hashMap);
                        pd.dismiss();
                    } else {
                        Toast.makeText(BuskerEditProfile.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(BuskerEditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                uploadImage();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getApplicationContext(), "Something gone wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
}


