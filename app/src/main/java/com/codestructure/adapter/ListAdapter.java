package com.codestructure.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codestructure.R;
import com.codestructure.bean.HomeBean;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HomeBean> homeBeanArrayList;

    public ListAdapter(Context context, ArrayList<HomeBean> homeBeanArrayList) {
        this.context = context;
        this.homeBeanArrayList = homeBeanArrayList;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder viewHolder, int i) {
        final HomeBean homeBean = homeBeanArrayList.get(i);

        viewHolder.tv_name.setText(!homeBean.name.equals("") ? homeBean.name : "");

        viewHolder.tv_description.setText(!homeBean.description.equals("") ? homeBean.description : "");

        if (homeBean.drawable != null) {
            viewHolder.iv_profile.setImageBitmap(homeBean.drawable);
        }
    }

    @Override
    public int getItemCount() {
        return homeBeanArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_profile;
        TextView tv_name, tv_description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_description = itemView.findViewById(R.id.tv_description);
        }
    }
}
