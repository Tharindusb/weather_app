package com.example.weatherapptwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<MainModel> mainModels;
    final private ListItemClickListener mOnClickListener;
//    Context context;
    public MainAdapter(ArrayList<MainModel> mainModels,ListItemClickListener listener){
//        this.context = context;
        this.mainModels = mainModels;
        mOnClickListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(mainModels.get(position).getLanglogo());
        holder.textView.setText(mainModels.get(position).getLangName());
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }
    public interface ListItemClickListener {
        void onphoneListClick(int clickedItemIndex);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView=itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onphoneListClick(clickedPosition);
        }
    }
}
