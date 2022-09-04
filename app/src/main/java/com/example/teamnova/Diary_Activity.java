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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Diary_Activity extends AppCompatActivity {

    private Button btn_AddDiary;
    RecyclerView DRecyclerView = null;
    DiaryAdapter DAdapter = null;
    ArrayList<DiaryData> DList;
    Gson gson = new Gson();
    SharedPreferences preferences;
    SharedPreferences preferences2;
    static String writetime;


    static int d=0;//position 값 받아오기.
    String CurPost ;
    String DelPast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        DRecyclerView = findViewById(R.id.DiaryRecyclerview);
        DList = new ArrayList<>();

        DAdapter = new DiaryAdapter(DList);
        DRecyclerView.setAdapter(DAdapter);
        DRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        // 리사이클러뷰 어댑터 연결, 레이아웃매니저 끝.
        //DAdapter.notifyDataSetChanged();

        preferences = getSharedPreferences("SpotInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        // editor를 선언해주는데 sharedpreference에 null값이여서 오류가 뜬것임.

        preferences2 = getSharedPreferences("Diary", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();

        String result = preferences.getString(Login_Activity.id, "fail");

        if(result == "fail")
        {
            // 결과가 없으면 그냥 넘어가기.
        }
        else{

            ArrayList<DiaryData> arrayList;
            Type type =new TypeToken<ArrayList<DiaryData>>(){}.getType();
            arrayList = gson.fromJson(result,type);
            System.out.println(arrayList.get(0).getTitle());

            for(int i =0; i<arrayList.size(); i++)
            {
                //arrayList의 사이즈만큼 반복문을 돌린다.
                Log.e("for", arrayList.get(i).toString());

                String title = arrayList.get(i).getTitle();
                String place = arrayList.get(i).getPlace();
                String start = arrayList.get(i).getStart();
                String finish = arrayList.get(i).getFinish();
                String time = arrayList.get(i).getTime();
                System.out.println(arrayList.get(i).getTitle());
                Log.e("title","dd");

                DList.add(new DiaryData(title, place, start, finish, time));
                // 리사이클러 뷰의 DList에 넣는다.

            }

            DAdapter.notifyDataSetChanged();

        }

        btn_AddDiary = findViewById(R.id.btn_AddDiary);
        btn_AddDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Diary_Activity.this, AddDiary_Activity.class);
                startActivity(intent);
                // 여행지 기본 정보 추가하는 페이지로 이동하게 됨.


            }
        });

        DAdapter.setOnItemClickListener(new DiaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int dpos) {
                String name = DList.get (dpos).getTitle ();
                Toast.makeText (getApplicationContext(), name+"의 다이어리로 이동합니다.", Toast.LENGTH_SHORT).show ();
                Intent intent = new Intent(Diary_Activity.this, TravelDiary_Activity.class);

                writetime = DList.get(dpos).getTime();

                Log.e("writetime", writetime);

                startActivity(intent);
                //클릭한 아이템의 인덱스를 넘겨줘야한다.


                //엑티비티에서 커스텀 리스너 객체를 생성하고 어댑터에 전달한다.
            }

            @Override
            public void onEditClick(View v, int dpos) {
                Intent intent = new Intent(Diary_Activity.this, EditDiary_Activity.class);
                d = dpos;

                intent.putExtra("title",DList.get(d).getTitle());
                intent.putExtra("where",DList.get(d).getPlace());
                intent.putExtra("start",DList.get(d).getStart());
                intent.putExtra("finish",DList.get(d).getFinish());

                startActivityForResult(intent, 2000);



            }

            @Override
            public void onDeleteClick(View v, int dpos) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Diary_Activity.this);
                // 현재 액티비티로 해야 한다.

                builder.setTitle("[ 삭제 확인 ]");
                builder.setMessage("여행지 카테고리 삭제시 해당 카테고리에 해당하는 다이어리도 삭제됩니다. 그래도 삭제하시겠습니까?");

                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        DList.remove(dpos);
                        DAdapter.notifyItemRemoved(dpos);


                        DelPast = gson.toJson(DList);

                        editor.putString(Login_Activity.id, DelPast);
                        editor.commit();

                        editor2.remove(Diary_Activity.writetime);
                        editor2.commit();



                    }
                });

                builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
                //builder.show를 해야 한다.


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Diary_Activity.this, Start_Activity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2000)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                String etitle = data.getStringExtra("title");
                String ewhere = data.getStringExtra("where");
                String estart = data.getStringExtra("start");
                String efinish = data.getStringExtra("finish");

                DList.get(d).setTitle(etitle);
                DList.get(d).setPlace(ewhere);
                DList.get(d).setStart(estart);
                DList.get(d).setFinish(efinish);

                DAdapter.notifyItemChanged(d,null);
                CurPost = gson.toJson(DList);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Login_Activity.id, CurPost);
                editor.commit();

            }
        }
    }

}