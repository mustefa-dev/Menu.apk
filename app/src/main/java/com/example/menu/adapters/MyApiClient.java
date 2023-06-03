package com.example.menu.adapters;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.menu.models.Item;

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

public class MyApiClient {

    private static final String TAG = "MyApiClient";
    private static final String API_ENDPOINT = "http://192.168.215.101:5043/api/items";

    private List<Item> cachedItems; // Cache for storing retrieved items

    public interface ItemCallback {
        void onSuccess(List<Item> items);

        void onFailure(Throwable throwable);
    }

    public void getItems(final ItemCallback callback) {
        if (cachedItems != null) {
            // If items are already cached, return them immediately
            callback.onSuccess(cachedItems);
        } else {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(API_ENDPOINT)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()) {
                        try {
                            String responseBody = response.body().string();
                            Log.d(TAG, "API response: " + responseBody);
                            List<Item> items = parseItemsFromResponse(responseBody);
                            cachedItems = items; // Cache the retrieved items
                            handleSuccess(callback, items);
                        } catch (IOException e) {
                            handleFailure(callback, e);
                        }
                    } else {
                        handleFailure(callback, new IOException("Unexpected response code: " + response.code()));
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "API request failed", e);
                    handleFailure(callback, e);
                }
            });
        }
    }

    public List<Item> getCachedItems() {
        return cachedItems;
    }

    private void handleSuccess(final ItemCallback callback, final List<Item> items) {
        runOnUiThread(() -> callback.onSuccess(items));
    }

    private void handleFailure(final ItemCallback callback, final Throwable throwable) {
        runOnUiThread(() -> callback.onFailure(throwable));
    }

    private void runOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    private List<Item> parseItemsFromResponse(String responseBody) {
        try {
            JSONArray jsonArray = new JSONArray(responseBody);
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                double price = jsonObject.getDouble("price");
                String photoUrl = jsonObject.getString("photo");

                String fullPhotoUrl = "http://192.168.215.101:5043/" + photoUrl;
                Item item = new Item(id, name, price, fullPhotoUrl);
                items.add(item);
            }
            return items;
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON response", e);
            return null;
        }
    }
}
