package com.biswa1045.quizgame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class profile extends AppCompatActivity {
    private static final int GALLERY_INTENT_CODE = 1023 ;
    TextView email,username,changeProfileImage;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
DatabaseReference reference;
    ProgressBar progressBar;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;
Button logout;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //rewared

        //end
        //banner
        //banner
        AudienceNetworkAds.initialize(this);

        adView = new AdView(this, "278968300447510_278979327113074", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.adView6);
        adContainer.addView(adView);
        adView.loadAd();

//banner end
        //end
logout=findViewById(R.id.logout);

        BottomNavigationView bottomNavigationNiew = findViewById(R.id.buttom_navigation);
        bottomNavigationNiew.setSelectedItemId(R.id.profile_nav);
        bottomNavigationNiew.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_nav:
                        startActivity(new Intent(getApplicationContext(),HOMEActivity.class));
                        overridePendingTransition(0,0);
                        return  true;
                    case R.id.profile_nav:

                        return true;
                    case R.id.leaderboard_nav:
                        startActivity(new Intent(getApplicationContext(),leaderboarderActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.setting_nav:
                        startActivity(new Intent(getApplicationContext(),settingActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


            progressBar = findViewById(R.id.progressBar);
            username = findViewById(R.id.profileUN);
            email    = findViewById(R.id.profileEmail);
            profileImage = findViewById(R.id.profileImage);
            changeProfileImage = findViewById(R.id.changeProfile);
            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();

            logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(profile.this);
                builder.setMessage("Are you sure !!!")
                        .setCancelable(true)

                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fAuth.signOut();
                                Intent in=new Intent(profile.this,MainActivity.class);
                                startActivity(in);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                   AlertDialog alertDialog = builder.create();
                   alertDialog.show();




            }
        });
            storageReference = FirebaseStorage.getInstance().getReference();

            StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(profileImage);

                }
            });

            userId = fAuth.getCurrentUser().getUid();
            user = fAuth.getCurrentUser();

            DocumentReference documentReference = fStore.collection("users").document(userId);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(documentSnapshot.exists()){
                        username.setText(documentSnapshot.getString("username"));

                        email.setText(documentSnapshot.getString("email"));

                    }else {
                        Log.d("tag", "onEvent: User data do not exists");
                    }
                }
            });




            changeProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // open gallery

                        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(openGalleryIntent, 1000);

                }
            });


        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();

                //profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);
                //

                 //
            }
        }

    }

    private void uploadImageToFirebase(Uri imageUri) {
        // uplaod image to firebase storage
        progressBar.setVisibility(View.VISIBLE);

        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);

                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
  //  @Override
   // protected void onDestroy() {
    //    if (adView != null) {
       //     adView.destroy();
       // }
      //  super.onDestroy();
   // }



}