package com.example.menu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.menu.Api.FoodSectionApiClient;
import com.example.menu.R;
import com.example.menu.adapters.FoodPagerAdapter;
import com.example.menu.models.FoodSectionDto;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class FoodFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FoodPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food, container, false);

        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.view_pager);

        fetchFoodSections();

        return rootView;
    }

    private void fetchFoodSections() {
        Thread thread = new Thread(() -> {
            try {
                List<FoodSectionDto> foodSections = FoodSectionApiClient.getFoodSections();
                getActivity().runOnUiThread(() -> {
                    if (!foodSections.isEmpty()) {
                        pagerAdapter = new FoodPagerAdapter(getChildFragmentManager(), foodSections);
                        viewPager.setAdapter(pagerAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                    }
                });
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
