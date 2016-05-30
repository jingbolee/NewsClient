package com.ljb.zhbj.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ljb.zhbj.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @FileName: com.ljb.zhbj.view.RefreshListView.java
 * @Author: Li Jingbo
 * @Date: 2016-05-26 11:38
 * @Version V1.0 <描述当前版本功能>
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
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
    private RotateAnimation rotateUp;
    private RotateAnimation rotateDown;
    private int dis;
    private ProgressBar pbProgress;
    private View mFooterView;
    private int mFooterViewHeight;
    private boolean isLoderMore;

    public RefreshListView(Context context) {
        super(context);
        initRefreshHeader();
        initRefreshFooter();
    }

    @TargetApi( Build.VERSION_CODES.LOLLIPOP )
    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initRefreshHeader();
        initRefreshFooter();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRefreshHeader();
        initRefreshFooter();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRefreshHeader();
        initRefreshFooter();
    }

    private void initRefreshHeader() {
        mHeaderView = View.inflate(getContext(), R.layout.view_refresh_header, null);
        tvRefreshtitle = (TextView) mHeaderView.findViewById(R.id.tv_refresh_title);
        tvRefreshdate = (TextView) mHeaderView.findViewById(R.id.tv_refresh_date);
        ivRefreshArrow = (ImageView) mHeaderView.findViewById(R.id.iv_refresh_arrow);
        pbProgress = (ProgressBar) mHeaderView.findViewById(R.id.pb_progress);
        //添加头布局文件
        addHeaderView(mHeaderView);
        mHeaderView.measure(0, 0);//设置为0，0的时候，系统会主动测量该view的宽和高

        mHeaderViewHeight = mHeaderView.getMeasuredHeight();//获取测量以后的高度
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);

        tvRefreshdate.setText("最后刷新时间:" + getCurrentTime());
        initArrowAnimation();
    }

    private void initRefreshFooter() {
        mFooterView = View.inflate(getContext(), R.layout.view_refresh_foot, null);
        addFooterView(mFooterView);
        mFooterView.measure(0, 0);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
        setOnScrollListener(this);
    }

    private void initArrowAnimation() {
        rotateUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateUp.setDuration(200);
        rotateUp.setFillAfter(true);

        rotateDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateDown.setDuration(200);
        rotateDown.setFillAfter(true);

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
                //当前状态为刷新状态时，不做任何的操作。
                if ( mCurrentState == STATE_REFRESHING ) {
                    break;
                }
                int endY = (int) ev.getRawY();
                //listview滑动距离
                dis = endY - startY;
                if ( dis > 0 && getFirstVisiblePosition() == 0 ) {
                    int paddingTop = dis - mHeaderViewHeight;
                    mHeaderView.setPadding(0, paddingTop, 0, 0);

                    //下拉刷新
                    if ( (paddingTop < 0) && (mCurrentState != STATE_PULL_REFRESH) ) {
                        mCurrentState = STATE_PULL_REFRESH;
                        setRefreshState();
                        //松开刷新
                    } else if ( paddingTop > 0 && mCurrentState != STATE_RELEASE_REFRESH ) {
                        mCurrentState = STATE_RELEASE_REFRESH;
                        setRefreshState();
                    }
                    return true;
                }
//                Log.e(TAG, "滑动距离为：" + dis + "结束位置和开始位置：" + endY + ":" + startY);
                break;
            case MotionEvent.ACTION_UP:
                if ( mCurrentState == STATE_RELEASE_REFRESH ) {
                    mCurrentState = STATE_REFRESHING;
                    mHeaderView.setPadding(0, 0, 0, 0);
                    if ( mListener != null ) {
                        mListener.onRefresh();
                    }
                    setRefreshState();
                } else if ( mCurrentState == STATE_PULL_REFRESH ) {
                    setRefreshState();
                    mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
                } else if ( mCurrentState == STATE_REFRESHING ) {
                    break;
                }


                startY = -1;
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void setRefreshState() {
        switch ( mCurrentState ) {
            case STATE_REFRESHING:
                tvRefreshtitle.setText("刷新中...");
                ivRefreshArrow.clearAnimation();
                ivRefreshArrow.setVisibility(View.INVISIBLE);
                pbProgress.setVisibility(View.VISIBLE);
                break;
            case STATE_RELEASE_REFRESH:
                ivRefreshArrow.startAnimation(rotateUp);
                tvRefreshtitle.setText("松开刷新");
                ivRefreshArrow.setVisibility(View.VISIBLE);
                pbProgress.setVisibility(View.INVISIBLE);
                break;

            case STATE_PULL_REFRESH:
                ivRefreshArrow.startAnimation(rotateDown);
                tvRefreshtitle.setText("下拉刷新");
                ivRefreshArrow.setVisibility(View.VISIBLE);
                pbProgress.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private RefreshListener mListener;

    //给listview设置刷新监听
    public void setRefreshListener(RefreshListener listener) {
        mListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if ( scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING ) {
            if ( getLastVisiblePosition() == getCount() - 1 && !isLoderMore ) {
                mFooterView.setPadding(0, 0, 0, 0);
                setSelection(getCount() - 1);
                if ( mListener != null ) {
                    mListener.onLoadMore();
                }
                isLoderMore = true;
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onItemClick(AdapterView< ? > parent, View view, int position, long id) {
        if ( mListener != null ) {
            mOnItemClickListener.onItemClick(parent, view, position - getHeaderViewsCount(), id);
        }
    }

    //监听刷新的接口
    public interface RefreshListener {
        void onRefresh();

        void onLoadMore();
    }

    public void refreshComplete(boolean success) {
        if ( isLoderMore ) {
            mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
            isLoderMore = false;
        } else {
            tvRefreshtitle.setText("下拉刷新");
            mCurrentState = STATE_PULL_REFRESH;
            pbProgress.clearAnimation();
            pbProgress.setVisibility(View.INVISIBLE);
            ivRefreshArrow.setVisibility(View.VISIBLE);
            mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
            if ( success ) {
                tvRefreshdate.setText("最后刷新时间:" + getCurrentTime());
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    //获取当前时间
    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(this);
        mOnItemClickListener = listener;
    }

}
