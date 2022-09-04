package com.example.teamnova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Start_Activity extends AppCompatActivity {
    private Button btn_trip;
    private Button btn_main;
    private ImageView img_ad;
    private long backBtnTime =0;

    private int i = 0;

    //메인 화면에서 뒤로가기 했을때 로그인화면으로 가지 않게끔 하기.
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
        }
        else
        {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다." , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // 이번 여행은 어디로? 액티비티로 이동 가능함.
        btn_trip = findViewById(R.id.btn_trip);
        btn_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start_Activity.this, TripAd_Activity.class);
                startActivity(intent);

            }
        });

        btn_main =findViewById(R.id.btn_main);
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start_Activity.this, Diary_Activity.class);
                startActivity(intent);
            }
        });

        img_ad = (ImageView) findViewById(R.id.img_ad);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        handler.sendMessage(handler.obtainMessage());
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        myThread.start();
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            updateThread();
        }
    };

    private void updateThread(){
        int mod = i % 3;

        switch (mod){

            case 0 :
                i++;
                img_ad.setImageResource(R.drawable.ad);
                //대만
                img_ad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://tour.interpark.com/package/d3/4905/?packagecd=4905&mbn=tourpackage&mln=pkgd2r_category_4&utm_medium=cpc&utm_source=naver&utm_campaign=tour_abroadpackage_20210601_paidsearch_pc_cpc&utm_content=consider_34&utm_term=%EB%8C%80%EB%A7%8C%ED%95%AD%EA%B3%B5%EA%B6%8C&n_media=27758&n_query=%EB%8C%80%EB%A7%8C%ED%95%AD%EA%B3%B5%EA%B6%8C&n_rank=1&n_ad_group=grp-a001-01-000000026796187&n_ad=nad-a001-01-000000180405694&n_keyword_id=nkw-a001-01-000004494217478&n_keyword=%EB%8C%80%EB%A7%8C%ED%95%AD%EA%B3%B5%EA%B6%8C&n_campaign_type=1&n_ad_group_type=1"));
                        startActivity(urlintent);
                    }
                });
                break;

            case 1:
                i++;
                img_ad.setImageResource(R.drawable.ad2);
                //후쿠오카
                img_ad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://flights.myrealtrip.com/air/b2c/AIR/INT/AIRINTTRP0100100000.k1?KSESID=air:b2c:SELK138RB:SELK138RB::00&depCtyCode1=SEL&arrCtyCode1=FUK&utm_source=NAD&n_media=27758&n_query=%ED%9B%84%EC%BF%A0%EC%98%A4%EC%B9%B4%ED%95%AD%EA%B3%B5%EA%B6%8C&n_rank=2&n_ad_group=grp-a001-01-000000023264458&n_ad=nad-a001-01-000000168834016&n_keyword_id=nkw-a001-01-000003999630113&n_keyword=%ED%9B%84%EC%BF%A0%EC%98%A4%EC%B9%B4%ED%95%AD%EA%B3%B5%EA%B6%8C&n_campaign_type=1&n_ad_group_type=1&NaPm=ct%3Dl2syu1cw%7Cci%3D0AC0001WJBbw2GdYTvjZ%7Ctr%3Dsa%7Chk%3D504da293d21ab5e75994dd3998f58b25307d8253"));
                        startActivity(urlintent);
                    }
                });
                break;

//            case 2:
//                i++;
//                img_ad.setImageResource(R.drawable.ad3);
//                //여행상품
//                img_ad.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jigutour.co.kr/search_list.html?nation=&top_mode=top&tym=&td=&keyword=%BA%CE%BB%EA&n_media=27758&n_query=%EB%B6%80%EC%82%B0%EC%97%AC%ED%96%89&n_rank=5&n_ad_group=grp-m001-01-000000010812712&n_ad=nad-a001-01-000000002379758&n_keyword_id=nkw-m001-01-000000023820207&n_keyword=%EB%B6%80%EC%82%B0%EC%97%AC%ED%96%89&n_campaign_type=1&n_ad_group_type=1&NaPm=ct%3Dl1n9kqyg%7Cci%3D0AC0002Vf4PwemvDiL1p%7Ctr%3Dsa%7Chk%3D25b88cd9869263645d74ccfba7917f021faf17b0"));
//                        startActivity(urlintent);
//                    }
//                });
//                break;

            case 2:
                i++;
                img_ad.setImageResource(R.drawable.ad4);
                //모스크바
                img_ad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://tour.interpark.com/package/d3/9079/?packagecd=9079&utm_medium=cpc&utm_source=naver&utm_campaign=tour_abroadpackage_20210601_paidsearch_pc_cpc&utm_content=consider_34&utm_term=%EB%AA%A8%EC%8A%A4%ED%81%AC%EB%B0%94%EC%97%AC%ED%96%89%EC%83%81%ED%92%88&n_media=27758&n_query=%EB%AA%A8%EC%8A%A4%ED%81%AC%EB%B0%94%EC%97%AC%ED%96%89%EC%83%81%ED%92%88&n_rank=2&n_ad_group=grp-a001-01-000000026796343&n_ad=nad-a001-01-000000180406049&n_keyword_id=nkw-a001-01-000004494719681&n_keyword=%EB%AA%A8%EC%8A%A4%ED%81%AC%EB%B0%94%EC%97%AC%ED%96%89%EC%83%81%ED%92%88&n_campaign_type=1&n_ad_group_type=1"));
                        startActivity(urlintent);
                    }
                });
                break;

        }

    }
}