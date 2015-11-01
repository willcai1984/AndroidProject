package com.qjdchina.qjdsale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.qjdchina.qjdsale.ImageUtil.getImage;

public class MemberPicturePlusFragment extends Fragment implements View.OnClickListener {
    private String TAG = "MemberPictureFragment";
    private View rootView;
    private Bitmap bitmap;
    private ImageView ivCompanyLicense0;
    private ImageView ivCompanyLicense1;
    private ImageView ivCompanyLicense2;
    private ImageView ivCompanyLicense3;
    private ImageView ivCompanyLicense4;
    //organization_code
    private ImageView ivOrganizationcode1;
    //other_certificates
    private ImageView ivOtherCertificates1;
    private ImageView ivOtherCertificates2;
    private ImageView ivOtherCertificates3;
    private ImageView ivOtherCertificates4;
    private ImageView ivOtherCertificates5;
    private String operationFlag = "000";
    public static final int REQUEST_CODE_TAKE_PICTURE = 12;
    public static final String SAVE_PATH_CUSTOMER_DATA = "/sdcard/pocketsale/customerdata/"; //图片及其他数据保存文件夹
    public static final String FILE_COMPANYLICENSE0 = "COMPANYLICENSE0.png";
    public static final String FILE_COMPANYLICENSE1 = "COMPANYLICENSE1.png";
    public static final String FILE_COMPANYLICENSE2 = "COMPANYLICENSE2.png";
    public static final String FILE_COMPANYLICENSE3 = "COMPANYLICENSE3.png";
    public static final String FILE_COMPANYLICENSE4 = "COMPANYLICENSE4.png";
    public static final String FILE_ORGANIZATIONCODE1 = "ORGANIZATIONCODE1.png";
    public static final String FILE_OTHERCERTIFICATES1 = "OTHERCERTIFICATES1.png";
    public static final String FILE_OTHERCERTIFICATES2 = "OTHERCERTIFICATES2.png";
    public static final String FILE_OTHERCERTIFICATES3 = "OTHERCERTIFICATES3.png";
    public static final String FILE_OTHERCERTIFICATES4 = "OTHERCERTIFICATES4.png";
    public static final String FILE_OTHERCERTIFICATES5 = "OTHERCERTIFICATES5.png";

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
                operationFlag = "001";
                clickProcess(ivCompanyLicense0, SAVE_PATH_CUSTOMER_DATA, FILE_COMPANYLICENSE0);
                break;
            case R.id.iv_comany_license1:
                operationFlag = "002";
                clickProcess(ivCompanyLicense0, SAVE_PATH_CUSTOMER_DATA, FILE_COMPANYLICENSE1);
                break;
            case R.id.iv_comany_license2:
                operationFlag = "003";
                clickProcess(ivCompanyLicense0, SAVE_PATH_CUSTOMER_DATA, FILE_COMPANYLICENSE2);
                break;
            case R.id.iv_comany_license3:
                operationFlag = "004";
                clickProcess(ivCompanyLicense0, SAVE_PATH_CUSTOMER_DATA, FILE_COMPANYLICENSE3);
                break;
            case R.id.iv_comany_license4:
                operationFlag = "005";
                clickProcess(ivCompanyLicense0, SAVE_PATH_CUSTOMER_DATA, FILE_COMPANYLICENSE4);
                break;
            case R.id.iv_organization_code_1:
                operationFlag = "011";
                clickProcess(ivOrganizationcode1, SAVE_PATH_CUSTOMER_DATA, FILE_ORGANIZATIONCODE1);
                break;
            case R.id.iv_other_certificates_1:
                operationFlag = "021";
                clickProcess(ivOtherCertificates1, SAVE_PATH_CUSTOMER_DATA, FILE_OTHERCERTIFICATES1);
                break;
            case R.id.iv_other_certificates_2:
                operationFlag = "022";
                clickProcess(ivOtherCertificates2, SAVE_PATH_CUSTOMER_DATA, FILE_OTHERCERTIFICATES2);
                break;
            case R.id.iv_other_certificates_3:
                operationFlag = "023";
                clickProcess(ivOtherCertificates3, SAVE_PATH_CUSTOMER_DATA, FILE_OTHERCERTIFICATES3);
                break;
            case R.id.iv_other_certificates_4:
                operationFlag = "024";
                clickProcess(ivOtherCertificates4, SAVE_PATH_CUSTOMER_DATA, FILE_OTHERCERTIFICATES4);
                break;
            case R.id.iv_other_certificates_5:
                operationFlag = "025";
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

            Dialog dialog = new MemberPictureDialog(getActivity(),R.style.dialog_center);
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
        if (Utils.isHasSdcard()) {
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
                case "001":
                    ivCompanyLicense0.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_COMPANYLICENSE0));
                    ivCompanyLicense0.setTag(1);
                    ivCompanyLicense1.setVisibility(View.VISIBLE);
                    break;
                case "002":
                    ivCompanyLicense1.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_COMPANYLICENSE1));
                    ivCompanyLicense1.setTag(1);
                    ivCompanyLicense2.setVisibility(View.VISIBLE);
                    break;
                case "003":
                    ivCompanyLicense2.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_COMPANYLICENSE2));
                    ivCompanyLicense2.setTag(1);
                    ivCompanyLicense3.setVisibility(View.VISIBLE);
                    break;
                case "004":
                    ivCompanyLicense3.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_COMPANYLICENSE3));
                    ivCompanyLicense3.setTag(1);
                    ivCompanyLicense4.setVisibility(View.VISIBLE);
                    break;
                case "005":
                    ivCompanyLicense4.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_COMPANYLICENSE4));
                    ivCompanyLicense4.setTag(1);
                    break;
                case "011":
                    ivOrganizationcode1.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_ORGANIZATIONCODE1));
                    ivOrganizationcode1.setTag(1);
                    break;
                case "021":
                    ivOtherCertificates1.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_OTHERCERTIFICATES1));
                    ivOtherCertificates1.setTag(1);
                    break;
                case "022":
                    ivOtherCertificates2.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_OTHERCERTIFICATES2));
                    ivOtherCertificates2.setTag(1);
                    break;
                case "023":
                    ivOtherCertificates3.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_OTHERCERTIFICATES3));
                    ivOtherCertificates3.setTag(1);
                    break;
                case "024":
                    ivOtherCertificates4.setImageBitmap(getImage(SAVE_PATH_CUSTOMER_DATA + FILE_OTHERCERTIFICATES4));
                    ivOtherCertificates4.setTag(1);
                    break;
                case "025":
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
