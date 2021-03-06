package com.example.katarzkubat.popularmoviesapp.Utilities;

        import com.example.katarzkubat.popularmoviesapp.Model.Reviews;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;


public class OpenReviewsJsonUtils {

    private static final String RESULTS = "results";
    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";

    public static ArrayList<Reviews> getReviewFromJson(String jsonReviews) {

        ArrayList<Reviews> reviewsArrayList = new ArrayList<>();

        String reviewAuthor = "";
        String reviewContent = "";

        try {
            JSONObject jsonObject = new JSONObject(jsonReviews);

            if (jsonObject.has(RESULTS)) {

                JSONArray results = jsonObject.getJSONArray(RESULTS);
                for (int i = 0; i < results.length(); i++) {

                    JSONObject item = results.getJSONObject(i);

                    if (item.has(REVIEW_AUTHOR)) {
                        reviewAuthor = item.optString(REVIEW_AUTHOR);
                    }

                    if (item.has(REVIEW_CONTENT)) {
                        reviewContent = item.optString(REVIEW_CONTENT);
                    }

                    Reviews review = new Reviews(reviewAuthor, reviewContent);
                    reviewsArrayList.add(review);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewsArrayList;
    }
}

