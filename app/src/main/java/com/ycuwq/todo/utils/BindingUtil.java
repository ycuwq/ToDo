package com.ycuwq.todo.utils;

import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.ycuwq.todo.R;
import com.ycuwq.todo.data.bean.Task;
import com.ycuwq.todo.view.edittask.child.ChooseStartTimeDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 绑定View和Data
 * Created by 杨晨 on 2017/12/9.
 */
public class BindingUtil {

    public static void setRemindTime(Fragment fragment, Task task) {
        ChooseStartTimeDialogFragment chooseStartTimeDialogFragment = new ChooseStartTimeDialogFragment();
        chooseStartTimeDialogFragment.show(fragment.getChildFragmentManager(), "chooseStartTime");
    }

	@BindingAdapter("textForDate")
	public static void setTextFormDate(TextView textView, Date date) {
		if (date == null) {
			textView.setText("");
			return;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		textView.setText(dateFormat.format(date));
	}

	@BindingAdapter({"repeat", "task"})
	public static void setRepeatMode(TextView tv, int repeat, Task task) {
		String[] repeatModeNames = tv.getContext().getResources().getStringArray(R.array.list_repeat_mode);
        Task.supplementRepeatModeName(task, repeatModeNames,
                tv.getResources().getStringArray(R.array.weekday_name));
        String text = "";
		if (repeat < repeatModeNames.length) {
		    text = repeatModeNames[repeat];
        }

		tv.setText(text);
	}
}
