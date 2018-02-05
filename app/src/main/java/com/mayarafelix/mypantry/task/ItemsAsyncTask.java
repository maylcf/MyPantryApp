package com.mayarafelix.mypantry.task;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.mayarafelix.mypantry.fragment.CategoryFragment;
import com.mayarafelix.mypantry.fragment.ItemFragment;

import org.json.JSONArray;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mayca on 2018-02-05.
 */

public class ItemsAsyncTask extends AsyncTask<String, Void, JSONArray>
{
    private final WeakReference<ItemFragment> fragmentReference;

    public ItemsAsyncTask(@NonNull ItemFragment fragment)
    {
        fragmentReference = new WeakReference<ItemFragment>(fragment);
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
        ItemFragment fragment = fragmentReference.get();
        //activity.progressBar.setVisibility(View.GONE);
        fragment.populateItems(result);
    }

}
