package net.anumbrella.pullrefresh.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import net.anumbrella.pullrefresh.PullRefreshBase.LoadingLayout;
import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.Utils.BaseUtils;
import net.anumbrella.pullrefresh.Utils.PreferenceUtils;

/**
 * Created by ${flyjiang} on 2016/12/8.
 * 文件说明：上拉下拉ScrollView
 */

public class PullRefreshScrollView extends PullRefreshBase<ScrollView> {

    private ScrollView mScrollView;

    public PullRefreshScrollView(Context context) {
        super(context);
    }

    public PullRefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("jiang01",this.getChildCount()+"jiang");
    }

    public PullRefreshScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
        mScrollView = new ScrollView(context);
        Log.e("jiang02",this.getChildCount()+"");
        mScrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return mScrollView;
    }

    @Override
    protected boolean isReadyForPullDown() {
        return mRefreshableView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullUp() {
        return false;
    }

   /* @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

    }*/

    /**
     * 设置Header样式
     *
     * @param layout
     */
    public void setHeaderLayout(LoadingLayout layout) {
        super.setHeaderLoadingLayout(getContext(), layout);
    }

   public void  setScrollViewStatue(){
       try {
           View view = getChildAt(getChildCount()-1);
           removeViewAt(getChildCount()-1);
           ((ScrollView)((FrameLayout)getChildAt(1)).getChildAt(0)).addView(view);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    /**
     * 设置Footer样式
     *
     * @param layout
     */
    public void setFooterLayout(LoadingLayout layout) {
        super.setFooterLoadingLayout(getContext(), layout);
    }


    /**
     * 设置图标是否可以显示
     *
     * @param value
     */
    public void setIconVisibility(boolean value) {
        if (getHeaderLoadingLayout().getIcon() != null) {
            if (value) {
                getHeaderLoadingLayout().getIcon().setVisibility(VISIBLE);
            } else {
                getHeaderLoadingLayout().getIcon().setVisibility(GONE);
            }
        }
    }

    /**
     * 设置图片
     *
     * @param imageView
     */
    public void setIconImage(int imageView) {
        if (getHeaderLoadingLayout().getIcon() != null) {
            if (imageView != -1) {
                ((ImageView) getHeaderLoadingLayout().getIcon()).setImageResource(imageView);
            }
        }
    }


    /**
     * 设置时间显示
     *
     * @param friendlyTime
     */
    public void setFriendlyTime(boolean friendlyTime) {
        PreferenceUtils.write(getContext(), BaseUtils.Md5(getClass().getName()), getClass().getName(), friendlyTime);
        if (friendlyTime) {
            updateDisplayTime();
        } else {
            updateDisplayTime();
        }
    }


    /**
     * 下拉刷新显示中是否展示时间
     *
     * @param value
     */
    public void setDisplayTime(boolean value) {
        if (getHeaderLoadingLayout().getDisplayTimeLayout() != null) {
            if (value) {
                getHeaderLoadingLayout().getDisplayTimeLayout().setVisibility(VISIBLE);
            } else {
                getHeaderLoadingLayout().getDisplayTimeLayout().setVisibility(GONE);
            }
        }
    }


    @Override
    protected void updateDisplayTime() {
        long time = System.currentTimeMillis();
        if (PreferenceUtils.readBoolean(getContext(), BaseUtils.Md5(getClass().getName()), getClass().getName(), true)) {
            setLastUpdatedLabel(BaseUtils.friendlyTime(time));
        } else {
            setLastUpdatedLabel(BaseUtils.formatDateTime(time));
        }
    }
}
