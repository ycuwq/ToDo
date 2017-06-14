package com.yangchen.extracalendarview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.TypedValue;

/**
 * 为ImageView添加涟漪效果
 * @author https://github.com/prolificinteractive/material-calendarview/
 */
public class DirectionButton extends android.support.v7.widget.AppCompatImageView {

	public DirectionButton(Context context) {
		super(context);

		setBackgroundResource(getThemeSelectableBackgroundId(context));
	}

	public void setColor(int color) {
		setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		setAlpha(enabled ? 1f : 0.1f);
	}

	/**
	 * 设置涟漪效果（Ripple）
	 */
	private static int getThemeSelectableBackgroundId(Context context) {
		int colorAttr = context.getResources().getIdentifier(
				"selectableItemBackgroundBorderless", "attr", context.getPackageName());
		//无边界只有5.0以上才可以
		if (colorAttr == 0) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				colorAttr = android.R.attr.selectableItemBackgroundBorderless;
			} else {
				colorAttr = android.R.attr.selectableItemBackground;
			}
		}
		TypedValue outValue = new TypedValue();
		context.getTheme().resolveAttribute(colorAttr, outValue, true);
		return outValue.resourceId;
	}
}
