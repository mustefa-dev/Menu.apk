package com.example.menu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.Api.DrinkApiClient;
import com.example.menu.R;
import com.example.menu.adapters.DrinkAdapter;
import com.example.menu.models.SectionDto;
import com.example.menu.models.DrinkDto;

import java.util.List;

public class DrinkSectionFragment extends Fragment {
    private static final String ARG_SECTION = "section";

    private SectionDto section;
    private TextView sectionTextView;
    private RecyclerView drinkRecyclerView;
    private DrinkAdapter drinkAdapter;

    public DrinkSectionFragment() {
        // Required empty public constructor
    }

    public static DrinkSectionFragment newInstance(SectionDto section) {
        DrinkSectionFragment fragment = new DrinkSectionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SECTION, section);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            section = getArguments().getParcelable(ARG_SECTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_section, container, false);
        sectionTextView = view.findViewById(R.id.section_name_text_view);
        drinkRecyclerView = view.findViewById(R.id.drink_recycler_view);

        sectionTextView.setText(section.getName());

        fetchDrinksBySection(section.getName());

        return view;
    }

    private void fetchDrinksBySection(String sectionName) {
        DrinkApiClient drinkApiClient = new DrinkApiClient();
        drinkApiClient.getDrinksBySectionName(sectionName, new DrinkApiClient.DrinkCallback() {
            @Override
            public void onSuccess(List<DrinkDto> drinks) {
                requireActivity().runOnUiThread(() -> {
                    if (drinks != null && !drinks.isEmpty()) {
                        setupDrinkRecyclerView(drinks);
                    } else {
                        // Handle empty drinks response
                        Toast.makeText(getContext(), "No drinks available for this section.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void setupDrinkRecyclerView(List<DrinkDto> drinks) {
        int numColumns = 2; // Set the number of columns you want to display
        drinkAdapter = new DrinkAdapter(drinks);
        drinkRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));
        drinkRecyclerView.setAdapter(drinkAdapter);
    }
}
