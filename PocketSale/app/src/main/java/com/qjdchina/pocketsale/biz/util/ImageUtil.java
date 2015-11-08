package com.qjdchina.pocketsale.biz.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2015/11/1.
 */
public class ImageUtil {
    public static Bitmap getImage(String file) {
        //default is 512kb
        return getImage(file, 512);
    }

    /*
    * @PARAM file， 图片绝对路径
    * @PARAM size， 图形大小，单位为kb
    */
    public static Bitmap getImage(String file, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, newOpts);//此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(file, newOpts);
        return compressImage(bitmap, size);//压缩好比例大小后再进行质量压缩
    }

    /*
    * @PARAM image， 图形源
    * @PARAM size， 图形大小，单位为kb
    */
    public static Bitmap compressImage(Bitmap image, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //现在的图片都约2M，从70%开始压缩
        //TODO bitmap转化成JPEG的话，size不是很准
        for (int i = 100; i > 0; i = i - 5) {
            image.compress(Bitmap.CompressFormat.JPEG, i, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            if (baos.toByteArray().length / 1024 <= size) {
                break;
            }
            baos.reset();
        }

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    public static boolean bitmap2File(Bitmap bmp, String filePath) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bmp.compress(format, quality, stream);
    }
}
