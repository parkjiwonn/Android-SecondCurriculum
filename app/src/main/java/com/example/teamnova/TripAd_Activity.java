package com.example.teamnova;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class TripAd_Activity extends AppCompatActivity {


    StringBuilder searchResult;
    BufferedReader br;
    String[] title, link, description, bloggername, postdate;
    SNSViewAdaptor mMyAdapter;
    EditText et_search;
    Button btn_search;

    private ListView mListView;
    int itemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_ad);



        btn_search = (Button)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et_search = (EditText)findViewById(R.id.et_search);
                String search = et_search.getText().toString();
                searchNaver(search);

                mListView = (ListView) findViewById(R.id.listViewSNS);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String blog_url = mMyAdapter.getItem(position).getBloglink();
                        Intent intent = new Intent(getApplicationContext(), InternetWebView.class);
                        intent.putExtra("blog_url", blog_url);
                        startActivity(intent);

                    }
                });
            }
        });
    }


    public void searchNaver(final String searchObject) {
        final String clientId = "o0XH74piGOkmhvNnxUl9";//?????????????????? ??????????????? ????????????";
        final String clientSecret = "yDsVWNJMgw";//?????????????????? ??????????????? ????????????";
        final int display = 10; // ???????????? ??????????????? ???

        // ???????????? ????????? Thread ?????? ??????
        new Thread() {

            @Override
            public void run() {
                try {
                    String text = URLEncoder.encode(searchObject, "UTF-8");
                    String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text + "&display=" + display + "&"; // json ??????
                    Log.d(TAG, "?????? ??????");

                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("X-Naver-Client-Id", clientId);
                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                    con.connect();

                    int responseCode = con.getResponseCode();


                    // responseCode ??????????????? ??????.
                    // ???????????????. -
                    Log.d("responsecode", String.valueOf(responseCode));

                    if(responseCode==200) { // ?????? ??????
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        Log.d("br", br.toString());

                    } else {  // ?????? ??????
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }

                    searchResult = new StringBuilder();
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        searchResult.append(inputLine + "\n");

                    }
                    br.close();
                    con.disconnect();

                    String data = searchResult.toString();

                    String[] array = data.split("\"");
                    title = new String[display];
                    link = new String[display];
                    description = new String[display];
                    bloggername = new String[display];
                    postdate = new String[display];

                    itemCount = 0;
                    for (int i = 0; i < array.length; i++) {
                        if (array[i].equals("title"))
                            title[itemCount] = array[i + 2];
                        if (array[i].equals("link"))
                            link[itemCount] = array[i + 2];
                        if (array[i].equals("description"))
                            description[itemCount] = array[i + 2];
                        if (array[i].equals("bloggername"))
                            bloggername[itemCount] = array[i + 2];
                        if (array[i].equals("postdate")) {
                            postdate[itemCount] = array[i + 2];
                            itemCount++;
                        }
                    }

                    // title ??? ???????????? ?????? ???.
                    Log.d(TAG, "title????????????: " + title[0] + title[1] + title[2]);

                    // ????????? ??????????????? ????????????, UiThread?????? listView??? ???????????? ??????
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listViewDataAdd();
                        }
                    });
                } catch (Exception e) {
                    //?????? ?????? ?????? ?????? ??????
                    Log.d(TAG, "error : " + e);
                }

            }
        }.start();

    }


    public void listViewDataAdd() {
        mMyAdapter = new SNSViewAdaptor();
        for (int i = 0; i < itemCount; i++) {

            mMyAdapter.addItem(Html.fromHtml(title[i]).toString(),
                    Html.fromHtml(description[i]).toString(),
                    Html.fromHtml(bloggername[i]).toString(),
                    Html.fromHtml(postdate[i]).toString(),
                    Html.fromHtml(link[i]).toString());

        }

        // set adapter on listView
        mListView.setAdapter(mMyAdapter);

    }

}