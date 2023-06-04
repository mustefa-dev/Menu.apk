package com.example.menu.Api;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.menu.models.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CategoryApiClient {
    private static final String TAG = "CategoryApiClient";
    private static final String BASE_URL = "http://192.168.215.101:5043/api/";

    private List<Category> cachedCategories;

    public interface CategoryApiCallback<T> {
        void onSuccess(T response);

        void onFailure(Throwable throwable);
    }

    public void getCategories(final CategoryApiCallback<List<Category>> callback) {
        if (cachedCategories != null) {
            // If categories are already cached, return them immediately
            callback.onSuccess(cachedCategories);
        } else {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(BASE_URL + "categories")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()) {
                        try {
                            String responseBody = response.body().string();
                            Log.d(TAG, "Categories API response: " + responseBody);
                            List<Category> categories = parseCategoriesFromResponse(responseBody);
                            cachedCategories = categories; // Cache the retrieved categories
                            handleSuccess(callback, categories);
                        } catch (IOException e) {
                            handleFailure(callback, e);
                        }
                    } else {
                        handleFailure(callback, new IOException("Unexpected response code: " + response.code()));
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Categories API request failed", e);
                    handleFailure(callback, e);
                }
            });
        }
    }

    private void handleSuccess(final CategoryApiCallback<List<Category>> callback, final List<Category> categories) {
        runOnUiThread(() -> callback.onSuccess(categories));
    }

    private void handleFailure(final CategoryApiCallback<List<Category>> callback, final Throwable throwable) {
        runOnUiThread(() -> callback.onFailure(throwable));
    }

    private void runOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    private List<Category> parseCategoriesFromResponse(String responseBody) {
        try {
            JSONArray jsonArray = new JSONArray(responseBody);
            List<Category> categories = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");

                Category category = new Category(id, name);
                categories.add(category);
            }
            return categories;
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON response", e);
            return null;
        }
    }
}
