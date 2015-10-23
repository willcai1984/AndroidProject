package com.qjdchina.qjdsale;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MemberMarkFragment extends Fragment implements View.OnClickListener {
    private TextView tvCompanySizeMark;
    private Spinner spCompanySize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member_mark, container, false);
        tvCompanySizeMark = (TextView) rootView.findViewById(R.id.tv_company_size_mark);
        spCompanySize = (Spinner) rootView.findViewById(R.id.sp_company_size);
        rootView.findViewById(R.id.btn_judge).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_judge:
                this.getSelectionAndSetMark();
                break;
        }
    }

    public void getSelectionAndSetMark(){
        //getSelectedItem() 选项内容本身
        //getSelectedItemId() 选了第几个，从0开始
        //getSelectedItemPosition() 类似与上，不过是int型

        switch (spCompanySize.getSelectedItemPosition()){
            case 0:
                tvCompanySizeMark.setText("10分");
                break;
            case 1:
                tvCompanySizeMark.setText("20分");
                break;
            case 2:
                tvCompanySizeMark.setText("30分");
                break;
            case 3:
                tvCompanySizeMark.setText("40分");
                break;
        }

    }
}
