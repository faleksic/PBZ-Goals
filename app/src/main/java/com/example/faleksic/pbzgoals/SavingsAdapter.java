package com.example.faleksic.pbzgoals;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class SavingsAdapter extends RecyclerView.Adapter<SavingsAdapter.MyViewHolder> {
    private List<Saving> savingList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int id;
        private TextView title, amount;
        private ProgressBar progressBar;
        private ImageView iconImage;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.saving_title_text_view);
            amount = view.findViewById(R.id.money_saving_text_view);
            progressBar = view.findViewById(R.id.saving_progress_bar);
            iconImage = view.findViewById(R.id.saving_icon_image_view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), SavingActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("progress", progressBar.getProgress());
            view.getContext().startActivity(intent);
        }
    }

    public SavingsAdapter(List<Saving> savingList) {
        this.savingList = savingList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saving_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Saving saving = savingList.get(position);
        holder.id = saving.getId();
        holder.title.setText(saving.getTitle());
        holder.amount.setText(String.valueOf(saving.getAmount()));
        holder.progressBar.setProgress(saving.getPaid());
        holder.iconImage.setImageResource(saving.getCategory());
    }

    @Override
    public int getItemCount() {
        return savingList.size();
    }
}
