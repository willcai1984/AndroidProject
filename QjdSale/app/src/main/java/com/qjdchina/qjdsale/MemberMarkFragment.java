package com.qjdchina.qjdsale;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MemberMarkFragment extends Fragment implements View.OnClickListener {

    private String[] experiences = {"2年以下", "2-5年", "5-10年", "10年以上"};
    private LinearLayout llExperience;
    private TextView tvExperience;
    private int indexExperience;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member_mark, container, false);
        llExperience = (LinearLayout) rootView.findViewById(R.id.ll_experience);
        tvExperience = (TextView) rootView.findViewById(R.id.tv_experience_option);
        llExperience.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_experience:
                onClickCategory();
                break;
        }
    }

    private void onClickCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(experiences, indexExperience, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                indexExperience = which;
                tvExperience.setText(experiences[indexExperience]);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog alert = builder.create();
        alert.show();
    }


}
