package com.ycuwq.todo.view.common;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ycuwq.datepicker.time.HourAndMinutePicker;
import com.ycuwq.todo.R;

import java.text.NumberFormat;

/**
 * 选择提醒时间
 * Created by ycuwq on 2018/1/25.
 */
public class ChooseRemindTimeDialogFragment extends DialogFragment {

    protected Button mCancelButton, mDecideButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_choose_remind_time, container);

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumIntegerDigits(2);
        TextView timeTv = view.findViewById(R.id.tv_dialog_choose_remind_time);
        HourAndMinutePicker hourAndMinutePicker = view.findViewById(R.id.picker_dialog_choose_remind_time);
        hourAndMinutePicker.setOnTimeListener(new HourAndMinutePicker.OnTimeListener() {
            @Override
            public void onTimeSelected(int hour, int minute) {

                timeTv.setText(String.format("%s:%s", numberFormat.format(hour), numberFormat.format(minute)));
            }
        });
        mCancelButton = view.findViewById(R.id.btn_dialog_choose_remind_cancel);
        mDecideButton = view.findViewById(R.id.btn_dialog_choose_remind_decide);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mDecideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定

        dialog.setContentView(R.layout.dialog_choose_remind_time);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        Window window = dialog.getWindow();
        if (window != null) {

            window.getAttributes().windowAnimations = R.style.dialogAnim;
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM; // 紧贴底部
            lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
            window.setAttributes(lp);
        }
        return dialog;
    }
}
