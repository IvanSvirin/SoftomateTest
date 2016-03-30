package com.example.ivansv.softomatetest.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivansv.softomatetest.R;
import com.example.ivansv.softomatetest.model.TextLanguage;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<TextLanguage> textLanguages;

    public RecyclerViewAdapter(ArrayList<TextLanguage> textLanguages) {
        this.textLanguages = textLanguages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item = textLanguages.get(position);
        holder.userTextTextView.setText(textLanguages.get(position).getText());
        holder.userTextLanguageTextView.setText(textLanguages.get(position).getLanguage());
    }


    @Override
    public int getItemCount() {
        return textLanguages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView userTextTextView;
        TextView userTextLanguageTextView;
        TextLanguage item;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            userTextTextView = (TextView) itemView.findViewById(R.id.userText);
            userTextLanguageTextView = (TextView) itemView.findViewById(R.id.userTextLanguage);
        }
    }
}
