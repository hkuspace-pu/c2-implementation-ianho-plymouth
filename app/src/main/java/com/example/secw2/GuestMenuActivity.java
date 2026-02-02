package com.example.secw2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.secw2.Util.MenuBean;
import com.example.secw2.Util.MenuDbAction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuestMenuActivity extends BaseEdgeToEdgeActivity {

    private Button btnBack;
    private RecyclerView rvMenu;
    private ProgressBar progress;
    private TextView tvEmpty;
    private final List<MenuBean> data = new ArrayList<>();
    private MenuAdapter adapter;
    private ExecutorService ioExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_menu);

        btnBack = findViewById(R.id.btnBack);
        rvMenu = findViewById(R.id.rvMenu);
        progress = findViewById(R.id.progress);
        tvEmpty = findViewById(R.id.tvEmpty);

        SearchView searchView = findViewById(R.id.searchView);
        if (searchView != null) searchView.setVisibility(View.GONE);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, GuestHomeActivity.class);
            startActivity(intent);
            finish();
        });

        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuAdapter(data);
        rvMenu.setAdapter(adapter);

        ioExecutor = Executors.newSingleThreadExecutor();
        loadAll();
    }

    private void loadAll() {
        showLoading(true);
        ioExecutor.submit(() -> {
            List<MenuBean> list;
            try {
                list = new MenuDbAction(getApplicationContext()).selectAll();
            } catch (Exception e) {
                list = new ArrayList<>();
            }
            List<MenuBean> finalList = list;
            runOnUiThread(() -> {
                showLoading(false);
                data.clear();
                data.addAll(finalList);
                adapter.notifyDataSetChanged();
                boolean empty = data.isEmpty();
                tvEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
                rvMenu.setVisibility(empty ? View.INVISIBLE : View.VISIBLE);
            });
        });
    }

    private void showLoading(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
        rvMenu.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ioExecutor != null) ioExecutor.shutdownNow();
    }

    private static class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
        private final List<MenuBean> list;

        MenuAdapter(List<MenuBean> list) {
            this.list = list;
        }

        @Override
        public MenuViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            android.view.View v = android.view.LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
            return new MenuViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MenuViewHolder holder, int position) {
            MenuBean b = list.get(position);
            holder.text1.setText(b.getItemName());
            holder.text2.setText(String.format("$%.2f", b.getPrice()));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private static class MenuViewHolder extends RecyclerView.ViewHolder {
        android.widget.TextView text1;
        android.widget.TextView text2;

        MenuViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}