package com.example.menu.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.menu.fragment.DrinkSectionFragment;
import com.example.menu.models.SectionDto;

import java.util.List;

public class DrinksPagerAdapter extends FragmentPagerAdapter {
    private List<SectionDto> sections;

    public DrinksPagerAdapter(@NonNull FragmentManager fm, List<SectionDto> sections) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.sections = sections;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position >= 0 && position < sections.size()) {
            SectionDto section = sections.get(position);
            return DrinkSectionFragment.newInstance(section);
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
            SectionDto section = sections.get(position);
            return section.getName();
        } else {
            return null;
        }
    }
}
