package com.ycuwq.todo.utils;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.ycuwq.todo.R;
import com.ycuwq.todo.data.bean.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 绑定View和Data
 * Created by 杨晨 on 2017/12/9.
 */
public class BindingUtil {
	@BindingAdapter("textForDate")
	public static void setTextFormDate(TextView textView, Date date) {
		if (date == null) {
			textView.setText("");
			return;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		textView.setText(dateFormat.format(date));
	}

	@BindingAdapter("repeat")
	public static void setRepeatMode(TextView tv, int repeat) {
		Resources resources = tv.getContext().getResources();
		String text;
		switch (repeat) {
			case Task.REPEAT_NULL:
				text = resources.getString(R.string.repeat_null);
				break;
			case Task.REPEAT_DAY:
				text = resources.getString(R.string.repeat_day);
				break;
			case Task.REPEAT_WEEK:
				text = resources.getString(R.string.repeat_week);
				break;
			case Task.REPEAT_MONTH:
				text = resources.getString(R.string.repeat_month);
				break;
			case Task.REPEAT_YEAR:
				text = resources.getString(R.string.repeat_year);
				break;
			default:
				text = "";
				break;
		}
		tv.setText(text);
	}
}
