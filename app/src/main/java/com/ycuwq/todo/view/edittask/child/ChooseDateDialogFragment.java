package com.ycuwq.todo.view.edittask.child;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.ycuwq.datepicker.date.DatePicker;
import com.ycuwq.todo.R;

import java.text.NumberFormat;

/**
 * 日期选择
 * Created by ycuwq on 2018/2/3.
 */
public class ChooseDateDialogFragment extends DialogFragment {
    protected Button mCancelButton, mDecideButton;

    private OnDateSelectedListener mOnDateSelectedListener;
    private int mInitYear = -1, mInitMonth = -1, mInitDay = -1;
    private String mTitle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_choose_date, container);

        TextView titleTv = view.findViewById(R.id.tv_dialog_choose_date_title);
        if (mTitle != null) {
            titleTv.setText(mTitle);
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumIntegerDigits(2);
        TextView timeTv = view.findViewById(R.id.tv_dialog_choose_date);
        DatePicker datePicker = view.findViewById(R.id.picker_dialog_choose_date);
        datePicker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day) {
                timeTv.setText(String.format("%s-%s-%s", year,
                        numberFormat.format(month), numberFormat.format(day)));
            }
        });
        mCancelButton = view.findViewById(R.id.btn_dialog_choose_date_cancel);
        mDecideButton = view.findViewById(R.id.btn_dialog_choose_date_decide);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mDecideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDateSelectedListener != null) {
                    mOnDateSelectedListener.onDateSelected(datePicker.getYear(),
                            datePicker.getMonth(), datePicker.getDay());
                }
                dismiss();
            }
        });
        if (mInitYear != -1) {
            datePicker.setDate(mInitYear, mInitMonth, mInitDay, false);
        }

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.DatePickerBottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定

        dialog.setContentView(R.layout.dialog_choose_date);
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

    public void setInitDate(int year, int month, int day) {
        mInitYear = year;
        mInitMonth = month;
        mInitDay = day;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        mOnDateSelectedListener = onDateSelectedListener;
    }

    public interface OnDateSelectedListener {
        void onDateSelected(int year, int month, int day);
    }
}
