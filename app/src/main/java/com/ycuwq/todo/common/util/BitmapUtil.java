package com.ycuwq.todo.common.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by yangchen on 2017/3/21.
 */

public class BitmapUtil {

    /**
     * bitmap转换成base64的数据
     * @param bitmap    bitmap
     * @return  base64 String
     */
    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }

    /**
     * base64转换成比bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap convertStringToBitmat(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
