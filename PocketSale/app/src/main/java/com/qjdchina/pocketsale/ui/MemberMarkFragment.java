package com.qjdchina.pocketsale.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qjdchina.pocketsale.R;
import com.qjdchina.pocketsale.biz.MemberApplyManager;
import com.qjdchina.pocketsale.biz.impl.MemberApplyManagerImpl;
import com.qjdchina.pocketsale.commons.Constants;
import com.qjdchina.pocketsale.commons.enums.MessageType;
import com.qjdchina.pocketsale.commons.enums.ResultCodes;
import com.qjdchina.pocketsale.handler.MemberRatingQueryResponseHandler;
import com.qjdchina.pocketsale.handler.MemberRatingResponseHandler;

import java.util.HashMap;
import java.util.Map;

public class MemberMarkFragment extends Fragment implements View.OnClickListener {
    private Map<String, String> optionMarkMap;
    private String[] optionSale = {
            "年销量>=500台",
            "400台<=年销量<500台",
            "350台<=年销量<400台",
            "300台<=年销量<350台",
            "200台<=年销量<300台",
            "100台<=年销量<200台",
            "年销量<100台"};
    private LinearLayout llSale;
    private TextView tvSaleMark;
    private TextView tvSaleOption;
    private int indexSale;

    private String[] optionRegisteredCapital = {
            "注册资本>=1000万",
            "500万<=注册资本<1000万",
            "300万<=注册资本<500万",
            "100万<=注册资本<300万",
            "注册资本<100万"};
    private LinearLayout llRegisteredCapital;
    private TextView tvRegisteredCapitalMark;
    private TextView tvRegisteredCapitalOption;
    private int indexRegisteredCapital;

    private String[] optionEstablishYears = {
            "成立年限>=7年",
            "5年<=成立年限<7年",
            "3年<=成立年限<5年",
            "成立年限<3年"};
    private LinearLayout llEstablishYears;
    private TextView tvEstablishYearsMark;
    private TextView tvEstablishYearsOption;
    private int indexEstablishYears;

    private String[] optionOperatorExperience = {
            "从业年限>=10年",
            "6年<=从业年限<10年",
            "3年<=从业年限<6年",
            "从业年限<3年"};
    private LinearLayout llOperatorExperience;
    private TextView tvOperatorExperienceMark;
    private TextView tvOperatorExperienceOption;
    private int indexOperatorExperience;

    private String[] optionSize = {
            ">=30人",
            "20-29人",
            "10-19人",
            "1-10人"};
    private LinearLayout llSize;
    private TextView tvSizeMark;
    private TextView tvSizeOption;
    private int indexSize;

    private String[] optionWorth = {
            "净资产>=500万",
            "300万<=净资产<500万",
            "0<=净资产<300万",
            "有房产，但不可转让",
            "无房产或家庭净资产<0"};
    private LinearLayout llWorth;
    private TextView tvWorthMark;
    private TextView tvWorthOption;
    private int indexWorth;

    private Button btnMark;
    private Bundle bundle;
    private String companyID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member_mark, container, false);
        llSale = (LinearLayout) rootView.findViewById(R.id.ll_sale);
        llSale.setOnClickListener(this);
        tvSaleMark = (TextView) rootView.findViewById(R.id.tv_sale_mark);
        tvSaleOption = (TextView) rootView.findViewById(R.id.tv_sale_option);

        llRegisteredCapital = (LinearLayout) rootView.findViewById(R.id.ll_registered_capital);
        llRegisteredCapital.setOnClickListener(this);
        tvRegisteredCapitalMark = (TextView) rootView.findViewById(R.id.tv_registered_capital_mark);
        tvRegisteredCapitalOption = (TextView) rootView.findViewById(R.id.tv_registered_capital_option);

        llEstablishYears = (LinearLayout) rootView.findViewById(R.id.ll_establish_years);
        llEstablishYears.setOnClickListener(this);
        tvEstablishYearsMark = (TextView) rootView.findViewById(R.id.tv_establish_years_mark);
        tvEstablishYearsOption = (TextView) rootView.findViewById(R.id.tv_establish_years_option);

        llOperatorExperience = (LinearLayout) rootView.findViewById(R.id.ll_operator_experience);
        llOperatorExperience.setOnClickListener(this);
        tvOperatorExperienceMark = (TextView) rootView.findViewById(R.id.tv_operator_experience_mark);
        tvOperatorExperienceOption = (TextView) rootView.findViewById(R.id.tv_operator_experience_option);

        llSize = (LinearLayout) rootView.findViewById(R.id.ll_size);
        llSize.setOnClickListener(this);
        tvSizeMark = (TextView) rootView.findViewById(R.id.tv_size_mark);
        tvSizeOption = (TextView) rootView.findViewById(R.id.tv_size_option);

        llWorth = (LinearLayout) rootView.findViewById(R.id.ll_worth);
        llWorth.setOnClickListener(this);
        tvWorthMark = (TextView) rootView.findViewById(R.id.tv_worth_mark);
        tvWorthOption = (TextView) rootView.findViewById(R.id.tv_worth_option);

        btnMark = (Button) rootView.findViewById(R.id.btn_mark_submit);
        btnMark.setOnClickListener(this);
        Intent intent = getActivity().getIntent();
        bundle = intent.getExtras();
        companyID = bundle.getString("companyID");
        initOptionMarkMap();
        //查询并设置mark
        querySetMarkOption(companyID);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_sale:
                onClickCategory(optionSale, indexSale, tvSaleOption);
                break;
            case R.id.ll_registered_capital:
                onClickCategory(optionRegisteredCapital, indexRegisteredCapital, tvRegisteredCapitalOption);
                break;
            case R.id.ll_establish_years:
                onClickCategory(optionEstablishYears, indexEstablishYears, tvEstablishYearsOption);
                break;
            case R.id.ll_operator_experience:
                onClickCategory(optionOperatorExperience, indexOperatorExperience, tvOperatorExperienceOption);
                break;
            case R.id.ll_size:
                onClickCategory(optionSize, indexSize, tvSizeOption);
                break;
            case R.id.ll_worth:
                onClickCategory(optionWorth, indexWorth, tvWorthOption);
                break;
            case R.id.btn_mark_submit:
                if (isAllOptionValued()) {
//                    Toast.makeText(getActivity(), "所有选项都填写完毕",
//                            Toast.LENGTH_SHORT).show();
                    //先提交再查询分数
                    submitUpdateMark(companyID);
                } else {
                    Toast.makeText(getActivity(), "还有选项未填写",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private String[] options_inner;
    private int index_inner;
    private TextView tv_inner;

    /*
    *处理弹出框选项
     */
    private void onClickCategory(String[] options, int index, TextView tv) {
        options_inner = options;
        index_inner = index;
        tv_inner = tv;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(options_inner, index_inner, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                index_inner = which;
                tv_inner.setText(options_inner[index_inner]);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * 判断是否所有评分项都已评分
     */
    private boolean isAllOptionValued() {
        String sale = tvSaleOption.getText().toString();
        String size = tvSizeOption.getText().toString();
        String worth = tvWorthOption.getText().toString();
        String establish = tvEstablishYearsOption.getText().toString();
        String operator = tvOperatorExperienceOption.getText().toString();
        String register = tvRegisteredCapitalOption.getText().toString();
        if (sale.isEmpty() || size.isEmpty() || worth.isEmpty() || establish.isEmpty() || operator.isEmpty() || register.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 上传评分结果
     *
     * @param companyId
     */
    private void submitUpdateMark(String companyId) {
        // 需要在当前主线程：UI线程中启动新线程调用接口
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MessageType.RATING.getType()) {
                    Bundle bundle = msg.getData();
                    String resultCode = bundle.getString(Constants.RESULT_CODE);
                    String resultMessage = bundle.getString(Constants.RESULT_MESSAGE);
                    if (ResultCodes.SUCCESS.getCode().equals(resultCode)) {
                        String re = setMarks(bundle);
                        Toast.makeText(getActivity(), re, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "评分失败：" + resultMessage, Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        MemberRatingResponseHandler memberRatingResponseHandler = new MemberRatingResponseHandler(handler);
        MemberApplyManager memberApplyManager = new MemberApplyManagerImpl();
        Map<String, Object> params = new HashMap<String, Object>();//参数封装 评分项-评分值
        params.put(Constants.MEMBER_MARK_SALE, optionMarkMap.get(tvSaleOption.getText().toString()));
        params.put(Constants.MEMBER_MARK_CAPITAL, optionMarkMap.get(tvRegisteredCapitalOption.getText().toString()));
        params.put(Constants.MEMBER_MARK_YEARS, optionMarkMap.get(tvEstablishYearsOption.getText().toString()));
        params.put(Constants.MEMBER_MARK_EXPERIENCE, optionMarkMap.get(tvOperatorExperienceOption.getText().toString()));
        params.put(Constants.MEMBER_MARK_SIZE, optionMarkMap.get(tvSizeOption.getText().toString()));
        params.put(Constants.MEMBER_MARK_WORTH, optionMarkMap.get(tvWorthOption.getText().toString()));
        memberApplyManager.doMemberRating(companyId, params, memberRatingResponseHandler);
    }

    /**
     * 查询评分结果
     *
     * @param companyId
     */
    private void querySetMarkOption(String companyId) {
        // 需要在当前主线程：UI线程中启动新线程调用接口
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MessageType.QUERY_RATING.getType()) {
                    Bundle bundle = msg.getData();
                    String resultCode = bundle.getString(Constants.RESULT_CODE);
                    String resultMessage = bundle.getString(Constants.RESULT_MESSAGE);
                    if (ResultCodes.SUCCESS.getCode().equals(resultCode)) {
                        String re = setMarks(bundle);
                        setOptions(bundle);
                        Toast.makeText(getActivity(), re, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "查询评分失败：" + resultMessage, Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        MemberRatingQueryResponseHandler memberRatingQueryResponseHandler = new MemberRatingQueryResponseHandler(handler);
        MemberApplyManager memberApplyManager = new MemberApplyManagerImpl();
        memberApplyManager.findMemberRatingResult(companyId, memberRatingQueryResponseHandler);
    }
    //设置分数
    private String setMarks(Bundle bundle) {
        String mark_sale_score = bundle.getString(Constants.MEMBER_MARK_SALE_SCORE);
        tvSaleMark.setText(mark_sale_score);
        String mark_capital_score = bundle.getString(Constants.MEMBER_MARK_CAPITAL_SCORE);
        tvRegisteredCapitalMark.setText(mark_capital_score);
        String mark_years_score = bundle.getString(Constants.MEMBER_MARK_YEARS_SCORE);
        tvEstablishYearsMark.setText(mark_years_score);
        String mark_experience_score = bundle.getString(Constants.MEMBER_MARK_EXPERIENCE_SCORE);
        tvOperatorExperienceMark.setText(mark_experience_score);
        String mark_size_score = bundle.getString(Constants.MEMBER_MARK_SIZE_SCORE);
        tvSizeMark.setText(mark_size_score);
        String mark_worth_score = bundle.getString(Constants.MEMBER_MARK_WORTH_SCORE);
        tvWorthMark.setText(mark_worth_score);
        Integer totalScore = Integer.parseInt(mark_sale_score)
                + Integer.parseInt(mark_capital_score)
                + Integer.parseInt(mark_years_score)
                + Integer.parseInt(mark_experience_score)
                + Integer.parseInt(mark_size_score)
                + Integer.parseInt(mark_worth_score);
        return "当前客户评分为："+totalScore.toString();
    }
    /**
     * 设置所有的options
     * @param
     */
    private void setOptions(Bundle bundle) {
        setOption(bundle,Constants.MEMBER_MARK_SALE_SCORE,optionSale,tvSaleOption);
        setOption(bundle,Constants.MEMBER_MARK_CAPITAL_SCORE,optionRegisteredCapital,tvRegisteredCapitalOption);
        setOption(bundle,Constants.MEMBER_MARK_YEARS_SCORE,optionEstablishYears,tvEstablishYearsOption);
        setOption(bundle,Constants.MEMBER_MARK_EXPERIENCE_SCORE,optionOperatorExperience,tvOperatorExperienceOption);
        setOption(bundle,Constants.MEMBER_MARK_SIZE_SCORE,optionSize,tvSizeOption);
        setOption(bundle,Constants.MEMBER_MARK_WORTH_SCORE,optionWorth,tvWorthOption);
    }
    /**
     * 根据查询值设置option，如果这个选项的分=此次此项返回的分，设置为这个option
     * @param
     */
    private void setOption(Bundle bundle,String scoreEnum,String[] scoreArray,TextView tv){
        String score = bundle.getString(scoreEnum);
        for (int index = 0; index < scoreArray.length; index++) {
            if (optionMarkMap.get(scoreArray[index]).equals(score)) {
                tv.setText(scoreArray[index]);
            }
        }
    }

    private void initOptionMarkMap() {
        optionMarkMap = new HashMap<String, String>();
        optionMarkMap.put(optionSale[0], "15");
        optionMarkMap.put(optionSale[1], "14");
        optionMarkMap.put(optionSale[2], "13");
        optionMarkMap.put(optionSale[3], "12");
        optionMarkMap.put(optionSale[4], "11");
        optionMarkMap.put(optionSale[5], "10");
        optionMarkMap.put(optionSale[6], "7");
        optionMarkMap.put(optionRegisteredCapital[0], "10");
        optionMarkMap.put(optionRegisteredCapital[1], "8");
        optionMarkMap.put(optionRegisteredCapital[2], "6");
        optionMarkMap.put(optionRegisteredCapital[3], "4");
        optionMarkMap.put(optionRegisteredCapital[4], "2");
        optionMarkMap.put(optionEstablishYears[0], "5");
        optionMarkMap.put(optionEstablishYears[1], "4");
        optionMarkMap.put(optionEstablishYears[2], "3");
        optionMarkMap.put(optionEstablishYears[3], "2");
        optionMarkMap.put(optionOperatorExperience[0], "5");
        optionMarkMap.put(optionOperatorExperience[1], "4");
        optionMarkMap.put(optionOperatorExperience[2], "3");
        optionMarkMap.put(optionOperatorExperience[3], "2");
        optionMarkMap.put(optionSize[0], "10");
        optionMarkMap.put(optionSize[1], "8");
        optionMarkMap.put(optionSize[2], "6");
        optionMarkMap.put(optionSize[3], "4");
        optionMarkMap.put(optionWorth[0], "10");
        optionMarkMap.put(optionWorth[1], "8");
        optionMarkMap.put(optionWorth[2], "6");
        optionMarkMap.put(optionWorth[3], "4");
        optionMarkMap.put(optionWorth[4], "0");
    }
}