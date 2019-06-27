package com.carking.quotationlibrary.view_guide;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * 作者：flyjiang
 * 时间: 2015/6/23 11:29
 * 邮箱：caiyoufei@looip.cn
 * 说明: https://github.com/w446108264/XhsParallaxWelcome
 */
public class ParallaxLayoutInflater extends LayoutInflater {

    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
        setUpLayoutFactory();
    }

    private void setUpLayoutFactory() {
        if (!(getFactory() instanceof ParallaxFactory)) {
            setFactory(new ParallaxFactory(this, getFactory()));
        }
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallaxLayoutInflater(this, newContext);
    }
}