package com.qjdchina.qjdsale;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MemberPicturePlusFragment extends Fragment implements View.OnClickListener {
    private String TAG = "MemberPictureFragment";
    private View rootView;
    private Bitmap bitmap;
    private ImageView ivCompanyLicense0;
    private ImageView ivCompanyLicense1;
    private ImageView ivCompanyLicense2;
    private ImageView ivCompanyLicense3;
    private ImageView ivCompanyLicense4;
    private int index = 0;
    private Map<ImageView, Bitmap> mapPic2Bit = new HashMap<ImageView, Bitmap>();
    public static final int REQUEST_CODE_TAKE_PICTURE = 12;
    public static final String SAVE_PATH_IN_SDCARD = "/sdcard/myImage/"; //图片及其他数据保存文件夹
    public static final String IMAGE_CAPTURE_NAME = "cameraTmp.png"; //照片名称
    //organization_code
    private ImageView ivOrganizationcode1;
    //other_certificates
    private ImageView ivOtherCertificates1;
    private ImageView ivOtherCertificates2;
    private ImageView ivOtherCertificates3;
    private ImageView ivOtherCertificates4;
    private ImageView ivOtherCertificates5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_member_picture_plus, container, false);
        initParameters(rootView);
        return rootView;
    }

    /*
    初始化各参数
    */
    private void initParameters(View view) {
        ivCompanyLicense0 = (ImageView) initParameter(view, R.id.iv_comany_license0);
        ivCompanyLicense1 = (ImageView) initParameter(view, R.id.iv_comany_license1);
        ivCompanyLicense2 = (ImageView) initParameter(view, R.id.iv_comany_license2);
        ivCompanyLicense3 = (ImageView) initParameter(view, R.id.iv_comany_license3);
        ivCompanyLicense4 = (ImageView) initParameter(view, R.id.iv_comany_license4);
        //organizationCode
        ivOrganizationcode1 = (ImageView) initParameter(view, R.id.iv_organization_code_1);
        //otherCertificates
        ivOtherCertificates1 = (ImageView) initParameter(view, R.id.iv_other_certificates_1);
        ivOtherCertificates2 = (ImageView) initParameter(view, R.id.iv_other_certificates_2);
        ivOtherCertificates3 = (ImageView) initParameter(view, R.id.iv_other_certificates_3);
        ivOtherCertificates4 = (ImageView) initParameter(view, R.id.iv_other_certificates_4);
        ivOtherCertificates5 = (ImageView) initParameter(view, R.id.iv_other_certificates_5);
    }

    /*
    处理点击事件
    */
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

    /*
    启动相机
    */
    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imgFile = new File(SAVE_PATH_IN_SDCARD, IMAGE_CAPTURE_NAME);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
    }

    /*
    相机回送数据
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        processPicture(requestCode, resultCode, data);
    }

    /*
    图片处理
    */
    public void processPicture(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "Capture picture successfully");
        Bundle b = intent.getExtras();
        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) b.get("data");
            switch (index) {
                case 0:
                    ivCompanyLicense0.setImageBitmap(bitmap);
                    mapPic2Bit.put(ivCompanyLicense0, bitmap);
                    //load captured picture
                    ivCompanyLicense0.setTag(1);
                    ivCompanyLicense1.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    ivCompanyLicense1.setImageBitmap(bitmap);
                    mapPic2Bit.put(ivCompanyLicense1, bitmap);
                    ivCompanyLicense1.setTag(1);
                    ivCompanyLicense2.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    ivCompanyLicense2.setImageBitmap(bitmap);
                    mapPic2Bit.put(ivCompanyLicense2, bitmap);
                    ivCompanyLicense2.setTag(1);
                    ivCompanyLicense3.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    ivCompanyLicense3.setImageBitmap(bitmap);
                    mapPic2Bit.put(ivCompanyLicense3, bitmap);
                    ivCompanyLicense3.setTag(1);
                    ivCompanyLicense4.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    ivCompanyLicense4.setImageBitmap(bitmap);
                    mapPic2Bit.put(ivCompanyLicense4, bitmap);
                    ivCompanyLicense4.setTag(1);
                    break;
            }
        }
    }

    private View initParameter(View rootView, int id) {
        View view;
        view = rootView.findViewById(id);
        //Tag0:not picture yet;Tag1:have picture in this tv
        view.setTag(0);
        view.setOnClickListener(this);
        return view;
    }

}
