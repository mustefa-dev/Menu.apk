package com.example.menu.Api;

import com.example.menu.models.FoodDto;

import org.jetbrains.annotations.NotNull;
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

public class FoodApiClient {
    private static final String BASE_URL = "http://192.168.31.93:5043/";

    public void getFoodBySectionName(String sectionName, FoodCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + "api/foods/section/" + sectionName)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    List<FoodDto> foodList = parseFoodFromResponse(responseBody);
                    callback.onSuccess(foodList);
                } else {
                    callback.onFailure("Failed to fetch food. Please try again.");
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                callback.onFailure("Failed to fetch food. Please check your internet connection and try again.");
            }
        });
    }

    private List<FoodDto> parseFoodFromResponse(String responseBody) {
        try {
            JSONArray jsonArray = new JSONArray(responseBody);
            List<FoodDto> foodList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonFood = jsonArray.getJSONObject(i);
                int id = jsonFood.getInt("id");
                String name = jsonFood.getString("name");
                String sectionName = jsonFood.getString("sectionName");
                double price = jsonFood.getDouble("price");
                String description = jsonFood.getString("description");
                String photoUrl = jsonFood.getString("photo");

                String fullPhotoUrl = BASE_URL + photoUrl;

                FoodDto food = new FoodDto(id, name, sectionName, price, description, fullPhotoUrl);
                foodList.add(food);
            }
            return foodList;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public interface FoodCallback {
        void onSuccess(List<FoodDto> foodList);

        void onFailure(String errorMessage);
    }
}
