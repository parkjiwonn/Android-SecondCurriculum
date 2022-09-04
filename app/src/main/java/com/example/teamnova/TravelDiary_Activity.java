package com.example.teamnova;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TravelDiary_Activity extends AppCompatActivity {

    private Button btn_addtravel;
    RecyclerView tRecyclerView = null;
    TravelAdapter tAdapter = null;
    ArrayList<TravelData> tList;
    static int t =0;
    String CurPost ;
    String DelPost;

    Gson gson = new Gson();
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_diary);

//        Intent intent = getIntent();
//        String time = intent.getStringExtra("key");
        //Log.e("기본정보에서 받아온 시간", time);



        tRecyclerView = findViewById(R.id.travelRecyclerView);
        tList = new ArrayList<>();

        tAdapter = new TravelAdapter(tList);
        tRecyclerView.setAdapter(tAdapter);
        tRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        //tAdapter.notifyDataSetChanged();

        preferences = getSharedPreferences("Diary", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String result = preferences.getString(Diary_Activity.writetime, "fail");

        if(result == "fail")
        {
            // 결과가 없으면 그냥 넘어가기.
        }
        else{

            ArrayList<TravelData> arrayList;
            Type type =new TypeToken<ArrayList<TravelData>>(){}.getType();
            arrayList = gson.fromJson(result,type);
            //System.out.println(arrayList.get(0).getSpot());

            for(int i =0; i<arrayList.size(); i++)
            {
                //arrayList의 사이즈만큼 반복문을 돌린다.
                Log.e("for", arrayList.get(i).toString());

                String date = arrayList.get(i).getDate();
                String clock = arrayList.get(i).getTime();
                String spot = arrayList.get(i).getSpot();
                String content = arrayList.get(i).getContent();
                String address = arrayList.get(i).getAddress();
                String image = arrayList.get(i).getImage();
                System.out.println(arrayList.get(i).getDate());
                Log.e("반복","시작");

                tList.add(new TravelData(date, clock, spot, content, address, image));
                // 리사이클러 뷰의 DList에 넣는다.

            }


            tAdapter.notifyDataSetChanged();

        }



        btn_addtravel= (Button) findViewById(R.id.btn_addtravel);
        btn_addtravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TravelDiary_Activity.this, AddTravelDiary_Activity.class);

                startActivity(intent);

            }
        });

        tAdapter.setOnItemClickListener(new TravelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int tpos) {
                String name = tList.get(tpos).getSpot();
                Toast.makeText(getApplicationContext(), name+"다이어리를 선택했습니다.",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(TravelDiary_Activity.this, DiaryView_Activity.class);
                intent.putExtra("date", tList.get(tpos).getDate());
                intent.putExtra("time", tList.get(tpos).getTime());
                intent.putExtra("spot", tList.get(tpos).getSpot());
                intent.putExtra("content", tList.get(tpos).getContent());
                intent.putExtra("address", tList.get(tpos).getAddress());
                intent.putExtra("image", tList.get(tpos).getImage());

                startActivity(intent);
                finish();
            }

            @Override
            public void onEditClick(View v, int tpos) {
                Intent intent = new Intent(TravelDiary_Activity.this, EditTravelDiary_Activity.class);
                t = tpos;

                intent.putExtra("date", tList.get(t).getDate());
                intent.putExtra("time", tList.get(t).getTime());
                intent.putExtra("spot", tList.get(t).getSpot());
                intent.putExtra("content", tList.get(t).getContent());
                intent.putExtra("address", tList.get(t).getAddress());
                intent.putExtra("image", tList.get(t).getImage());

                startActivityForResult(intent, 3000);

            }

            @Override
            public void onDeleteClick(View v, int tpos) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TravelDiary_Activity.this);

                builder.setTitle("[ 삭제 확인 ]");
                builder.setMessage("삭제하시겠습니까?");

                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        tList.remove(tpos);
                        tAdapter.notifyItemRemoved(tpos);

                        DelPost = gson.toJson(tList);

                        editor.putString(Diary_Activity.writetime, DelPost);
                        editor.commit();



                    }
                });

                builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(TravelDiary_Activity.this, Diary_Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 3000)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                String edate = data.getStringExtra("date");
                String etime = data.getStringExtra("time");
                String espot = data.getStringExtra("spot");
                String econtent = data.getStringExtra("content");
                String eaddress = data.getStringExtra("address");
                String eimage = data.getStringExtra("image");

                tList.get(t).setDate(edate);
                tList.get(t).setTime(etime);
                tList.get(t).setSpot(espot);
                tList.get(t).setContent(econtent);
                tList.get(t).setAddress(eaddress);
                tList.get(t).setImage(eimage);

                tAdapter.notifyItemChanged(t,null);
                CurPost = gson.toJson(tList);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Diary_Activity.writetime, CurPost);
                editor.commit();

            }
        }
    }

    // 이메일 공유
    public void process (View view)
    {
        Intent intent = null, chooser = null;

        if(view.getId()==R.id.sendEmial)
        {
            intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto :"));
            String[] to={"slidener@gamil.com", "dolphindelopers@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT, "여행 다이어리 공유 상황");
            intent.putExtra(Intent.EXTRA_TEXT, "어플리케이션으로부터 온 여행 다이어리입니다.");
            intent.setType("message/rfc822");
            chooser = Intent.createChooser(intent, "send email");
            startActivity(chooser);

        }
    }
}