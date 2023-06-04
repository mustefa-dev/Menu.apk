package com.example.menu.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.menu.Api.MenuApiClient;
import com.example.menu.R;
import com.example.menu.adapters.ItemAdapter;
import com.example.menu.models.Item;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 123;

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private MenuApiClient menuApiClient;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemAdapter = new ItemAdapter(getActivity(), new ArrayList<>()); // Initialize with an empty list
        recyclerView.setAdapter(itemAdapter);

        menuApiClient = new MenuApiClient();

        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchItems(); // Refresh the item list
            swipeRefreshLayout.setRefreshing(false); // Indicate that the refresh is complete
        });

        if (checkPermission()) {
            fetchItems();
        } else {
            requestPermission();
        }

        return root;
    }

    private void fetchItems() {
        menuApiClient.getItems(new MenuApiClient.MenuApiCallback<List<Item>>() {
            @Override
            public void onSuccess(List<Item> items) {
                itemAdapter.setItems(items);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(getActivity(), "Failed to fetch items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchItems();
            } else {
                Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
