package com.wntime.advert.util;

import com.alibaba.fastjson.JSONObject;
import com.wntime.common.utils.JsonUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author ysc
 * 2020/11/9 11:05
 */
public class VideoUtil {


    public static Map<String,Object> getVideoInfo(String filePath) {
        String command = "ffprobe -v quiet -print_format json -show_streams " + filePath;

        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            JSONObject videoInfoJson = JsonUtil.String2Json(sb.toString());
            List<Map<String,Object>> streamList = (List<Map<String, Object>>) videoInfoJson.get("streams");
            if(streamList == null || streamList.isEmpty()){
                return null;
            }
            Map<String, Object> videoInfo = streamList.get(0);
            return videoInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (br != null)
            {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
