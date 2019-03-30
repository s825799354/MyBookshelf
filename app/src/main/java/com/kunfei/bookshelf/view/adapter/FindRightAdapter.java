package com.kunfei.bookshelf.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kunfei.bookshelf.R;
import com.kunfei.bookshelf.bean.FindKindBean;
import com.kunfei.bookshelf.bean.FindKindGroupBean;
import com.kunfei.bookshelf.widget.recycler.expandable.OnRecyclerViewListener;
import com.kunfei.bookshelf.widget.recycler.expandable.bean.RecyclerViewData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FindRightAdapter extends RecyclerView.Adapter<FindRightAdapter.MyViewHolder> {
    private List<Object> data = new ArrayList<>();
    private Context context;
    private OnRecyclerViewListener.OnItemLongClickListener onItemLongClickListener;
    private final static int TYPE_GROUP = 0;
    private final static int TYPE_ITEM = 1;

    public FindRightAdapter(OnRecyclerViewListener.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setData(List<RecyclerViewData> data) {
        this.data.clear();
        for (RecyclerViewData recyclerViewData : data) {
            this.data.add(recyclerViewData.getGroupData());
            this.data.addAll(recyclerViewData.getChildList());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        if (i == TYPE_GROUP) {
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_find_right, viewGroup, false));
        }
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_find_right_child, viewGroup, false));
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof FindKindGroupBean) {
            return TYPE_GROUP;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int pos) {
        if (data.get(pos) instanceof FindKindGroupBean) {
            FindKindGroupBean groupBean = (FindKindGroupBean) data.get(pos);
            myViewHolder.sourceName.setText(groupBean.getGroupName());
            myViewHolder.sourceName.setOnClickListener(null);
            myViewHolder.sourceName.setOnLongClickListener(v -> {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onGroupItemLongClick(pos, pos, v);
                }
                return true;
            });
        } else {
            FindKindBean kindBean = (FindKindBean) data.get(pos);
            myViewHolder.sourceName.setText(kindBean.getKindName());
            myViewHolder.sourceName.setOnClickListener(v -> {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onChildItemLongClick(pos, pos, pos, v);
                }
            });
            myViewHolder.sourceName.setOnLongClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Object> getData() {
        return data;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sourceName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sourceName = itemView.findViewById(R.id.tv_source_name);
        }
    }
}

