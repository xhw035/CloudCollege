package com.cloud.college.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.cloud.college.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiao on 2017/4/9.
 */

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.detailToolbar) Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        //this.setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.detail_toolbar_menu);
        toolbar.setNavigationOnClickListener(this);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_item1) {


                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}