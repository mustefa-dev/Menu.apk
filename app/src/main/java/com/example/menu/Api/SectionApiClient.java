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
    private static final String BASE_URL = "http://192.168.75.101:5043/api/";

    public static List<SectionDto> getSections() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + "Section")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return parseSectionResponse(responseBody);
            } else {
                throw new IOException("Unexpected response code: " + response.code());
            }
        }
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
}
