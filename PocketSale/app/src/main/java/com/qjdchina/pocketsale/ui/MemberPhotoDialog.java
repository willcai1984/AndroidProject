package com.qjdchina.pocketsale.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.qjdchina.pocketsale.R;

/**
 * Created by Will on 2015/10/26.
 */
public class MemberPhotoDialog extends Dialog {
    public MemberPhotoDialog(Context context) {
        super(context);
    }

    public MemberPhotoDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MemberPhotoDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_member_photo);
    }

    public void setRatio(){
//        Window dialogWindow = dialog.getWindow();
//        WindowManager m = getActivity().getWindowManager();
//        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.9
//        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.9
//        dialogWindow.setAttributes(p);
    }
}
