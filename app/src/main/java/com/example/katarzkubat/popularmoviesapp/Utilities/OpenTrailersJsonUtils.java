package com.example.katarzkubat.popularmoviesapp.Utilities;
import android.util.Log;

import com.example.katarzkubat.popularmoviesapp.Model.Trailers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OpenTrailersJsonUtils {

    private static final String RESULTS = "results";
    private static final String TRAILER_PATH = "key";
    private static final String TRAILER_TITLE = "name";
    private static final String TRAILER_URI = "site";

    public static ArrayList<Trailers> getTrailersFromJson(String jsonTrailers) {

        ArrayList<Trailers> trailerArrayList = new ArrayList<>();
        String trailerTitle = "";
        String trailerPath = "";
        String trailerSite = "";

        try {
            JSONObject jsonObject = new JSONObject(jsonTrailers);

            if (jsonObject.has(RESULTS)) {

                JSONArray results = jsonObject.getJSONArray(RESULTS);
                Log.d("TRAILERSJSONOBJECT", String.valueOf(results));

                for (int i = 0; i < results.length(); i++) {

                    JSONObject item = results.getJSONObject(i);

                    if (item.has(TRAILER_TITLE)) {
                        trailerTitle = item.optString(TRAILER_TITLE);
                    }

                    if (item.has(TRAILER_PATH)) {
                        trailerPath = item.optString(TRAILER_PATH);
                    }

                    if (item.has(TRAILER_URI)) {
                        trailerSite = item.optString(TRAILER_URI);
                   }

                Trailers trailer = new Trailers(trailerSite, trailerTitle, trailerPath);
                trailerArrayList.add(trailer);
            }
        }

    } catch (JSONException e) {
            e.printStackTrace();
        }

    return trailerArrayList;

    }
}
