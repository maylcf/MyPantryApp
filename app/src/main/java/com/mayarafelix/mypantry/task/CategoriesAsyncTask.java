package com.mayarafelix.mypantry.task;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.mayarafelix.mypantry.activity.MainActivity;
import com.mayarafelix.mypantry.fragment.CategoryFragment;
import com.mayarafelix.mypantry.model.Category;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mayca on 2018-02-05.
 */

public class CategoriesAsyncTask extends AsyncTask<String, Void, JSONArray>
{
    private final WeakReference<CategoryFragment> activityReference;

    public CategoriesAsyncTask(@NonNull CategoryFragment categoryFragment)
    {
        activityReference = new WeakReference<CategoryFragment>(categoryFragment);
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
       // CategoryFragment fragment = activityReference.get();
        //appActivity.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected JSONArray doInBackground(String... urls)
    {
        JSONArray result = null;

        String urlString = urls != null && urls.length > 0 ? urls[0] : "";

        if (urlString == null || urlString.isEmpty())
        {
            return null;
        }

        try
        {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(2000); // 2 seconds
            connection.setConnectTimeout(3000); // 3 seconds
            connection.setRequestMethod("GET");
            connection.connect();

            int statusCode = connection.getResponseCode();

            if (statusCode == 200)
            {
                // Get information
                InputStream inputStream = connection.getInputStream();
                String jsonString = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();

                // disconnect
                inputStream.close();
                connection.disconnect();

                // Convert info to json
                result = new JSONArray(jsonString);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    protected void onPostExecute(JSONArray result)
    {
        CategoryFragment fragment = activityReference.get();
        //activity.progressBar.setVisibility(View.GONE);
        fragment.populateCategories(result);
    }

}
