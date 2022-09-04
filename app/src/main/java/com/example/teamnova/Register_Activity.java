package com.example.teamnova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Register_Activity extends AppCompatActivity {

    private EditText et_id;
    private EditText et_pass;
    private EditText et_passck;
    private EditText et_name;
    private EditText et_age;
    private Button btn_register;
    private Button btn_validate;
    String shared = "UserInfo";
    static String Rid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_passck = findViewById(R.id.et_passck);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);

        SharedPreferences preferences = getSharedPreferences(shared, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new GsonBuilder().create();

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Rid = et_id.getText().toString();
                String pass = et_pass.getText().toString();
                String passck = et_passck.getText().toString();
                String name = et_name.getText().toString();
                String age = et_age.getText().toString();

                if(Rid.length() == 0)
                {
                    Toast.makeText(Register_Activity.this, "Email을 입력하세요", Toast.LENGTH_SHORT).show();
                    et_id.requestFocus();
                    return;
                }
                if(pass.length() == 0)
                {
                    Toast.makeText(Register_Activity.this, "비밀번호을 입력하세요", Toast.LENGTH_SHORT).show();
                    et_pass.requestFocus();
                    return;
                }

                if(passck.length()==0)
                {
                    Toast.makeText(Register_Activity.this, "비밀번호 확인을 입력하세요", Toast.LENGTH_SHORT).show();
                    et_passck.requestFocus();
                    return;
                }

                if(!pass.equals(passck))
                {
                    Toast.makeText(Register_Activity.this, "비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show();
                    et_pass.setText("");
                    et_passck.setText("");
                    et_pass.requestFocus();
                    return;
                }

                if(age.length() == 0){
                    Toast.makeText(Register_Activity.this, "나이를 입력하세요", Toast.LENGTH_SHORT).show();
                    et_age.requestFocus();
                    return;
                }

                if(name.length() == 0)
                {
                    Toast.makeText(Register_Activity.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                    et_name.requestFocus();
                    return;
                }

                if(Rid.length() !=0 && pass.equals(passck))
                {
                    int intage = Integer.parseInt(et_age.getText().toString());
                    UserInfo userInfo = new UserInfo(Rid, pass, name, intage);
                    String userJson = gson.toJson(userInfo);
                    //자바 객체를 문자열로 변환.

                    editor.putString(Rid, userJson);
                    // userJson 즉 회원가입한 유저의 정보를 sp에 저장해라.
                    // key 값은 유저의 id가 되어야 한다.
                    // 중복검사를 해서 id는 유일한 키값이 된다.

                    editor.commit();

                    Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
                    startActivity(intent);
                    Toast.makeText(Register_Activity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                    finish();
                    //회원가입이 완료되면 그때 액티비티 이동을 해라.

                }


            }
        });

        btn_validate = findViewById(R.id.btn_validate);
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String valid = et_id.getText().toString();

                String response = preferences.getString(valid, "");
                //입력한 아이디가 key값이 되고 그 key 값이 가지고 있는 데이터들이 없다면 회원가입 가능
                if(response.isEmpty())
                {
                    Toast.makeText(Register_Activity.this, "회원가입 가능", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Register_Activity.this, "이미 존재하는 아이디 입니다", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}