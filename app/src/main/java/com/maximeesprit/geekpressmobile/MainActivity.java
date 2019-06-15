package com.maximeesprit.geekpressmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.wearable.activity.WearableActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends WearableActivity {

    private ListView mListview;
    //private List<Article> mArticles = new ArrayList<Article>();
    //private ArrayAdapter<String> mAdapter;
    private ArticleAdapter mAdapter;
    private SwipeRefreshLayout mRefresher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListview = (ListView) findViewById(R.id.listviewArticles);
        mRefresher = (SwipeRefreshLayout) findViewById(R.id.refresher);

        //mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mAdapter = new ArticleAdapter(this, 0);

        mListview.setAdapter(mAdapter);

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Article articleClicked = mArticles.get(position);
                Article articleClicked = mAdapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("activityID", articleClicked.getId());
                startActivity(intent);
            }
        });

        mRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetArticlesAsyncTask(MainActivity.this).execute("http://www.geekpress.fr/wp-json/wp/v2/posts");
            }
        });

        mRefresher.post(new Runnable() {
            @Override
            public void run() {
                new GetArticlesAsyncTask(MainActivity.this).execute("http://www.geekpress.fr/wp-json/wp/v2/posts");
            }
        });

        // Enables Always-on
        setAmbientEnabled();
    }

    public void UpdateUI(List<Article> lsArticles){
        /*mArticles.clear();
        mArticles.addAll(lsArticles);

        for (Article a:mArticles) {
            mAdapter.add(a.getTitle());
        }*/
        mAdapter.addAll(lsArticles);
    }

    private class GetArticlesAsyncTask extends AsyncTask<String, Void, List<Article>> {
        private MainActivity Parent;
        public GetArticlesAsyncTask(MainActivity parent){
            Parent = parent;
        }

        protected void onPreEecute(){
            Parent.mRefresher.setRefreshing(true);
        }

        protected List<Article> doInBackground(String... sUrl){
            List<Article> resultArticles = new ArrayList<Article>();

            String sJson = Utils.readJSONFeed(sUrl[0]);

            try {
                JSONArray jsonArr = new JSONArray(sJson);

                for (int i = 0; i < jsonArr.length(); i++) {

                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                    resultArticles.add(new Article(jsonObj));
                }
            }
            catch (JSONException exc){

            }

            return resultArticles;
        }

        protected void onPostExecute(List<Article> articlesToShow){
            Parent.UpdateUI(articlesToShow);
            Parent.mRefresher.setRefreshing(false);
        }
    }
}