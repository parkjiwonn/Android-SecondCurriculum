package com.example.teamnova;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
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

import java.text.SimpleDateFormat;

public class EditTravelDiary_Activity extends AppCompatActivity {

    private TextView tx_editdate;
    private TextView tx_edittime;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private TimePickerDialog.OnTimeSetListener callbackMethod2;
    private EditText et_editspot;
    private EditText et_editcontent;
    private EditText et_editaddress;
    private ImageView edit_imageView;
    private Button btn_edit;

    String imagePath ="";
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat imageDate = new SimpleDateFormat("yyyyMMdd_HHmmss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_travel_diary);

        this.InitializeView();
        this.InitializeListener();

        this.InitializeView2();
        this.InitializeListener2();

        tx_editdate = findViewById(R.id.tx_editdate);
        tx_edittime = findViewById(R.id.tx_edittime);
        et_editspot = findViewById(R.id.et_editspot);
        et_editcontent = findViewById(R.id.et_editcontent);
        et_editaddress = findViewById(R.id.et_editaddress);
        edit_imageView = findViewById(R.id.edit_imageView);

        Intent intent = getIntent();
        String str = intent.getStringExtra("date");
        tx_editdate.setText(str);

        Intent intent1 = getIntent();
        String str1 = intent1.getStringExtra("time");
        tx_edittime.setText(str1);

        Intent intent2 = getIntent();
        String str2 = intent2.getStringExtra("spot");
        et_editspot.setText(str2);

        Intent intent3 = getIntent();
        String str3 = intent3.getStringExtra("content");
        et_editcontent.setText(str3);

        Intent intent4 = getIntent();
        String str4 = intent4.getStringExtra("address");
        et_editaddress.setText(str4);

        Intent intent5 = getIntent();
        String str5 = intent5.getStringExtra("image");
        Glide.with(this).load(str5).into(edit_imageView);

        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EditTravelDiary_Activity.this, TravelDiary_Activity.class);

                intent.putExtra("date",tx_editdate.getText().toString());
                intent.putExtra("time",tx_edittime.getText().toString());
                intent.putExtra("spot",et_editspot.getText().toString());
                intent.putExtra("content",et_editcontent.getText().toString());
                intent.putExtra("address",et_editaddress.getText().toString());
                intent.putExtra("image", imagePath);

                setResult(RESULT_OK, intent);


                finish();
            }
        });

        //갤러리에서 이미지 업로드하기 버튼.
        Button imageButton = findViewById(R.id.btn_editgallery);
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

        if(resultCode == Activity.RESULT_OK){
            if(requestCode ==0){
                imagePath = data.getDataString();
            }
        }

        if(imagePath.length()>0){
            Log.e("ImagePath", imagePath);
            Glide.with(this).load(imagePath).into(edit_imageView);

        }



//        if(requestCode == 0){
//            if(resultCode == RESULT_OK){
////                Glide.with(getApplicationContext()).load(data.getData()).override(500,500).into(imageView);
//            }
//        }


    }

    // 날짜 다이얼로그 구현

    public void InitializeView()
    {
        tx_editdate = (TextView)findViewById(R.id.tx_editdate);
    }

    public void InitializeListener()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                monthOfYear += 1;
                tx_editdate.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");
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
        tx_edittime = (TextView)findViewById(R.id.tx_edittime);
    }

    public void InitializeListener2()
    {
        callbackMethod2 = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                tx_edittime.setText(hourOfDay + "시" + minute + "분");
            }
        };
    }
    public void OnClickHandler2(View view)
    {
        TimePickerDialog dialog = new TimePickerDialog(this, callbackMethod2, 8, 10, true);

        dialog.show();
    }


}