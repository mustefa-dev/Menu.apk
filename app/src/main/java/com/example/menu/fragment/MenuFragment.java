package com.example.menu.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.menu.Api.CategoryApiClient;
import com.example.menu.R;
import com.example.menu.adapters.CategoryAdapter;
import com.example.menu.models.Category;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 123;

    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private CategoryApiClient categoryApiClient;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        recyclerView = view.findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryAdapter = new CategoryAdapter(getActivity(), new ArrayList<>()); // Initialize with an empty list
        recyclerView.setAdapter(categoryAdapter);
        categoryApiClient = new CategoryApiClient();
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchCategories(); // Refresh the category list
            swipeRefreshLayout.setRefreshing(false); // Indicate that the refresh is complete
        });

        if (checkPermission()) {
            fetchCategories();
        } else {
            requestPermission();
        }

        return view;
    }

    private void fetchCategories() {
        categoryApiClient.getCategories(new CategoryApiClient.CategoryApiCallback<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                categoryAdapter.setCategories(categories);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(getActivity(), "Failed to fetch categories", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
