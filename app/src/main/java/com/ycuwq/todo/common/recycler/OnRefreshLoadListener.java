package com.ycuwq.todo.common.recycler;

/**
 * RecyclerView监听器
 * Created by yangchen on 2017/4/5.
 */
public interface OnRefreshLoadListener {
    /**
     * 当滑动到底部时调用
     */
    void onSlideToEnd();
}
