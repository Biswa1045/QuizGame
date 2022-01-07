package com.biswa1045.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class HOMEActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private AdView adView;
    TextView name,attempt;
TextView score;
DatabaseReference ref;
    FirebaseUser user;


    ImageView profileImage2;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_o_m_e);
        checkConnection();
        //banner
        //banner
        AudienceNetworkAds.initialize(this);

        adView = new AdView(this, "278968300447510_278974677113539", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.adView1);
        adContainer.addView(adView);
        adView.loadAd();

//banner end
        //end

        //navigation
        BottomNavigationView bottomNavigationNiew = findViewById(R.id.buttom_navigation);
        bottomNavigationNiew.setSelectedItemId(R.id.home_nav);
        bottomNavigationNiew.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_nav:
                        return  true;
                    case R.id.profile_nav:
                        startActivity(new Intent(getApplicationContext(),profile.class));
                        overridePendingTransition(0,0);
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
        //end
        attempt=findViewById(R.id.attempt);
        name=findViewById(R.id.profileUN);
        score=findViewById(R.id.score_home);
        user= FirebaseAuth.getInstance().getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference().child("Score").child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String score_str=snapshot.child("score").getValue().toString();
                score.setText(score_str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref= FirebaseDatabase.getInstance().getReference().child("Score").child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name_str=snapshot.child("name").getValue().toString();
                name.setText(name_str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref= FirebaseDatabase.getInstance().getReference().child("Score").child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String atmpt_str=snapshot.child("attempt").getValue().toString();
                attempt.setText(atmpt_str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        profileImage2=findViewById(R.id.profileImage2);

        findViewById(R.id.category).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in=new Intent(HOMEActivity.this, CategoryActivity.class);
                startActivity(in);
            }
        });


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();



        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage2);
            }
        });
//user name show

        //end

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
   // @Override
    //protected void onDestroy() {
     //   if (adView != null) {
     //       adView.destroy();
      //  }
      //  super.onDestroy();
   // }
    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

            }
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
