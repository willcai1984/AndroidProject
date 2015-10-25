package com.qjdchina.qjdsale;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MemberPicturePlusFragment extends Fragment implements View.OnClickListener {
    private String TAG = "MemberPictureFragment";
    private ImageView ivCompanyLicense0;
    private ImageView ivCompanyLicense1;
    private ImageView ivCompanyLicense2;
    private ImageView ivCompanyLicense3;
    private ImageView ivCompanyLicense4;
    private View rootView;
    private Bitmap bitmap;
    private int index = 0;
    public final static int REQUEST_CODE_TAKE_PICTURE = 12;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_member_picture_plus, container, false);
        ivCompanyLicense0 = (ImageView) rootView.findViewById(R.id.iv_comany_license0);
        ivCompanyLicense1 = (ImageView) rootView.findViewById(R.id.iv_comany_license1);
        ivCompanyLicense2 = (ImageView) rootView.findViewById(R.id.iv_comany_license2);
        ivCompanyLicense3 = (ImageView) rootView.findViewById(R.id.iv_comany_license3);
        ivCompanyLicense4 = (ImageView) rootView.findViewById(R.id.iv_comany_license4);
        //Tag0:not picture yet;Tag1:have picture in this tv
        ivCompanyLicense0.setTag(0);
        ivCompanyLicense1.setTag(0);
        ivCompanyLicense2.setTag(0);
        ivCompanyLicense3.setTag(0);
        ivCompanyLicense4.setTag(0);
        ivCompanyLicense0.setOnClickListener(this);
        ivCompanyLicense1.setOnClickListener(this);
        ivCompanyLicense2.setOnClickListener(this);
        ivCompanyLicense3.setOnClickListener(this);
        ivCompanyLicense4.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_comany_license0:
                index = 0;
                if (ivCompanyLicense0.getTag().equals(0)) {
                    startCamera();
                } else {
                    Toast.makeText(getActivity(), "此处应该有放大的图片",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_comany_license1:
                index = 1;
                if (ivCompanyLicense1.getTag().equals(0)) {
                    startCamera();
                } else {
                    Toast.makeText(getActivity(), "此处应该有放大的图片",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_comany_license2:
                index = 2;
                if (ivCompanyLicense2.getTag().equals(0)) {
                    startCamera();
                } else {
                    Toast.makeText(getActivity(), "此处应该有放大的图片",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_comany_license3:
                index = 3;
                if (ivCompanyLicense3.getTag().equals(0)) {
                    startCamera();
                } else {
                    Toast.makeText(getActivity(), "此处应该有放大的图片",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_comany_license4:
                index = 4;
                if (ivCompanyLicense4.getTag().equals(0)) {
                    startCamera();
                } else {
                    Toast.makeText(getActivity(), "此处应该有放大的图片",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        processPicture(requestCode, resultCode, data);
    }

    public void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
    }

    public void processPicture(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "Capture picture successfully");
        //获取结果时，从结果内查看index参数
        Bundle b = intent.getExtras();
        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) b.get("data");
            switch (index) {
                case 0:
                    ivCompanyLicense0.setImageBitmap(bitmap);
                    ivCompanyLicense0.setTag(1);
                    ivCompanyLicense1.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    ivCompanyLicense1.setImageBitmap(bitmap);
                    ivCompanyLicense1.setTag(1);
                    ivCompanyLicense2.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    ivCompanyLicense2.setImageBitmap(bitmap);
                    ivCompanyLicense2.setTag(1);
                    ivCompanyLicense3.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    ivCompanyLicense3.setImageBitmap(bitmap);
                    ivCompanyLicense3.setTag(1);
                    ivCompanyLicense4.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    ivCompanyLicense4.setImageBitmap(bitmap);
                    ivCompanyLicense4.setTag(1);
                    break;
            }
        }
    }
}
