package com.qjdchina.qjdsale;


import android.app.Activity;
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
        rootView.findViewById(R.id.btn_company_license).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_company_license:
                // 存储卡可用 将照片存储在 sdcard
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (Utils.isHasSdcard()) {
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
//                            SAVE_PATH_IN_SDCARD, IMAGE_CAPTURE_NAME)));
//                }
                startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Capture picture successfully");
        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            //TODO replace IMAGE_CAPTURE_NAME
            picPath = picSave(bitmap, IMAGE_CAPTURE_NAME);
            Bitmap bm = lessenUriImage(picPath);
            ivCompanyLicense.setImageBitmap(bm);
            ivCompanyLicense.setVisibility(View.VISIBLE);
            Log.d(TAG, "Set picture to view successfully");
        }
        rootView.requestLayout();
    }

    public String picSave(Bitmap bitmap, String name) {
        File file = new File(SAVE_PATH_IN_SDCARD);
        file.mkdirs();
        try {
            fos = new FileOutputStream(SAVE_PATH_IN_SDCARD + name);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SAVE_PATH_IN_SDCARD + name;
    }

    public Bitmap lessenUriImage(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); //此时返回 bm 为空
        options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = (int) (options.outHeight / (float) 320);
        if (be <= 0)
            be = 1;
        options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
        bitmap = BitmapFactory.decodeFile(path, options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Log.d(TAG, w + " " + h); //after zoom
        return bitmap;
    }
}
