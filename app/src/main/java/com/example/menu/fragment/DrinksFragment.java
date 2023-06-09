package com.example.menu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.menu.Api.SectionApiClient;
import com.example.menu.R;
import com.example.menu.adapters.DrinksPagerAdapter;
import com.example.menu.models.SectionDto;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class DrinksFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrinksPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drinks, container, false);

        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.view_pager);

        fetchSections();

        return rootView;
    }

    private void fetchSections() {
        Thread thread = new Thread(() -> {
            try {
                List<SectionDto> sections = SectionApiClient.getSections();
                getActivity().runOnUiThread(() -> {
                    if (!sections.isEmpty()) {
                        pagerAdapter = new DrinksPagerAdapter(getChildFragmentManager(), sections);
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
