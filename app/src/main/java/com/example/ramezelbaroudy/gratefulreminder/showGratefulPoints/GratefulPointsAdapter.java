package com.example.ramezelbaroudy.gratefulreminder.showGratefulPoints;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.ramezelbaroudy.gratefulreminder.R;
import com.example.ramezelbaroudy.gratefulreminder.repositories.GratefulPointsRepository;

import java.util.ArrayList;

import io.reactivex.Scheduler;

public class GratefulPointsAdapter extends RecyclerView.Adapter<GratefulPointsAdapter.ViewHolder> {
    public ArrayList<String> mDataset;
    private LayoutInflater mInflater;
    GratefulPointShowPresenter gratefulPointShowPresenter;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageButton mImageButton;
        public ViewHolder(View v) {
            super(v);
            mTextView = itemView.findViewById(R.id.gratefulSentenceString);
            mImageButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public GratefulPointsAdapter(Context context, ArrayList<String> mDataset, GratefulPointsRepository gratefulPointsRepository, Scheduler scheduler){
        this.mDataset = mDataset;
        this.mInflater = LayoutInflater.from(context);
        GratefulPointsShowActivity gratefulPointsShowActivity = (GratefulPointsShowActivity) context;
        gratefulPointShowPresenter = new GratefulPointShowPresenter(gratefulPointsShowActivity, gratefulPointsRepository,scheduler);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String sentence = mDataset.get(position);
        holder.mTextView.setText(sentence);
        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gp = mDataset.get(position);
                gratefulPointShowPresenter.deleteGratefulPoint(gp);
                mDataset.remove(position);
                notifyItemRemoved(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
