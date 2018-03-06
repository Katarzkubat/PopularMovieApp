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

        ArrayList<Trailers> trailerArrayList = new ArrayList<Trailers>();
        String trailerTitle = "";
        String trailerPath = "";
        String trailerSite = "";

        try {
            JSONObject jsonObject = new JSONObject(jsonTrailers);

            if (jsonObject.has(RESULTS)) {

                JSONArray results = jsonObject.getJSONArray(RESULTS);
                Log.d("TRAILERSJSONOBJECT", String.valueOf(results));

                    for (int i = 0; i < results.length(); i++) {

                        JSONObject trailer = results.getJSONObject(i);

                        if (trailer.has(TRAILER_TITLE)) {
                            trailerTitle = trailer.optString(TRAILER_TITLE);
                        }

                        if (trailer.has(TRAILER_PATH)) {
                            trailerPath = trailer.optString(TRAILER_PATH);
                        }

                        if (trailer.has(TRAILER_URI)) {
                            trailerSite = trailer.optString(TRAILER_URI);
                        }
                    }

                    Trailers trailer = new Trailers(trailerTitle, trailerSite,trailerPath);
                    trailerArrayList.add(trailer);
               }

    } catch (JSONException e) {
            e.printStackTrace();
        }

    return trailerArrayList;

    }
}
