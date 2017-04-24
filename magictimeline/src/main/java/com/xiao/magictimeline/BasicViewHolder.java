package com.xiao.magictimeline;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 24540 on 2017/3/13.
 */

public abstract class BasicViewHolder extends RecyclerView.ViewHolder {
    public BasicViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindHolder(CatalogModel catalogModel);

}
