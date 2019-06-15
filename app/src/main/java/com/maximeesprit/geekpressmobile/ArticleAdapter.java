package com.maximeesprit.geekpressmobile;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;

public class ArticleAdapter extends ArrayAdapter<Article> {

    private ArrayList<Article> mArticles = new ArrayList<Article>();

    public ArticleAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public Article getItem(int position) {
        return mArticles.get(position);
    }

    @Override
    public int getCount() {
        return mArticles.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article articleToDisplay = getItem(position);

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.article_view, parent, false);

        TextView tvArticleTitle = (TextView) v.findViewById(R.id.articleTitle);

        tvArticleTitle.setText(Html.fromHtml(articleToDisplay.getTitle()));

        return v;
    }

    @Override
    public void addAll(Collection<? extends Article> collection) {
        mArticles.clear();
        mArticles.addAll(collection);

        notifyDataSetChanged();
    }
}
