package com.example.teamnova;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AddTravelDiary_Activity extends AppCompatActivity {

    private TextView textView_Date;
    private TextView textView_time;
    private EditText et_spot;
    private EditText et_content;
    private TextView et_address;
    private ImageView imageView;
    ArrayList<TravelData> travellist;
    private Button btn_map;

    String imagePath ="";
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat imageDate = new SimpleDateFormat("yyyyMMdd_HHmmss");

    private Button btn_move;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private TimePickerDialog.OnTimeSetListener callbackMethod2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel_diary);


//        Intent intent = getIntent();
//        String writetime = intent.getStringExtra("write");
//        Log.e("여행지 기본정보에서 두번째로 받아온 포지션", writetime);


        this.InitializeView();
        this.InitializeListener();

        this.InitializeView2();
        this.InitializeListener2();

        SharedPreferences preferences = getSharedPreferences("Diary", MODE_PRIVATE);
        // "Diary"라는 shared preference 생성하기
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new GsonBuilder().create();

        textView_Date = findViewById(R.id.textView_date);
        textView_time = findViewById(R.id.textView_time);
        et_spot = findViewById(R.id.et_spot);
        et_content = findViewById(R.id.et_content);
        et_address = findViewById(R.id.et_address);
        imageView = findViewById(R.id.imageView);



        btn_move =findViewById(R.id.btn_move);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTravelDiary_Activity.this, TravelDiary_Activity.class);
                String spot = et_spot.getText().toString();
                String content = et_content.getText().toString();
                String address = et_address.getText().toString();
                String date = textView_Date.getText().toString();
                String time = textView_time.getText().toString();

                TravelData travelData ;

                if(imagePath.length() > 0)
                {
                    Log.e("이미지 경로 있을 때", imagePath);
                    travelData= new TravelData(date,time,spot,content,address, imagePath);
                    Log.e("travelDate", travelData.getDate());
                    Log.e("imagePath",travelData.getImage());

                }
                else
                {
                    imagePath = "null";
                    Log.e("이미지 경로 없을 때", imagePath);
                    travelData = new TravelData(date, time,spot,content,address, imagePath);
                    Log.e("travelDate", travelData.getDate());
                }


                String travel = preferences.getString(Diary_Activity.writetime, "");
                //여행 기본정보 작성 시간이 key 값이 된다.

                // 회원가입을 하고 데이터 저장을 한다는것은 spotinfo에 아무런 데이터도 없다는 말.
                //회원가입 id를 키값으로 하는 데이터가 없을때

                // log id를 키값으로 갖는 데이터가 없으면 새로 json array 를 만들어라.
                if(travel.equals(""))
                {
                    String data = gson.toJson(travelData);
                    // 자바 객체를 문자열로 만듬.
                    try {
                        JSONObject obj = new JSONObject(data);
                        Log.e("obj", obj.toString());
                        JSONArray array = new JSONArray();
                        array.put(obj);
                        Log.e("array", array.toString());
                        //json 객체에 java 객체를 문자열로 만든 인자를 넣고 그 json 객체를 json array에 넣는다.

                        String result = array.toString();

                        editor.putString(Diary_Activity.writetime, result);
                        Log.e("result", result);
                        editor.commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    Log.e("travel",travel);
                    travellist = new ArrayList<>();
                    Log.e("travellist", travellist.toString());
                    Log.e("travel",travel);
                    travellist = gson.fromJson(travel, new TypeToken<ArrayList<TravelData>>(){}.getType() );
                    Log.e("travellist", travellist.toString());

                    // datalist = new ArrayList<>();
                    travellist.add(travelData);
                    // Log.e("datalist", datalist.toString());


                    editor.putString(Diary_Activity.writetime, gson.toJson(travellist));

                    editor.commit();

                }





                startActivity(intent);
//                intent.putExtra("ttime", writetime);
                finish();
            }
        });

        // 지도 버튼
        btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTravelDiary_Activity.this, map.class);
                startActivityForResult(intent, 1000);
            }
        });

        //갤러리에서 이미지 업로드하기 버튼.
        Button imageButton = findViewById(R.id.btn_gallery);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,0);
            }
        });


    }

    // 갤러리에서 이미지 불러오기.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(resultCode == Activity.RESULT_OK){
//            if(requestCode ==0){
//                imagePath = data.getDataString();
//            }
//            else if(requestCode == 1000)
//            {
//                data = getIntent();
//                String map = data.getStringExtra("map");
//                Log.e("map",map);
//                et_address.setText(map);
//                Log.e("et_address", et_address.toString());
//            }
//        }

        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK){

                imagePath = data.getDataString();

            }
        }
        else if(requestCode ==1000){
            if(resultCode == Activity.RESULT_OK) {

                //data = getIntent();
                String str = data.getStringExtra("map");
                Log.e("map", str);
                et_address.setText(str);
                Log.e("et_address", et_address.toString());

            }
        }



        if(imagePath.length()>0){
            Log.e("ImagePath", imagePath);
            Glide.with(this).load(imagePath).into(imageView);

        }



    }

    // 날짜 다이얼로그 구현

    public void InitializeView()
    {
        textView_Date = (TextView)findViewById(R.id.textView_date);
    }

    public void InitializeListener()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                monthOfYear += 1;
                textView_Date.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");
            }
        };
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2019, 5, 24);

        dialog.show();
    }

    public void InitializeView2()
    {
        textView_time = (TextView)findViewById(R.id.textView_time);
    }

    public void InitializeListener2()
    {
        callbackMethod2 = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                textView_time.setText(hourOfDay + "시" + minute + "분");
            }
        };
    }
    public void OnClickHandler2(View view)
    {
        TimePickerDialog dialog = new TimePickerDialog(this, callbackMethod2, 8, 10, true);

        dialog.show();
    }

}