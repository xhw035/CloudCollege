package com.xiao.magictimeline;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Crated by 24540 on 2017/3/13.
 */

public class ChildHolder extends BasicViewHolder {
    public TextView title;
    public TextView videoTime;
    public ImageView dwonload;
    public SwipeDragLayout swipeDragLayout;

    public ChildHolder(View itemView) {
        super(itemView);
        swipeDragLayout = (SwipeDragLayout) itemView.findViewById(R.id.swipeDragLayout);
        title = (TextView) itemView.findViewById(R.id.title);
        videoTime = (TextView) itemView.findViewById(R.id.vidoeTime);
        dwonload = (ImageView) itemView.findViewById(R.id.dwonload);
        dwonload = (ImageView) itemView.findViewById(R.id.dwonload);
    }


    //为ViewHolder绑定数据
    @Override
    public void bindHolder(CatalogModel catalogModel) {
        title.setText(catalogModel.getChildName());
        title.setTextColor(catalogModel.getFontColor());
        videoTime.setText(catalogModel.getVideoTime());
        videoTime.setTextColor(catalogModel.getFontColor());
    }
}
