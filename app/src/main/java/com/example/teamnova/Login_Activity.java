package com.example.teamnova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;


public class Login_Activity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_register;
    private EditText log_id, log_pass;
    static  String id;
//    private String personEmail;
//    private Button signBt;
//    private Button logoutBt;
//    // Google Sign In API와 호출할 구글 로그인 클라이언트
//    GoogleSignInClient mGoogleSignInClient;
//
//    private final int RC_SIGN_IN = 123;
//    private static final String TAG = "Login_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log_id=findViewById(R.id.log_id);
        log_pass=findViewById(R.id.log_pass);

//        signBt = findViewById(R.id.btn_goologin);
//        signBt.setOnClickListener(this);
//        // oncreate 매서드에서 사용자가 클릭했을 때 로그인 하도록 버큰의 onclicklisterer를 등록한다.
////        logoutBt = findViewById(R.id.logoutBt);
////        logoutBt.setOnClickListener(this);
//
//        // 앱에 필요한 사용자 데이터를 요청하도록 로그인 옵션을 설정한다.
//        // DEFAULT_SIGN_IN parameter는 유저의 ID와 기본적인 프로필 정보를 요청하는데 사용된다.
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail() // email addresses도 요청함
//                .build();
//
//        // 위에서 만든 GoogleSignInOptions을 사용해 GoogleSignInClient 객체를 만듬
//        mGoogleSignInClient = GoogleSignIn.getClient(Login_Activity.this, gso);
//
//        // 기존에 로그인 했던 계정을 확인한다.
//        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(Login_Activity.this);
//
//        // 로그인 되있는 경우 (토큰으로 로그인 처리)
//        if (gsa != null && gsa.getId() != null) {
//
//            System.out.println("이미 로그인한 계정입니다.");
//            // null을 반환한다면 사용자는 아직 google을 사용하여 앱에 로그인하지 않은 것이므로
//            // google 로그인 버튼을 표시하도록 UI를 업데이트 해야 한다.
//        }


        String shared = "UserInfo";
        SharedPreferences preferences = getSharedPreferences(shared, MODE_PRIVATE);


        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, Start_Activity.class);

                //로그인을 할때 유저가 입력한 아이디와 비번이 유저데이터에 속해있는지 확인하고
                //로그인 절차가 진행되어야 한다.
                id = log_id.getText().toString();
                String pass = log_pass.getText().toString();
                // 애초애 아이디는 하나만 저장이 가능하다 같은 아이디에 여러 비밀번호 저장이 불가능함.


                String response = preferences.getString(id,"");
                //입력한 id 를 key 값으로 가진 데이터를 담아봐라. 없으면 공백을 반환해라

                if ( response.isEmpty() )
                {
                    Toast.makeText(Login_Activity.this, "존재하지 않은 회원 정보 입니다.", Toast.LENGTH_SHORT).show();
                    Log.e("login22",response);
                }
                else
                {
                    try {
                        Log.e("유저 정보들", response);
                        JSONObject infoJson = new JSONObject(response);
                        //JSONObject 객체 생성하고 문자열 response를 인자로 받는다.
                        // == json string을 jsonobject로 변환함.

                        Log.e("login22","login22");

                        String curr_pw = infoJson.get("pass").toString();
                        // curr_pw 안에 json 객체에 있는 Upass라는 키의 값을 가져온다.
                        String curr_id = infoJson.get("id").toString();
                        // curr_id 안에 json 객체에 있는 Uemail라는 키의 값을 가져온다.

                        if(id.equals(curr_id) && pass.equals(curr_pw))
                        {
                            Toast.makeText(Login_Activity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            startActivity(intent); //액티비티 이동해주는 구문
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Login_Activity.this, "맞지 않는 아이디, 비밀번호 입니다.", Toast.LENGTH_SHORT).show();
                            log_id.setText("");
                            log_pass.setText("");
                            log_id.requestFocus();
                            return;
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //비번이 틀릴 경우도 생각해줘야 함. userinfo에 pass가 틀린 경우 고려.
                    //키값=아이디가 가지고 있는 데이터에 키값이 같긴한데 저장된 pass랑 입력된 pass가 같짚 않을때.

                }






            }
        });

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(intent);
                finish();
            }
        });


    }

//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
//            //GoogleSignInAccount
//            //GoogleSignInAccout 객체에는 사용자의 이름과 같은 로그인한 사용자에 대한 정보가 포함된다.
//
//            if (acct != null) {
//                String personName = acct.getDisplayName();
//                String personGivenName = acct.getGivenName();
//                String personFamilyName = acct.getFamilyName();
//                personEmail = acct.getEmail();
//                String personId = acct.getId();
//                Uri personPhoto = acct.getPhotoUrl();
//
//                //personEmail 이 사용자의 이메일임.
//
//                Log.d(TAG, "handleSignInResult:personName "+personName);
//                Log.d(TAG, "handleSignInResult:personGivenName "+personGivenName);
//                Log.d(TAG, "handleSignInResult:personEmail "+personEmail);
//                Log.d(TAG, "handleSignInResult:personId "+personId);
//                Log.d(TAG, "handleSignInResult:personFamilyName "+personFamilyName);
//                Log.d(TAG, "handleSignInResult:personPhoto "+personPhoto);
//            }
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
//
//        }
//    }

//    // onclick 매서드에서 getSignInintent 매서드로 로그인 인텐트를 만들어 로그인 버튼 탭을 처리한다.
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_goologin:
//                signIn();
//                Intent intent = new Intent(Login_Activity.this, Start_Activity.class);
//                intent.putExtra("email", personEmail);
//                startActivity(intent);
//                finish();
//
//                break;
////            case R.id.logoutBt:
////                mGoogleSignInClient.signOut()
////                        .addOnCompleteListener(this, task -> {
////                            Log.d(TAG, "onClick:logout success ");
////                            mGoogleSignInClient.revokeAccess()
////                                    .addOnCompleteListener(this, task1 -> Log.d(TAG, "onClick:revokeAccess success "));
////
////                            Log.d(TAG, personEmail);
////
////                        });
////                break;
//
//        }
//    }
//
//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//        // 인텐트를 시작하면 사용자게에 로그인할 google 계정을 선택하라는 메세지가 표시된다.
//        // profile, email, openid 이 외의 범위를 요청했다면 요청된 리소스에 대한 액세스 권한을 부여하라는 메세지도 표시된다.
//        // getsignInIntent 메서드로 로그인 인텐트를 만들어 로그인 버튼 탭을 처리한다.
//        // startActivityforresult 로 인텐트를 처리한다.
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            // 사용자가 로그인 하면 활동의 onActivityResult 메서드에서 사용자의 GooglesignInaAccount 객체를 가져올수 있다.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }
}