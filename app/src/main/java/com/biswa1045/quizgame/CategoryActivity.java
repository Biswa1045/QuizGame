package com.biswa1045.quizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.LinearLayout;


import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;

import static com.biswa1045.quizgame.SplashActivity.catList;

public class CategoryActivity extends AppCompatActivity {

    private GridView catGrid;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //banner
        AudienceNetworkAds.initialize(this);

        adView = new AdView(this, "278968300447510_278974973780176", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.adView2);
        adContainer.addView(adView);
        adView.loadAd();

//banner end
        //end
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("categories");

        catGrid = findViewById(R.id.catGridview);
        CatGridAdapter adapter = new CatGridAdapter(catList);
        catGrid.setAdapter(adapter);


    }

   // @Override
   // protected void onDestroy() {
   //     if (adView != null) {
   //         adView.destroy();
    //    }
     //   super.onDestroy();
   // }

}
