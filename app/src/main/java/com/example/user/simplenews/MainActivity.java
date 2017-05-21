package com.example.user.simplenews;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<News> list = new ArrayList<>();
    private ListView listView;
    private NewsAdapter newsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv);
        initListData();
    }

    private void initListData() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                final String FORECAST_BASE_URL =
                        "http://api.jisuapi.com/news/get?";
                final String CHANNEL_PARAM = "channel";
                final String NUM_PARAM = "num";
                final String APPKEY_PARAM = "appkey";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(CHANNEL_PARAM, "头条")
                        .appendQueryParameter(NUM_PARAM, "20")
                        .appendQueryParameter(APPKEY_PARAM, BuildConfig.OPEN_NEWS_API_KEY)
                        .build();

                HttpURLConnection conn = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(builtUri.toString());
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = conn.getInputStream();
                        StringBuffer buffer = new StringBuffer();
                        if (inputStream == null) {
                            return;
                        }

                        reader = new BufferedReader(new InputStreamReader(inputStream));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                            // But it does make debugging a *lot* easier if you print out the completed
                            // buffer for debugging.
                            buffer.append(line + "\n");
                        }

                        if (buffer.length() == 0) {
                            // Stream was empty.  No point in parsing.
                            return;
                        }

                        JSONObject object = new JSONObject(buffer.toString());
                        JSONArray array = object.getJSONObject("result").getJSONArray("list");

                        for (int i = 0; i < array.length(); i++) {
                            String title = array.getJSONObject(i).getString("title");
                            String content = array.getJSONObject(i).getString("content");
                            String des = Html.fromHtml(content).toString();
                            String imgUrl = array.getJSONObject(i).getString("pic");
                            list.add(new News(title, des, imgUrl));
                        }

                        newsAdapter = new NewsAdapter(getApplicationContext(), list);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listView.setAdapter(newsAdapter);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("MainActivity", "Error ", e);
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e("MainActivity", "Error closing stream", e);
                        }
                    }
                }
            }
        }.start();
    }

}
