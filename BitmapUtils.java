package test.ykai.com.app_test;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/** Bitmap 的高效加载
    使用方法：
    后面两个参数300，300。是要设置的目标大小宽和高。
    bitmap = BitmapUtils.decodeSampledBitmapFromResoruce(getResources(), R.drawable.beijing_autumn_2,300,300);
    imageView.setImageBitmap(bitmap);
 */
public class BitmapUtils {
    private static String TAG= "BitmapUtils";

    public static Bitmap decodeSampledBitmapFromResoruce(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 获取 BitmapFactory.Options，这里面保存了很多有关 Bitmap 的设置
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置 true 轻量加载图片信息
        options.inJustDecodeBounds = true;
        // 由于上方设置false，这里轻量加载图片
        BitmapFactory.decodeResource(res, resId, options);
        // 计算采样率
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 设置 false 正常加载图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap =BitmapFactory.decodeResource(res, resId, options);
        Log.d(TAG, "decodeSampledBitmapFromResoruce: w " + bitmap.getWidth() + " h " + bitmap.getHeight() );
        return bitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;
        // 宽或高大于预期就将采样率 *=2 进行缩放, 留下空白
//        while ((height / inSampleSize) > reqHeight || (width / inSampleSize) > reqWidth) {
//            inSampleSize *= 2;
//        }

        // 宽且高大于预期就将采样率 *=2 进行缩放, 大出来的地方剪裁掉
        while ((height / inSampleSize) > reqHeight && (width / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
        Log.d(TAG, "calculateInSampleSize: " + inSampleSize);
        return inSampleSize;
    }

}
