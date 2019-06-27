package com.carking.quotationlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * 作者：flyjiang
 * 时间: 2015/6/18 12:05
 * 说明: 可以放到ScrollView里面的Listview
 * 需要手动把ScrollView滚动至最顶端，因为使用这个方法的话，默认在ScrollView顶端的项是ListView
 * sv = (ScrollView) findViewById(R.id.act_solution_4_sv); sv.smoothScrollTo(0, 0);
 */
public class ListViewForScrollView extends ListView {
    public ListViewForScrollView(Context context) {
        super(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}