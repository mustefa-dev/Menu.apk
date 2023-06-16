package com.example.menu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.Api.FoodApiClient;
import com.example.menu.R;
import com.example.menu.adapters.FoodAdapter;
import com.example.menu.models.FoodDto;
import com.example.menu.models.FoodSectionDto;

import java.util.List;

public class FoodSectionFragment extends Fragment {
    private static final String ARG_SECTION = "section";

    private FoodSectionDto section;
    private TextView sectionTextView;
    private RecyclerView foodRecyclerView;
    private FoodAdapter foodAdapter;

    public FoodSectionFragment() {
        // Required empty public constructor
    }

    public static FoodSectionFragment newInstance(FoodSectionDto section) {
        FoodSectionFragment fragment = new FoodSectionFragment();
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
        foodRecyclerView = view.findViewById(R.id.drink_recycler_view);

        sectionTextView.setText(section.getName());

        fetchFoodBySection(section.getName());

        return view;
    }

    private void fetchFoodBySection(String sectionName) {
        FoodApiClient foodApiClient = new FoodApiClient();
        foodApiClient.getFoodBySectionName(sectionName, new FoodApiClient.FoodCallback() {
            @Override
            public void onSuccess(List<FoodDto> foodList) {
                requireActivity().runOnUiThread(() -> {
                    if (foodList != null && !foodList.isEmpty()) {
                        setupFoodRecyclerView(foodList);
                    } else {
                        // Handle empty food response
                        Toast.makeText(getContext(), "No food available for this section.", Toast.LENGTH_SHORT).show();
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

    private void setupFoodRecyclerView(List<FoodDto> foodList) {
        int numColumns = 2; // Set the number of columns you want to display
        foodAdapter = new FoodAdapter(foodList);
        foodRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));
        foodRecyclerView.setAdapter(foodAdapter);
    }
}
