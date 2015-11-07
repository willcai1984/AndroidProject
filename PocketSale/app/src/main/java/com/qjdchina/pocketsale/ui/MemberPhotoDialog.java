package com.qjdchina.pocketsale.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

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
}
