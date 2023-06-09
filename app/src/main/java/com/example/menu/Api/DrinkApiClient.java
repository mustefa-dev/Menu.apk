package com.example.menu.Api;

import com.example.menu.models.DrinkDto;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrinkApiClient {
    private static final String BASE_URL = "http://192.168.75.101:5043/api/";

    public static List<DrinkDto> getDrinks() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + "drinks")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return parseDrinkResponse(responseBody);
            } else {
                throw new IOException("Unexpected response code: " + response.code());
            }
        }
    }

    private static List<DrinkDto> parseDrinkResponse(String responseBody) throws JSONException {
        List<DrinkDto> drinks = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(responseBody);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            // Parse other drink properties as needed

            DrinkDto drink = new DrinkDto(id, name);
            drinks.add(drink);
        }

        return drinks;
    }
}
