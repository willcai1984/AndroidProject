package com.qjdchina.qjdsale;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Will on 2015/10/26.
 */
public class MemberPictureDialog extends Dialog {
    public MemberPictureDialog(Context context) {
        super(context);
    }

    public MemberPictureDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MemberPictureDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_member_picture);
    }
}
