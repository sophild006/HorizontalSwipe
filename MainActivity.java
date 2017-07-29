package com.github.shchurov.horizontalwheelview.sample;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity
        implements INotifySelectItem {

    private static final String TAG = "wwq";

    private HorizontalScrollView hsv;
    private HSVLayout layoutAvatar;
    private HSVAdapter adapterAvatar;

    private TextView tvScrollX;

    private int layoutWidthPerAvatar = 0;

    private Integer[] images = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };

    // 记录当前居中的头像索引
    private int currentIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int layoutWidth = (int) (width - getResources().getDimension(R.dimen.activity_horizontal_margin) * 2);

        // 每一个头像占用的宽度
        layoutWidthPerAvatar = layoutWidth / 6;

        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        hsv.setOnTouchListener(new View.OnTouchListener() {

            private int lastScrollX = 0;
            private int TouchEventId = -9987832;

            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == TouchEventId) {
                        if (lastScrollX == hsv.getScrollX()) {
                            // 停止滚动，计算合适的位置(采用四舍五入)
                            int indexScrollTo = Math.round(lastScrollX / (layoutWidthPerAvatar * 1.0f));
                            Log.d(TAG, "stop scroll - " + lastScrollX
                                    + "|" + layoutWidthPerAvatar
                                    + "|" + lastScrollX / (layoutWidthPerAvatar * 1.0f)
                                    + "|" + indexScrollTo);
                            if (indexScrollTo > 0) {
                                    hsv.smoothScrollTo(indexScrollTo * layoutWidthPerAvatar, 0);
                            } else {
                                hsv.smoothScrollTo(15, 0);
                            }
                        } else {
                            handler.sendMessageDelayed(
                                    handler.obtainMessage(TouchEventId), 50);
                            lastScrollX = hsv.getScrollX();
                        }
                    }
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "touch event - action: " + event.getAction()
                        + "|" + event.getX()
                        + "|" + event.getY()
                        + "|" + hsv.getScrollX()
                        + "|" + hsv.getScrollY());
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.sendMessageDelayed(handler.obtainMessage(TouchEventId), 50);
                }else if(event.getAction()==MotionEvent.ACTION_MOVE){

                }
                return false;
            }
        });

        layoutAvatar = (HSVLayout) findViewById(R.id.avatar_layout);
        adapterAvatar = new HSVAdapter(this, layoutWidthPerAvatar);
        for (int i = 0; i < images.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", images[i]);
            map.put("index", (i + 1));
            adapterAvatar.addObject(map);
        }
        layoutAvatar.setAdapter(layoutWidthPerAvatar, adapterAvatar, this);

        tvScrollX = (TextView) findViewById(R.id.scrollx_tv);
    }

    @Override
    public void select(int position) {
        Toast.makeText(this, "select " + String.valueOf(position),
                Toast.LENGTH_SHORT).show();
        if (position > 0) {
            if (currentIndex != position) {
                hsv.smoothScrollTo((position - 1) * layoutWidthPerAvatar, 0);
                currentIndex = position;
            }
        }
    }

    public void onClickScrollX(View view) {
        tvScrollX.setText("Scroll.x = " + String.valueOf(hsv.getScrollX()));
    }
}