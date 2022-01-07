package com.biswa1045.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class settingActivity extends AppCompatActivity {
TextView SHARE,PP,FEEDBACK;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //banner
        //banner
        AudienceNetworkAds.initialize(this);

        adView = new AdView(this, "278968300447510_278979527113054", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.adView8);
        adContainer.addView(adView);
        adView.loadAd();

//banner end
        //end
       FEEDBACK=findViewById(R.id.feedback_sett);
        SHARE=findViewById(R.id.share_sett);
        PP=findViewById(R.id.pp_sett);
        FEEDBACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intUUU = new Intent(settingActivity.this, FEEDBACKActivity.class);
                startActivity(intUUU);
            }
        });
        PP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1 = new Intent(settingActivity.this, PPActivity.class);
                startActivity(int1);
            }
        });
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
        BottomNavigationView bottomNavigationNiew = findViewById(R.id.buttom_navigation);
        bottomNavigationNiew.setSelectedItemId(R.id.setting_nav);
        bottomNavigationNiew.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_nav:
                        startActivity(new Intent(getApplicationContext(),HOMEActivity.class));
                        overridePendingTransition(0,0);
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

                        return true;
                }
                return false;
            }
        });
    }
   // @Override
   // protected void onDestroy() {
    //    if (adView != null) {
     //       adView.destroy();
     //   }
     //   super.onDestroy();
   // }

}