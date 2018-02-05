package com.mayarafelix.mypantry.model;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Category
{
    private String id;
    private String name;

    public Category(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Category(JSONObject json)
    {
        this.id = json.optString("_id");
        this.name = json.optString("name");
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
}
