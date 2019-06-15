package com.maximeesprit.geekpressmobile;

import org.json.JSONException;
import org.json.JSONObject;

public class Article {

    private int Id;

    public int getId(){
        return Id;
    }
    public void setId(int iId){
        Id = iId;
    }


    private String Title;

    public String getTitle(){
        return Title;
    }
    public void setTitle(String sTitle){
        Title = sTitle;
    }


    private String Author;

    public String getAuthor(){
        return Author;
    }
    public void setAuthor(String sAuthor){
        Author = sAuthor;
    }


    private String Date;

    public String getDate(){
        return Date;
    }
    public void setDate(String sDate){
        Date = sDate;
    }


    private String Content;

    public String getContent(){
        return Content;
    }
    public void setContent(String sContent){
        Content = sContent;
    }


    public Article(JSONObject jsonObj) throws JSONException {
        Id = jsonObj.getInt("id");

        JSONObject articleTitle = jsonObj.getJSONObject("title");
        Title = articleTitle.getString("rendered");

        JSONObject articleContent = jsonObj.getJSONObject("content");
        Content = articleContent.getString("rendered");
    }
}
