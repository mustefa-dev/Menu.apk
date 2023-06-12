package com.example.menu.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.menu.fragment.FoodSectionFragment;
import com.example.menu.models.FoodSectionDto;

import java.util.List;

public class FoodPagerAdapter extends FragmentPagerAdapter {
    private List<FoodSectionDto> sections;

    public FoodPagerAdapter(@NonNull FragmentManager fm, List<FoodSectionDto> sections) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.sections = sections;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position >= 0 && position < sections.size()) {
            FoodSectionDto section = sections.get(position);
            return FoodSectionFragment.newInstance(section);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return sections.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < sections.size()) {
            FoodSectionDto section = sections.get(position);
            return section.getName();
        } else {
            return null;
        }
    }
}
