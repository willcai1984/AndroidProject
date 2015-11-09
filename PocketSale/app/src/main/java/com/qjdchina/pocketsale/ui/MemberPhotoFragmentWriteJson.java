package com.qjdchina.pocketsale.ui;

import com.qjdchina.pocketsale.dto.MemberPhotoBo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 2015/11/8.
 * 将List<MemberPhotoBo>转化为本地JsonFile
 */
public class MemberPhotoFragmentWriteJson {
    List<MemberPhotoBo> mpbs;
    File jsonFile;

    public MemberPhotoFragmentWriteJson(List<MemberPhotoBo> mpbs) {
        this.mpbs = mpbs;
    }

    public void setFilePath(String filepath) {
        jsonFile = new File(filepath);
        try {
            jsonFile.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getJsonData(String key) {
        String jsonData = null;
        //String jsonData=new JSONStringer().object().key("village").value("abc").endObject().toString();
        try {
            StringBuilder builder = new StringBuilder();
            List<String> mpbsData = new ArrayList<String>();
            JSONArray array = new JSONArray();
            for (int i = 0; i < mpbs.size(); i++) {
                MemberPhotoBo mpb = mpbs.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("auth", mpb.getAuthFile());
                jsonObject.put("local", mpb.getLocalFile());
                jsonObject.put("server", mpb.getServerFile());
                mpbsData.add(jsonObject.toString());
                array.put(jsonObject);
            }
            //JSONArray jsonArray=new JSONArray(mpbsData);
            int len = array.length();
            jsonData = new JSONStringer().object().key(key).value(array).endObject().toString();
            System.out.println(jsonData);
            writeData(jsonData);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonData;
    }

    private void writeData(String jsonData) {
        // TODO Auto-generated method stub
        try {
            BufferedReader reader = new BufferedReader(new StringReader(jsonData));
            BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile));
            int len = 0;
            char[] buffer = new char[1024];
            while ((len = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, len);
            }
            writer.flush();
            writer.close();
            reader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
