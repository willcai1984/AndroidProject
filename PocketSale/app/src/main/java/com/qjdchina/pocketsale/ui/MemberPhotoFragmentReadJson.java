package com.qjdchina.pocketsale.ui;

import com.qjdchina.pocketsale.commons.enums.MemberAuthFile;
import com.qjdchina.pocketsale.dto.MemberPhotoBo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 2015/11/8.
 * 读取本地json File 并转化成List<MemberPhotoBo>
 */
public class MemberPhotoFragmentReadJson {
    String jsonData;
    //  ArrayList<Folk> folks;
    public MemberPhotoFragmentReadJson(String filePath){

        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(filePath);
            InputStream in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                sb.append((char) tempbyte);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.jsonData=sb.toString();
    }

    public List<MemberPhotoBo> getJsonData(String key){
        List<MemberPhotoBo> folks=new ArrayList<MemberPhotoBo>();
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONArray jsonArray=jsonObject.getJSONArray(key);
            int len = jsonArray.length();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject json=jsonArray.getJSONObject(i);
                MemberPhotoBo folk=new MemberPhotoBo();
                //TODO enums可能会有问题
                folk.setAuthFile((MemberAuthFile) json.opt("auth"));
                folk.setLocalFile(json.optString("local"));
                folk.setServerFile(json.optString("server"));
                folks.add(folk);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return folks;
    }
}
