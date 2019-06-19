package com.example.celebritydb_week2wknd;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<Celebrity> celebList;

    public RecyclerViewAdapter(ArrayList<Celebrity> celebList){
        this.celebList = celebList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celeb_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        final Celebrity itemsCelebs = celebList.get(i);
        viewHolder.tvFullName.setText(itemsCelebs.getName());
        viewHolder.tvAge.setText(itemsCelebs.getAge());
        viewHolder.tvProfession.setText(itemsCelebs.getProfession());

        // make a toast when an item is clicked
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v){
               Toast.makeText(v.getContext(), itemsCelebs.getName(), Toast.LENGTH_SHORT).show();
           }
        });
    }

    public void addCelebs(Celebrity celebrity){
        celebList.add(celebrity);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (celebList != null){
            return celebList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName;
        TextView tvAge;
        TextView tvProfession;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvProfession = itemView.findViewById(R.id.tvProfession);
        }
    } // end class ViewHolder
}


