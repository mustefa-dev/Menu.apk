package com.example.menu.Api;

import com.example.menu.models.FoodSectionDto;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodSectionApiClient {
    private static final String BASE_URL = "http://192.168.31.93:5043/api/";

    public static List<FoodSectionDto> getFoodSections() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + "FoodSection")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return parseFoodSectionResponse(responseBody);
            } else {
                throw new IOException("Unexpected response code: " + response.code());
            }
        }
    }

    private static List<FoodSectionDto> parseFoodSectionResponse(String responseBody) throws JSONException {
        List<FoodSectionDto> foodSections = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(responseBody);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");

            FoodSectionDto foodSection = new FoodSectionDto(id, name);
            foodSections.add(foodSection);
        }

        return foodSections;
    }
}
