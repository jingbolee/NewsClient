package com.ljb.zhbj.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ljb.zhbj.R;

/**
 * @FileName: com.ljb.zhbj.view.RefreshListView.java
 * @Author: Li Jingbo
 * @Date: 2016-05-26 11:38
 * @Version V1.0 <描述当前版本功能>
 */
public class RefreshListView extends ListView {
    private static final String TAG = "RefreshListView";

    private static final int STATE_PULL_REFRESH = 0; //下拉刷新
    private static final int STATE_RELEASE_REFRESH = 1; //松开刷新
    private static final int STATE_REFRESHING = 2;  //刷新中

    private int mCurrentState = STATE_PULL_REFRESH;  //当前的刷新状态
    private int startY = -1;

    private int mHeaderViewHeight;  //header布局高度

    private View mHeaderView;   //header布局
    private TextView tvRefreshtitle;
    private TextView tvRefreshdate;
    private ImageView ivRefreshArrow;

    public RefreshListView(Context context) {
        super(context);
        initRefreshHeader();
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initRefreshHeader();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRefreshHeader();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRefreshHeader();
    }

    private void initRefreshHeader() {
        mHeaderView = View.inflate(getContext(), R.layout.view_refresh_header, null);
        tvRefreshtitle = (TextView) mHeaderView.findViewById(R.id.tv_refresh_title);
        tvRefreshdate = (TextView) mHeaderView.findViewById(R.id.tv_refresh_date);
        ivRefreshArrow = (ImageView) mHeaderView.findViewById(R.id.iv_refresh_arrow);
        //添加头布局文件
        addHeaderView(mHeaderView);
        mHeaderView.measure(0, 0);//设置为0，0的时候，系统会主动测量该view的宽和高

        mHeaderViewHeight = mHeaderView.getMeasuredHeight();//获取测量以后的高度
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch ( ev.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if ( startY == -1 ) {//防止startY获取不到数值
                    startY = (int) ev.getRawY();
                }
                int endY = (int) ev.getRawY();
                int dis = Math.abs(endY - startY);
                int paddingTop = dis - mHeaderViewHeight;
                mHeaderView.setPadding(0, paddingTop, 0, 0);
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                break;
        }
        return super.onTouchEvent(ev);
    }
}
