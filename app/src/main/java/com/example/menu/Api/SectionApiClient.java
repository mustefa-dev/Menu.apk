package com.example.menu.Api;

import com.example.menu.models.SectionDto;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SectionApiClient {
    private static final String BASE_URL = "http://192.168.28.101:5043/api/";

    private static OkHttpClient client;

    public SectionApiClient() {
        client = new OkHttpClient();
    }

    public static void getSections(SectionCallback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "Section")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    List<SectionDto> sections;
                    try {
                        sections = parseSectionResponse(responseBody);
                        callback.onSuccess(sections);
                    } catch (JSONException e) {
                        callback.onFailure("Error parsing section response");
                    }
                } else {
                    callback.onFailure("Unexpected response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("Request failed: " + e.getMessage());
            }
        });
    }

    private static List<SectionDto> parseSectionResponse(String responseBody) throws JSONException {
        List<SectionDto> sections = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(responseBody);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");

            SectionDto section = new SectionDto(id, name);
            sections.add(section);
        }

        return sections;
    }

    public interface SectionCallback {
        void onSuccess(List<SectionDto> sections);

        void onFailure(String errorMessage);
    }
}
