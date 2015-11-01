package com.qjdchina.qjdsale;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MemberPictureFragment extends Fragment implements View.OnClickListener {
    private ImageView ivCompanyLicense;
    private String TAG = "MemberPictureFragment";
    private View rootView;
    private Bitmap bitmap;
    private FileOutputStream fos;
    private String picPath;
    public static final String SAVE_PATH_IN_SDCARD = "/sdcard/myImage/"; //图片及其他数据保存文件夹
    public static final String IMAGE_CAPTURE_NAME = "cameraTmp.png"; //照片名称
    public final static int REQUEST_CODE_TAKE_PICTURE = 12;//设置拍照操作的标志

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_member_picture, container, false);
        ivCompanyLicense = (ImageView) rootView.findViewById(R.id.iv_comany_license);
        ivCompanyLicense.setTag(0);
        rootView.findViewById(R.id.btn_company_license).setOnClickListener(this);
        rootView.findViewById(R.id.iv_comany_license).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_company_license:
                // 存储卡可用 将照片存储在 sdcard
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Utils.isHasSdcard()) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                            SAVE_PATH_IN_SDCARD, IMAGE_CAPTURE_NAME)));
                } else {
                    Toast.makeText(getActivity(), "SD卡未准备好",
                            Toast.LENGTH_SHORT).show();
                }
                startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
                break;
            case R.id.iv_comany_license:
                Dialog dialog = new MemberPictureDialog(getActivity(),
                        R.style.dialog_full_screen);
                ImageView iv = (ImageView) dialog.findViewById(R.id.iv_dialog_member);
                iv.setImageBitmap(getImage(SAVE_PATH_IN_SDCARD + IMAGE_CAPTURE_NAME, 1024));
                dialog.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            Bitmap bp = getImage(SAVE_PATH_IN_SDCARD + IMAGE_CAPTURE_NAME);
            ivCompanyLicense.setImageBitmap(bp);
            ivCompanyLicense.setVisibility(View.VISIBLE);
        }
    }


    private Bitmap compressImage(Bitmap image, int size) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > size) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    private Bitmap getImage(String srcPath, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

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
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap, size);//压缩好比例大小后再进行质量压缩
    }

    private Bitmap getImage(String srcPath) {
        //default is 100kb
        return getImage(srcPath, 100);
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, "Capture picture successfully");
//        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
//            Bundle extras = data.getExtras();
//            bitmap = (Bitmap) extras.get("data");
//            //TODO replace IMAGE_CAPTURE_NAME
//            picPath = picSave(bitmap, IMAGE_CAPTURE_NAME);
//            Bitmap bm = lessenUriImage(picPath);
//            ivCompanyLicense.setImageBitmap(bm);
//            ivCompanyLicense.setVisibility(View.VISIBLE);
//            Log.d(TAG, "Set picture to view successfully");
//        }
//        rootView.requestLayout();
//    }

//    public String picSave(Bitmap bitmap, String name) {
//        File file = new File(SAVE_PATH_IN_SDCARD);
//        file.mkdirs();
//        try {
//            fos = new FileOutputStream(SAVE_PATH_IN_SDCARD + name);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);// 把数据写入文件
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fos.flush();
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return SAVE_PATH_IN_SDCARD + name;
//    }
//
//    public Bitmap lessenUriImage(String path) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeFile(path, options); //此时返回 bm 为空
//        options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = (int) (options.outHeight / (float) 320);
//        if (be <= 0)
//            be = 1;
//        options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
//        bitmap = BitmapFactory.decodeFile(path, options);
//        int w = bitmap.getWidth();
//        int h = bitmap.getHeight();
//        Log.d(TAG, w + " " + h); //after zoom
//        return bitmap;
//    }
}
