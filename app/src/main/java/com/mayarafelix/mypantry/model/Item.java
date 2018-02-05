package com.mayarafelix.mypantry.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Item
{
    private String id;
    private String name;
    private String category;
    private String categoryId;
    private String imgUrl;
    private String imgDir;

    public Item(String id, String name, String category, String categoryId, String imgUrl, String imgDir)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.categoryId = categoryId;
        this.imgUrl = imgUrl;
        this.imgDir = imgDir;
    }

    public Item(JSONObject json)
    {
        try
        {
            this.id         = json.optString("_id");
            this.name       = json.optString("name");
            this.imgUrl     = json.optString("imgUrl");
            this.imgDir     = json.optString("imgDir");

            JSONObject categoryObj = json.optJSONArray("category").getJSONObject(0);

            this.category   = categoryObj.optString("name");
            this.categoryId = categoryObj.optString("_id");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgDir() {
        return imgDir;
    }

    public void setImgDir(String imgDir) {
        this.imgDir = imgDir;
    }
}
