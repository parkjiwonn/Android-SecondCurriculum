package com.example.teamnova;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class EditDiary_Activity extends AppCompatActivity {

    private EditText et_edittitle, et_editwhere;
    private TextView tx_editstart, tx_editfinish;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private DatePickerDialog.OnDateSetListener callbackMethod2;
    private Button btn_editadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary);

        et_edittitle = findViewById(R.id.et_edittitle);
        et_editwhere = findViewById(R.id.et_editwhere);
        tx_editstart = findViewById(R.id.tx_editstart);
        tx_editfinish = findViewById(R.id.tx_editfinish);
        btn_editadd = findViewById(R.id.btn_editadd);

        this.InitializeView2();
        this.InitializeListener2();

        this.InitializeView();
        this.InitializeListener();

        Intent intent = getIntent();
        String str = intent.getStringExtra("title");
        et_edittitle.setText(str);


        Intent intent1 = getIntent();
        String str2 = intent1.getStringExtra("where");
        et_editwhere.setText(str2);

        Intent intent2 = getIntent();
        String str3 =intent2.getStringExtra("start");
        tx_editstart.setText(str3);


        Intent intent3 = getIntent();
        String str4 = intent3. getStringExtra("finish");
        tx_editfinish.setText(str4);


        btn_editadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EditDiary_Activity.this, Diary_Activity.class);

                intent.putExtra("title",et_edittitle.getText().toString());
                intent.putExtra("where",et_editwhere.getText().toString());
                intent.putExtra("start",tx_editstart.getText().toString());
                intent.putExtra("finish",tx_editfinish.getText().toString());

                setResult(RESULT_OK, intent);


                finish();

            }
        });
    }

    //캘린더 다이얼로그 띄우기. 끝 날짜
    public void InitializeView()
    {
        tx_editfinish = (TextView)findViewById(R.id.tx_editfinish);
    }

    public void InitializeListener()
    {
        callbackMethod2 = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear , int dayOfMonth)
            {
                monthOfYear += 1;
                tx_editfinish.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");


            }
        };
    }

    //캘린더 다이얼로그 띄우기. 끝 날짜
    public void InitializeView2()
    {
        tx_editstart = (TextView)findViewById(R.id.tx_editstart);
    }

    public void InitializeListener2()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear , int dayOfMonth)
            {
                monthOfYear += 1;
                tx_editstart.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");

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