package com.xiao.magictimeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 24540 on 2017/3/13.
 */

public class CatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;

    private List<CatalogModel> mList = new ArrayList<>();

    private Context mContext;

    private OnItemClickListener listener;

    public   interface OnItemClickListener{
        //void onItemLongClick(View view,int position){};
        void  onTitleClick(ChildHolder holder, int position);
        void  onDownloadClick(View view, int position);
    }

    public CatalogAdapter(Context mContext, List<CatalogModel> list,OnItemClickListener listener) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        mList=list;
        this.listener=listener;
    }


    /*//使用此方法从获取数据
    public void addList(List<CatalogModel> list){
        mList.addAll(list);
    }*/

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据不同的viewType，创建并返回影响的ViewHoder
        switch (viewType){
            case CatalogModel.TYPE_GROUP:
                return new GroupHolder(mLayoutInflater.inflate(R.layout.catalog_group_item,parent,false));
            case CatalogModel.TYPE_CHILD:
                return new ChildHolder(mLayoutInflater.inflate(R.layout.catalog_child_item,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //抽象出ViewHolder，所以在进行绑定的时候可以直接调用
        ((BasicViewHolder)holder).bindHolder(mList.get(position));
        setItemEvent((BasicViewHolder)holder);
    }

    protected void setItemEvent(final BasicViewHolder holder){
        /*if(holder.getItemViewType()== CatalogModel.TYPE_GROUP) {
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int getlayoutposition = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, getlayoutposition);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int getlayoutposition = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, getlayoutposition);
                        return false;
                    }
                });
            }
        }*/

        if(holder.getItemViewType()== CatalogModel.TYPE_CHILD){
            ((ChildHolder) holder).dwonload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int getlayoutposition = holder.getLayoutPosition();
                    if(listener!=null)
                        listener.onDownloadClick(((ChildHolder) holder).dwonload, getlayoutposition);
                }
            });


            ((ChildHolder) holder).swipeDragLayout.addListener(new SwipeDragLayout.SwipeListener() {
                @Override
                public void onUpdate(SwipeDragLayout layout, float offset) {
                    //Log.d("offset", "onUpdate() called with offset = [" + offset + "]");
                }

                @Override
                public void onOpened(SwipeDragLayout layout) {
                    //Toast.makeText(mContext, "onOpened", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClosed(SwipeDragLayout layout) {
                    //Toast.makeText(mContext, "onClosed", Toast.LENGTH_SHORT).show();
                }

                /**
                 * 等同于setOnClickListener
                 * 见Method {@link SwipeDragLayout#onFinishInflate()}
                 * @param layout
                 */
                @Override
                public void onClick(SwipeDragLayout layout) {
                    //Toast.makeText(mContext, mList.get(holder.getLayoutPosition()).getChildName(), Toast.LENGTH_SHORT).show();
                    int getlayoutposition = holder.getLayoutPosition();
                    if(listener!=null)
                        listener.onTitleClick(((ChildHolder) holder),getlayoutposition);
                }
            });
        }
    }

}
