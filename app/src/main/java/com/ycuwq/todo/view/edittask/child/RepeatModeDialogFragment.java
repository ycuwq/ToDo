package com.ycuwq.todo.view.edittask.child;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ycuwq.common.recycler.ExRecyclerAdapter;
import com.ycuwq.common.recycler.ExRecyclerViewHolder;
import com.ycuwq.todo.R;
import com.ycuwq.todo.data.bean.Task;

import java.util.Arrays;
import java.util.List;

/**
 * 选择重复模式
 * Created by ycuwq on 1/30/2018.
 */
public class RepeatModeDialogFragment extends DialogFragment {

    private OnRepeatModeSelectedListener mOnRepeatModeSelectedListener;
    private Task mTask;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_repeat_mode, container, false);
        Context context = getContext();
        if (context == null) {
            return null;
        }

        String[] modeNames = getResources().getStringArray(R.array.list_repeat_mode);
        if (mTask != null) {
            Task.supplementRepeatModeName(mTask, modeNames, getResources().getStringArray(R.array.weekday_name));
        }
        List<String> modeNameList = Arrays.asList(modeNames);
        RecyclerView repeatModeRv = view.findViewById(R.id.rv_dialog_repeat_mode);

        ExRecyclerAdapter<String> adapter = new ExRecyclerAdapter<String>(context, R.layout.item_choose) {
            @Override
            public void bindData(ExRecyclerViewHolder holder, String s, int position) {
                TextView textView = holder.getView(R.id.tv_item_choose);
                textView.setText(s);
                holder.getRootView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mOnRepeatModeSelectedListener != null) {
                            //这边position和模式是一一对应的。
                            mOnRepeatModeSelectedListener.onRepeatModeSelected(position);
                        }
                        dismiss();
                    }
                });
            }
        };
        adapter.setList(modeNameList);
        repeatModeRv.setAdapter(adapter);
        repeatModeRv.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }



    public void setTask(Task task) {
        mTask = task;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.DatePickerBottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定

        dialog.setContentView(R.layout.dialog_choose_remind_time);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        Window window = dialog.getWindow();
        if (window != null) {

            window.getAttributes().windowAnimations = R.style.DatePickerDialogAnim;
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM; // 紧贴底部
            lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
            lp.dimAmount = 0.35f;
            window.setAttributes(lp);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        return dialog;
    }


    public void setOnRepeatModeSelectedListener(OnRepeatModeSelectedListener onRepeatModeSelectedListener) {
        mOnRepeatModeSelectedListener = onRepeatModeSelectedListener;
    }

    public interface OnRepeatModeSelectedListener {
        void onRepeatModeSelected(int repeat);
    }
}
