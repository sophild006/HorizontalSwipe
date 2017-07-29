package com.github.shchurov.horizontalwheelview.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Map;

public class HSVLayout extends LinearLayout {
 
    private HSVAdapter adapter;
    private Context context;
 
    public HSVLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
 
    /**
     * 设置布局适配器
     *
     * @param layoutWidthPerAvatar 指定了每一个 item 的占用宽度
     * @param adapter 适配器
     * @param notify 在点击某一个 item 后的回调
     */
    public void setAdapter(int layoutWidthPerAvatar, HSVAdapter adapter,
                           final INotifySelectItem notify) {
        this.adapter = adapter;
 
        for (int i = 0; i < adapter.getCount(); i++) {
            final Map<String, Object> map = adapter.getItem(i);
            View view = adapter.getView(i, null, null);
 
            // 为视图设定点击监听器
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 点击选择了某一个 Item 视图
                    notify.select(finalI);
                }
            });
 
            this.setOrientation(HORIZONTAL);
 
            // 设置固定显示的每个 item 布局的宽度
            this.addView(view, new LinearLayout.LayoutParams(
                    layoutWidthPerAvatar, LayoutParams.WRAP_CONTENT));
        }
    }
}