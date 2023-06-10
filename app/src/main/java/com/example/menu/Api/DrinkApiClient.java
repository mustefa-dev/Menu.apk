package com.example.menu.Api;

import com.example.menu.models.DrinkDto;

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

public class DrinkApiClient {
    private static final String BASE_URL = "http://192.168.5.101:5043/";

    public void getDrinksBySectionName(String sectionName, DrinkCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + "api/drinks/section/" + sectionName)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    List<DrinkDto> drinks = parseDrinksFromResponse(responseBody);
                    callback.onSuccess(drinks);
                } else {
                    callback.onFailure("Failed to fetch drinks. Please try again.");
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                callback.onFailure("Failed to fetch drinks. Please check your internet connection and try again.");
            }
        });
    }

    private List<DrinkDto> parseDrinksFromResponse(String responseBody) {
        try {
            JSONArray jsonArray = new JSONArray(responseBody);
            List<DrinkDto> drinks = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonDrink = jsonArray.getJSONObject(i);
                int id = jsonDrink.getInt("id");
                String name = jsonDrink.getString("name");
                String sectionName = jsonDrink.getString("sectionName");
                int sectionId = jsonDrink.getInt("sectionId");
                double price = jsonDrink.getDouble("price");
                String description = jsonDrink.getString("description");
                String photoUrl = jsonDrink.getString("photo");

                String fullPhotoUrl = BASE_URL + photoUrl;

                DrinkDto drink = new DrinkDto(id, name, sectionName, sectionId, price, description, fullPhotoUrl);
                drinks.add(drink);
            }
            return drinks;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public interface DrinkCallback {
        void onSuccess(List<DrinkDto> drinks);

        void onFailure(String errorMessage);
    }
}
