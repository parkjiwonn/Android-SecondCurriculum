package com.example.teamnova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DiaryView_Activity extends AppCompatActivity {

    private TextView view_date;
    private TextView view_time;
    private TextView view_spot;
    private TextView view_content;
    private TextView view_address;
    private ImageView view_image;
    private Button category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_view);

        view_date = findViewById(R.id.view_date);
        view_time = findViewById(R.id.view_time);
        view_spot = findViewById(R.id.view_spot);
        view_content = findViewById(R.id.view_content);
        view_address = findViewById(R.id.view_address);
        view_image = findViewById(R.id.view_image);

        Intent intent = getIntent();
        String str = intent.getStringExtra("date");
        view_date.setText(str);

        Intent intent1 = getIntent();
        String str1 = intent1.getStringExtra("time");
        view_time.setText(str1);

        Intent intent2 = getIntent();
        String str2 = intent2.getStringExtra("spot");
        view_spot.setText(str2);

        Intent intent3 = getIntent();
        String str3 = intent3.getStringExtra("content");
        view_content.setText(str3);

        Intent intent4 = getIntent();
        String str4 = intent4.getStringExtra("address");
        view_address.setText(str4);

        Intent intent5 = getIntent();
        String str5 = intent5.getStringExtra("image");
        Glide.with(this).load(str5).into(view_image);

        category = findViewById(R.id.category);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaryView_Activity.this, TravelDiary_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}