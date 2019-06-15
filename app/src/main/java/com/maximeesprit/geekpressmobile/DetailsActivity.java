package com.maximeesprit.geekpressmobile;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends WearableActivity {

    private TextView mTextView;
    private TextView mTextViewTitle;
    private TextView mTextViewContent;
    private TextView mTextViewAuthorDate;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mTextView = (TextView) findViewById(R.id.text);
        mTextViewAuthorDate = (TextView) findViewById(R.id.textviewArticleAuthorDate);
        mTextViewTitle = (TextView) findViewById(R.id.textviewArticleTitle);
        mTextViewContent = (TextView) findViewById(R.id.textviewArticleContent);

        int iIdArticle = getIntent().getIntExtra("activityID", 0);

        //new GetArticleAsyncTask(this).execute("http://www.geekpress.fr/wp-json/wp/v2/posts/7756");
        new GetArticleAsyncTask(this).execute("http://www.geekpress.fr/wp-json/wp/v2/posts/" + iIdArticle);

    }


    private class GetArticleAsyncTask extends AsyncTask<String, Void, Article> {
        private DetailsActivity Parent;
        public GetArticleAsyncTask(DetailsActivity parent){
            Parent = parent;
        }

        protected void onPreEecute(){
            Parent.mProgressBar.setVisibility(View.VISIBLE);
        }

        protected Article doInBackground(String... sUrl){
            Article resultArticle = null;

            String sJson = Utils.readJSONFeed(sUrl[0]);

            try {
                JSONObject jsonObj = new JSONObject(sJson);

                resultArticle = new Article(jsonObj);
            }
            catch (JSONException exc){

            }

            return resultArticle;
        }

        protected void onPostExecute(Article articleToShow){
            Parent.mTextViewTitle.setText(Html.fromHtml(articleToShow.getTitle()));
            Parent.mTextViewContent.setText(Html.fromHtml(articleToShow.getContent()));
            Parent.mProgressBar.setVisibility(View.GONE);
        }
    }

}
