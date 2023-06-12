package com.example.menu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.Api.FoodApiClient;
import com.example.menu.Api.SectionApiClient;
import com.example.menu.R;
import com.example.menu.adapters.FoodAdapter;
import com.example.menu.adapters.SectionAdapter;
import com.example.menu.models.FoodDto;
import com.example.menu.models.FoodSectionDto;
import com.example.menu.models.SectionDto;

import java.io.IOException;
import java.util.List;

public class FoodSectionFragment extends Fragment {
    private static final String ARG_SECTION = "section";

    private FoodSectionDto section;
    private GridView sectionGridView;
    private RecyclerView foodRecyclerView;
    private FoodAdapter foodAdapter;

    private SectionAdapter sectionAdapter;
    private SectionApiClient sectionApiClient;

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
        sectionApiClient = new SectionApiClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_section, container, false);
        sectionGridView = view.findViewById(R.id.grid_view_sections);
        foodRecyclerView = view.findViewById(R.id.food_recycler_view);

        try {
            setupSectionGridView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fetchFoodBySection(section.getName());

        return view;
    }

    private void setupSectionGridView() throws IOException {
        sectionApiClient.getSections(new SectionApiClient.SectionCallback() {
            @Override
            public void onSuccess(List<SectionDto> sections) {
                sectionAdapter = new SectionAdapter(getContext(), sections);
                sectionGridView.setAdapter(sectionAdapter);

                sectionGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SectionDto selectedSection = sections.get(position);
                        fetchFoodBySection(selectedSection.getName());
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchFoodBySection(String sectionName) {
        FoodApiClient foodApiClient = new FoodApiClient();
        foodApiClient.getFoodBySectionName(sectionName, new FoodApiClient.FoodCallback() {
            @Override
            public void onSuccess(List<FoodDto> foodList) {
                requireActivity().runOnUiThread(() -> {
                    if (foodList != null && !foodList.isEmpty()) {
                        showFoodRecyclerView();
                        setupFoodRecyclerView(foodList);
                    } else {
                        hideFoodRecyclerView();
                        Toast.makeText(getContext(), "No food available for this section.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                requireActivity().runOnUiThread(() -> {
                    hideFoodRecyclerView();
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void setupFoodRecyclerView(List<FoodDto> foodList) {
        foodAdapter = new FoodAdapter(foodList);
        foodRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Change the number of columns as needed
        foodRecyclerView.setAdapter(foodAdapter);
    }

    private void showFoodRecyclerView() {
        foodRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideFoodRecyclerView() {
        foodRecyclerView.setVisibility(View.GONE);
    }
}
