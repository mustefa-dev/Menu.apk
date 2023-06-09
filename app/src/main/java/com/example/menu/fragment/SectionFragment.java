package com.example.menu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.menu.R;

public class SectionFragment extends Fragment {
    private static final String ARG_SECTION_NAME = "sectionName";

    private String sectionName;

    public SectionFragment() {
        // Required empty public constructor
    }

    public static SectionFragment newInstance(String sectionName) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NAME, sectionName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sectionName = getArguments().getString(ARG_SECTION_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section, container, false);
        TextView sectionTextView = view.findViewById(R.id.section_name_text_view);
        sectionTextView.setText(sectionName);
        return view;
    }
}
