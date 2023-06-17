package com.example.menu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.menu.Api.FoodSectionApiClient;
import com.example.menu.R;
import com.example.menu.adapters.FoodPagerAdapter;
import com.example.menu.adapters.FoodSectionAdapter;
import com.example.menu.models.FoodSectionDto;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment implements FoodSectionAdapter.OnSectionClickListener {
    private RecyclerView sectionRecyclerView;
    private FoodSectionAdapter sectionAdapter;
    private List<FoodSectionDto> sectionList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food, container, false);

        sectionRecyclerView = rootView.findViewById(R.id.section_recycler_view);
        sectionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sectionList = new ArrayList<>();
        sectionAdapter = new FoodSectionAdapter(sectionList, this);
        sectionRecyclerView.setAdapter(sectionAdapter);

        fetchFoodSections();

        return rootView;
    }

    private void fetchFoodSections() {
        Thread thread = new Thread(() -> {
            try {
                List<FoodSectionDto> foodSections = FoodSectionApiClient.getFoodSections();
                getActivity().runOnUiThread(() -> {
                    sectionList.addAll(foodSections);
                    sectionAdapter.notifyDataSetChanged();
                });
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    @Override
    public void onSectionClick(FoodSectionDto section) {
        Toast.makeText(getContext(), section.getName(), Toast.LENGTH_SHORT).show();
    }
}
