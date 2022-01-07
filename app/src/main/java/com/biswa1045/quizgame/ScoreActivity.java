package com.biswa1045.quizgame;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScoreActivity extends AppCompatActivity {


    private TextView score;
int total;
Button SHARE;
int atmpt;
    private Button done;
    private final String TAG =ScoreActivity.class.getSimpleName();
FirebaseUser user;
TextView txt;
    private AdView adView;
DatabaseReference reference;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        txt=findViewById(R.id.rew_scr);
        //int...
        AudienceNetworkAds.initialize(ScoreActivity.this);
        interstitialAd = new InterstitialAd(ScoreActivity.this, "278968300447510_278980230446317");
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
        //int ads end


        //banner
        AudienceNetworkAds.initialize(this);

        adView = new AdView(this, "278968300447510_278975593780114", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.adView5);
        adContainer.addView(adView);
        adView.loadAd();

//banner end


//share
        SHARE=findViewById(R.id.share_score);
        SHARE.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                try{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())));
                }catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                }
            }
        });
//end
        done = findViewById(R.id.sa_done);
        score = findViewById(R.id.sa_score);
        String score_str = getIntent().getStringExtra("SCORE");
        total = getIntent().getIntExtra("SCORE2",0);
atmpt = getIntent().getIntExtra("QUESTION",0);
        score.setText(score_str);


user= FirebaseAuth.getInstance().getCurrentUser();
reference= FirebaseDatabase.getInstance().getReference();
reference.child("Score").child(user.getUid()).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
   public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists()){
           total += Integer.parseInt(snapshot.getValue().toString());
        }

        snapshot.getRef().setValue(total);
   }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});



        reference.child("Score").child(user.getUid()).child("attempt").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                   atmpt += Integer.parseInt(snapshot.getValue().toString());
                }

                snapshot.getRef().setValue(atmpt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in=new Intent(ScoreActivity.this, HOMEActivity.class);
                startActivity(in);

                finish();
            }
        });







       // done.setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View v) {

             //   Intent intent = new Intent(ScoreActivity.this,CategoryActivity.class);
               // ScoreActivity.this.startActivity(intent);
                //ScoreActivity.this.finish();


            //}
        //});

    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(ScoreActivity.this, HOMEActivity.class);
        startActivity(in);

        finish();
        }
   // @Override
   // protected void onDestroy() {
   //     if (adView != null) {
   //         adView.destroy();
   //     }
  //      super.onDestroy();
  //  }

}