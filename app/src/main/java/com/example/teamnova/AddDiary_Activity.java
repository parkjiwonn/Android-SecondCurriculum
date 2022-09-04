package com.example.teamnova;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class AddDiary_Activity extends AppCompatActivity {

    private TextView tx_startday;
    private TextView tx_finishday;

    private EditText et_triptitle;
    private EditText et_where;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private DatePickerDialog.OnDateSetListener callbackMethod2;
    private Button btn_add;
    private String title;
    private String place;
    private String start;
    private String finish;
    ArrayList<DiaryData> datalist;
    // diarydata 리스트 만들기

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        tx_finishday = findViewById(R.id.tx_finishday);
        tx_startday = findViewById(R.id.tx_startday);
        et_triptitle = findViewById(R.id.et_triptitle);
        et_where = findViewById(R.id.et_where);


        this.InitializeView2();
        this.InitializeListener2();

        this.InitializeView();
        this.InitializeListener();

        SharedPreferences preferences = getSharedPreferences("SpotInfo", MODE_PRIVATE);
        // "spotinfo"라는 shared preference 생성하기
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new GsonBuilder().create();






        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddDiary_Activity.this, Diary_Activity.class);
                // 추가 버튼을 눌렀을때 sp에 저장되게 끔 하기.

                // 유저가 기입한 데이터들을 연결시켜줘야 한다.
                title = et_triptitle.getText().toString();
                place = et_where.getText().toString();
                start = tx_startday.getText().toString();
                finish = tx_finishday.getText().toString();

                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = simpleDate.format(mDate).toString();

                DiaryData diaryData = new DiaryData(title, place, start, finish, getTime);
                Log.e("diaryinfo1", diaryData.getTitle());
                Log.e("diaryinfo1", diaryData.getPlace());
                Log.e("diaryinfo1", diaryData.getStart());
                Log.e("diaryinfo1", diaryData.getFinish());
                Log.e("시간", diaryData.getTime());
                // 잘 들어 갔는지 확인차.


                String diary = preferences.getString(Login_Activity.id, "");

                    // 회원가입을 하고 데이터 저장을 한다는것은 spotinfo에 아무런 데이터도 없다는 말.
                    //회원가입 id를 키값으로 하는 데이터가 없을때

                // log id를 키값으로 갖는 데이터가 없으면 새로 json array 를 만들어라.
                if(diary.equals(""))
                {
                    String data = gson.toJson(diaryData);
                    // 자바 객체를 문자열로 만듬.
                    try {
                        JSONObject obj = new JSONObject(data);
                        Log.e("obj", obj.toString());
                        JSONArray array = new JSONArray();
                        array.put(obj);
                        Log.e("array", array.toString());
                        //json 객체에 java 객체를 문자열로 만든 인자를 넣고 그 json 객체를 json array에 넣는다.

                        String result = array.toString();

                        editor.putString(Login_Activity.id, result);
                        Log.e("result", result);
                        editor.commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    Log.e("diary",diary);
                    datalist = new ArrayList<>();
                    Log.e("datalist", datalist.toString());
                    Log.e("diary",diary);
                    datalist = gson.fromJson(diary, new TypeToken<ArrayList<DiaryData>>(){}.getType() );
                    Log.e("datalist", datalist.toString());

                   // datalist = new ArrayList<>();
                    datalist.add(diaryData);
                   // Log.e("datalist", datalist.toString());


                    editor.putString(Login_Activity.id, gson.toJson(datalist));

                    editor.commit();

                }

                startActivity(intent);
                finish();



            }
        });

    }

    //캘린더 다이얼로그 띄우기. 끝 날짜
    public void InitializeView()
    {
        tx_finishday = (TextView)findViewById(R.id.tx_finishday);
    }

    public void InitializeListener()
    {
        callbackMethod2 = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear , int dayOfMonth)
            {
                monthOfYear += 1;
                tx_finishday.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");


            }
        };
    }

    //캘린더 다이얼로그 띄우기. 끝 날짜
    public void InitializeView2()
    {
        tx_startday = (TextView)findViewById(R.id.tx_startday);
    }

    public void InitializeListener2()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear , int dayOfMonth)
            {
                monthOfYear += 1;
                tx_startday.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");

            }
        };
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2019, 5, 24);

        dialog.show();
    }


    public void OnClickHandler2(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod2, 2019, 5, 24);

        dialog.show();
    }

}