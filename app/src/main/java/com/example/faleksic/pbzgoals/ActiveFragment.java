package com.example.faleksic.pbzgoals;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ActiveFragment extends Fragment {

    private List<Saving> savingList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SavingsAdapter savingsAdapter;
    private DBHelper dbHelper;


    public ActiveFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_active);
        dbHelper = new DBHelper(getActivity());

        savingsAdapter = new SavingsAdapter(savingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(savingsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(savingsAdapter);

        prepareSavingData();

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.action_add);
        item.setVisible(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        savingList.clear();
        savingsAdapter.notifyItemRangeRemoved(0, savingsAdapter.getItemCount());
        prepareSavingData();
    }

    private void prepareSavingData(){

        Cursor cursor = dbHelper.getAllSavings();

        while (cursor.moveToNext()) {
            boolean active = cursor.getInt(7) > 0;
            if(active) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String dateEnd = cursor.getString(2);
                double amount = cursor.getDouble(3);
                int frequency = cursor.getInt(4);
                int category = cursor.getInt(5);
                double paid = cursor.getDouble(6);
                savingList.add(new Saving(id, title, dateEnd, amount, frequency, category, paid, active));
            }
        }

        cursor.close();

        savingsAdapter.notifyDataSetChanged();
    }
}
