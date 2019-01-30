package com.example.tarun.vdbassignment.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tarun.vdbassignment.R;
import com.example.tarun.vdbassignment.appUtils.AppListeners;
import com.example.tarun.vdbassignment.beans.Data;

import java.util.List;

/**
 * Created by Tarun on 1/29/2019.
 */

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<Data> dataList;

    public RecyclerAdapter(Context context, List<Data> list){
        this.context = context;
        this.dataList = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (!TextUtils.isEmpty(dataList.get(position).getName()))
            holder.txtName.setText(dataList.get(position).getName());
        else
            holder.txtName.setText("null");

        if (!TextUtils.isEmpty(dataList.get(position).getDesc()))
            holder.txtDesc.setText(dataList.get(position).getDesc());
        else
            holder.txtDesc.setText("null");

        if (!TextUtils.isEmpty(dataList.get(position).getLanuage()))
            holder.txtLanguage.setText(dataList.get(position).getLanuage());
        else
            holder.txtLanguage.setText("null");

        if (!TextUtils.isEmpty(dataList.get(position).getOpenIssuesCount()))
            holder.txtOpenIssuesCount.setText(dataList.get(position).getOpenIssuesCount());
        else
            holder.txtOpenIssuesCount.setText("0");

        if (!TextUtils.isEmpty(dataList.get(position).getWatchers()))
            holder.txtWatchers.setText(dataList.get(position).getWatchers());
        else
            holder.txtWatchers.setText("0");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void updateAdapter(List<Data> list){
        this.dataList = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        private TextView txtName,txtDesc,txtLanguage,txtOpenIssuesCount,txtWatchers;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView)itemView.findViewById(R.id.txtName);
            txtDesc = (TextView)itemView.findViewById(R.id.txtDesc);
            txtLanguage = (TextView)itemView.findViewById(R.id.txtLanguage);
            txtOpenIssuesCount = (TextView)itemView.findViewById(R.id.txtOpenIssuesCount);
            txtWatchers = (TextView)itemView.findViewById(R.id.txtWatchers);
        }
    }
}
