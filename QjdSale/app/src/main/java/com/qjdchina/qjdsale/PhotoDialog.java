package com.qjdchina.qjdsale;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Will on 2015/10/26.
 */
public class PhotoDialog extends Dialog {

    private boolean mCanceledOnTouchOutside = true;
    private boolean mIsMenu;

    protected PhotoDialog(Context context, int theme) {
        super(context, theme);
    }

    protected PhotoDialog(Context context, boolean cancelable,
                          OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected PhotoDialog(Context context) {
        this(context, R.style.dialog_photo);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 捕获系统菜单键响应事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mIsMenu) {
            cancel();
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (mCanceledOnTouchOutside
                && event.getAction() == MotionEvent.ACTION_DOWN
                && isOutOfBounds(event)) {
            cancel();
            return true;
        }
        return false;
    }

    private boolean isOutOfBounds(MotionEvent event) {
//        final int x = (int) event.getX();
//        final int y = (int) event.getY();
//        final View contentView = findViewById(R.id.content);
//        return (x < contentView.getLeft()) || (y < contentView.getTop())
//                || (x > contentView.getRight()) || (y > contentView.getBottom());
        return true;
    }

    public static class Builder {
        private PhotoDialog pDialog;
        private LinearLayout mContent;
        private View customView;
        public Builder(Context context) {
            pDialog = new PhotoDialog(context);
            pDialog.setContentView(R.layout.dialog_photo);
            WindowManager.LayoutParams params = pDialog.getWindow().getAttributes();
            //params.width = ViewGroup.LayoutParams.FILL_PARENT;
            //params.height = ViewGroup.LayoutParams.FILL_PARENT;
            pDialog.getWindow().setAttributes(params);
            //mContent = (LinearLayout) pDialog.findViewById(R.id.content);
            pDialog.setCanceledOnTouchOutside(true);
        }
        public void setOnCancelListener(OnCancelListener listener) {
            pDialog.setOnCancelListener(listener);
        }
        public void setCanceledOnTouchOutside(boolean flag) {
            pDialog.mCanceledOnTouchOutside = flag;
        }
        public void setOnDismissListener(OnDismissListener listener) {
            pDialog.setOnDismissListener(listener);
        }
        public void dismiss() {
            pDialog.dismiss();
        }
        public void show() {
            if (customView != null) {
                mContent.addView(customView);
            }
            pDialog.show();
        }
        public void setView(View view) {
            customView = view;
        }
        public void setIsMenu(boolean isMenu) {
            pDialog.mIsMenu = isMenu;
        }
    }
}
