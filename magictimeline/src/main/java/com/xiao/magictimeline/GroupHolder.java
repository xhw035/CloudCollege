package com.xiao.magictimeline;


import android.view.View;
import android.widget.TextView;


public class GroupHolder extends BasicViewHolder {
    private TextView chapter;

    public GroupHolder(View itemView) {
        super(itemView);
        chapter = (TextView) itemView.findViewById(R.id.chapter);
    }


    //为ViewHolder绑定数据
    @Override
    public void bindHolder(CatalogModel catalogModel) {
        chapter.setText(catalogModel.getGruopName());
    }
}
