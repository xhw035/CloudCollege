package com.xiao.magictimeline;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xiao on 2017/4/24.
 */

public class CatalogFragment extends Fragment {

    private final CatalogAdapter.OnItemClickListener listener;
    private RecyclerView mRecyclerView;
    private List<CatalogModel> list;
    public  CatalogAdapter adpter;

    public CatalogFragment() {
        listener = null;
    }

    @SuppressLint("ValidFragment")
    public CatalogFragment(List<CatalogModel> list, CatalogAdapter.OnItemClickListener listener) {
        this.list = list;
        this.listener=listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration( new RecyclerView.ItemDecoration(){

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top=10;
                outRect.bottom=10;
            }
        });

        adpter = new CatalogAdapter(getActivity(),list,listener);
        mRecyclerView.setAdapter(adpter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
