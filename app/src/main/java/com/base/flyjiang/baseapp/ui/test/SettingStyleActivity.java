package com.base.flyjiang.baseapp.ui.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.base.flyjiang.baseapp.R;

import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.Widget.PullRefreshListView;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * author：anumbrella
 * Date:16/8/4 上午11:10
 */
public class SettingStyleActivity extends AppCompatActivity implements PullRefreshBase.OnRefreshListener<ListView> {

    private PullRefreshListView mPullListView;
    private ArrayAdapter<String> mAdapter;
    private ListView mListView;
    private LinkedList<String> mListItems;


    public static final String[] mStrings = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"
    };
    /*public static final String[] mStrings = {
    };*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);


        mPullListView = (PullRefreshListView) findViewById(R.id.PullRefreshListView);
        //上拉刷新
        mPullListView.setPullLoadEnabled(true);
        //滑动到底部自动加载
        mPullListView.setScrollLoadEnabled(false);


        mListItems = new LinkedList<String>();
        // mListItems.addAll(Arrays.asList(mStrings));
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
        mListView = mPullListView.getRefreshableView();
        mPullListView.setAdapterWithProgress(mAdapter);
        // mPullListView.setAdapter(mAdapter);
        mPullListView.setOnRefreshListener(this);
        onPullDownRefresh(mPullListView);
    }


    @Override
    public void onPullDownRefresh(PullRefreshBase<ListView> refreshView) {

      new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             mListItems.addAll(Arrays.asList(mStrings));
                mListItems.clear();
             mAdapter.notifyDataSetChanged();
              mPullListView.setHasMoreData(true);
                mPullListView.onPullDownRefreshComplete();
            }
        }, 2000);

    }

    @Override
    public void onPullUpToRefresh(PullRefreshBase<ListView> refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListItems.addAll(Arrays.asList(mStrings));
                mAdapter.notifyDataSetChanged();

                mPullListView.onPullUpRefreshComplete();
                mPullListView.setHasMoreData(true);

            }
        }, 2000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
