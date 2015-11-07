package com.qjdchina.pocketsale.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.qjdchina.pocketsale.R;
import com.qjdchina.pocketsale.biz.util.SDUtil;

import java.io.File;

import static com.qjdchina.pocketsale.biz.util.ImageUtil.getImage;

public class MemberPhotoFragment extends Fragment implements View.OnClickListener {
    private String TAG = "MemberPictureFragment";
    private View rootView;
    private Bitmap bitmap;

    //company_license1
    private ImageView ivCompanyLicense1;
    //organization_code
    private ImageView ivOrganizationcode1;
    //other_certificates
    private ImageView ivOtherCertificates1;
    private ImageView ivOtherCertificates2;
    private ImageView ivOtherCertificates3;
    private ImageView ivOtherCertificates4;
    private ImageView ivOtherCertificates5;

    private byte operationFlag = 0000;
    public static final int REQUEST_CODE_TAKE_PICTURE = 12;
    public static final String SAVE_PATH_CUSTOMER_DATA = "/sdcard/pocketsale/customerdata/"; //图片及其他数据保存文件夹
    public static final String FILE_COMPANYLICENSE1 = "COMPANYLICENSE1.png";
    public static final String FILE_ORGANIZATIONCODE1 = "ORGANIZATIONCODE1.png";
    public static final String FILE_OTHERCERTIFICATES1 = "OTHERCERTIFICATES1.png";
    public static final String FILE_OTHERCERTIFICATES2 = "OTHERCERTIFICATES2.png";
    public static final String FILE_OTHERCERTIFICATES3 = "OTHERCERTIFICATES3.png";
    public static final String FILE_OTHERCERTIFICATES4 = "OTHERCERTIFICATES4.png";
    public static final String FILE_OTHERCERTIFICATES5 = "OTHERCERTIFICATES5.png";

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_member_photo, container, false);
        initParameters(rootView);
        Intent intent=getActivity().getIntent();
        Bundle bundle=intent.getExtras();
//        //TODO 将提示信息转换为查询操作
//        Toast.makeText(getActivity(), "照片ID是" + bundle.get("id"), Toast.LENGTH_LONG).show();
        return rootView;
    }

    /*
    初始化各参数
    */
    private void initParameters(View view) {
        //company_license1
        ivCompanyLicense1 = (ImageView) initParameter(view, R.id.iv_company_license_1);
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
            case R.id.iv_company_license_1:
                operationFlag = 0001;
                clickProcess(ivCompanyLicense1, SAVE_PATH_CUSTOMER_DATA, FILE_COMPANYLICENSE1);
                break;
            case R.id.iv_organization_code_1:
                operationFlag = 0011;
                clickProcess(ivOrganizationcode1, SAVE_PATH_CUSTOMER_DATA, FILE_ORGANIZATIONCODE1);
                break;
            case R.id.iv_other_certificates_1:
                operationFlag = 0021;
                clickProcess(ivOtherCertificates1, SAVE_PATH_CUSTOMER_DATA, FILE_OTHERCERTIFICATES1);
                break;
            case R.id.iv_other_certificates_2:
                operationFlag = 0022;
                clickProcess(ivOtherCertificates2, SAVE_PATH_CUSTOMER_DATA, FILE_OTHERCERTIFICATES2);
                break;
            case R.id.iv_other_certificates_3:
                operationFlag = 0023;
                clickProcess(ivOtherCertificates3, SAVE_PATH_CUSTOMER_DATA, FILE_OTHERCERTIFICATES3);
                break;
            case R.id.iv_other_certificates_4:
                operationFlag = 0024;
                clickProcess(ivOtherCertificates4, SAVE_PATH_CUSTOMER_DATA, FILE_OTHERCERTIFICATES4);
                break;
            case R.id.iv_other_certificates_5:
                operationFlag = 0025;
                clickProcess(ivOtherCertificates5, SAVE_PATH_CUSTOMER_DATA, FILE_OTHERCERTIFICATES5);
                break;
        }
    }

    /*
    单一点击事件处理
    */
    private void clickProcess(View view, String folder, String file) {
        if (view.getTag().equals(0)) {
            startCamera(folder, file);
        } else {
            Dialog dialog = new MemberPhotoDialog(getActivity(),R.style.dialog_center);
            dialog.show();
            ImageView iv = (ImageView) dialog.findViewById(R.id.iv_dialog_member);
            ProgressBar pb= (ProgressBar) dialog.findViewById(R.id.pb_dialog_member);
            Bitmap bp=getImage(folder + file, 1024);
            iv.setImageBitmap(bp);
            pb.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), "此处应该有放大的图片",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /*
    启动相机
    */
    private void startCamera(String folder, String file) {
        if (SDUtil.isHasSdcard()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File imgFolder=new File(folder);
            if (!imgFolder.exists()) {
                imgFolder.mkdirs();
            }
            File imgFile = new File(folder, file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        }
        else {
            Toast.makeText(getActivity(), "SD卡未准备好",
                    Toast.LENGTH_SHORT).show();
        }
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
        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            switch (operationFlag) {
                case 0001:
                    ivCompanyLicense1.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_COMPANYLICENSE1));
                    ivCompanyLicense1.setTag(1);
                    break;
                case 0011:
                    ivOrganizationcode1.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_ORGANIZATIONCODE1));
                    ivOrganizationcode1.setTag(1);
                    break;
                case 0021:
                    ivOtherCertificates1.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_OTHERCERTIFICATES1));
                    ivOtherCertificates1.setTag(1);
                    ivOtherCertificates2.setVisibility(View.VISIBLE);
                    break;
                case 0022:
                    ivOtherCertificates2.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_OTHERCERTIFICATES2));
                    ivOtherCertificates2.setTag(1);
                    ivOtherCertificates3.setVisibility(View.VISIBLE);
                    break;
                case 0023:
                    ivOtherCertificates3.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_OTHERCERTIFICATES3));
                    ivOtherCertificates3.setTag(1);
                    ivOtherCertificates4.setVisibility(View.VISIBLE);
                    break;
                case 0024:
                    ivOtherCertificates4.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_OTHERCERTIFICATES4));
                    ivOtherCertificates4.setTag(1);
                    ivOtherCertificates5.setVisibility(View.VISIBLE);
                    break;
                case 0025:
                    ivOtherCertificates5.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_OTHERCERTIFICATES5));
                    ivOtherCertificates5.setTag(1);
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
