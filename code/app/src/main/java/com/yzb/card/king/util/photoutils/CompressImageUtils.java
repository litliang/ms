package com.yzb.card.king.util.photoutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;


import com.yzb.card.king.util.ImageUtils;
import com.yzb.card.king.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with Android Studio.
 * User: ryan@xisue.com
 * Date: 11/24/14
 * Time: 2:20 PMs
 * Desc: CompressImageUtils
 */
public class CompressImageUtils {

    public static final String TAG = "ChoosePhotoUtils";

    public static void compressImageFile(CropParams cropParams, Uri originUri, Uri compressUri) {
        Bitmap bitmap = null;
        OutputStream out = null;
        try {
            int degree = ImageUtils.readPictureDegree(originUri.getPath());

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(originUri.getPath(), options);
            // Calculate inSampleSize
            int minSideLength = cropParams.compressWidth > cropParams.compressHeight ? cropParams.compressHeight : cropParams.compressWidth;
            options.inSampleSize = computeSampleSize(options, minSideLength, cropParams.compressWidth * cropParams.compressHeight);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(originUri.getPath(), options);
            if(0 != degree)
                bitmap = ImageUtils.rotaingImageView(degree, bitmap);

            File compressFile = new File(compressUri.getPath());
            if (!compressFile.exists()) {
                boolean result = compressFile.createNewFile();
                Log.d(TAG, "Target " + compressUri + " not exist, create a new one " + result);
            }
            out = new FileOutputStream(compressFile);
            boolean result = bitmap.compress(Bitmap.CompressFormat.JPEG, cropParams.compressQuality, out);
            String str = result ? "succeed" : "failed";
            LogUtil.e(TAG, "Compress bitmap " + str);
        } catch (Exception e) {
            LogUtil.e(TAG, "compressInputStreamToOutputStream");
        } finally {
            if (bitmap != null)
                bitmap.recycle();
            try {
                if (out != null)
                    out.close();
            } catch (IOException ignore) {
            }
        }
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}
