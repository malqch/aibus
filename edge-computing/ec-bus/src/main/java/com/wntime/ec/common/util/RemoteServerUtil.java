package com.wntime.ec.common.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wntime.ec.common.model.Constant;
import com.wntime.ec.common.model.FsmsRsp;
import com.wntime.ec.common.util.exception.BusinessException;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * @author wing
 * @create 2019-12-12 15:50
 * 调用服务端api工具类
 */
public class RemoteServerUtil {


    /**
     * 调用远程服务端 body
     */
    public static FsmsRsp post(String apiUrl, Object param, Class<? extends FsmsRsp> resultType) {
        FsmsRsp result = null;
        String resp = null;
        try {
            if(CommonUtil.isEmpty(param)){
                param = "";
            }
            resp = HttpUtil.post(apiUrl, JSONUtil.toJsonStr(param),1000 * 10);
            result = JSONUtil.toBean(resp, resultType);
        } catch (Exception e) {
            result = new FsmsRsp(500, "call remote fsms server error:" + apiUrl + ":" + param + resp, e.getMessage());
        }
        return result;
    }

    /**
     * 调用远程服务端 form表单提交
     */
    public static FsmsRsp form(String apiUrl, Map param, Class<? extends FsmsRsp> resultType) {
        FsmsRsp result = null;
        String resp = null;
        try {
            resp = HttpUtil.post(apiUrl, param,1000 * 30);
            result = JSONUtil.toBean(resp, resultType);
        } catch (Exception e) {
            result = new FsmsRsp(500, "call remote fsms server error:" + apiUrl + ":" + param + resp, e.getMessage());
        }
        return result;
    }

    /**
     * 从远程服务端下载图片
     * @param imageUrl
     * @param formatName
     * @param localFile
     * @return
     */
    public static boolean downloadImage(String imageUrl, String localFile, String formatName) {
        boolean isSuccess = false;
        URL url = null;
        try {
            url = new URL(imageUrl);
            isSuccess = ImageIO.write(ImageIO.read(url), formatName, new File(localFile));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 从远程服务端下载图片
     * @param imageUrl
     * @param localFile
     * @return
     */
    public static boolean downloadImage(String imageUrl, String localFile) {
        String suffix;
        try {
            int index = imageUrl.lastIndexOf(".");
            suffix = imageUrl.substring(index + 1).toLowerCase();
            if(!Constant.ImgType.contails(suffix)){
                throw new BusinessException("img suffix not support");
            }
        }catch (Exception e){
            e.printStackTrace();
            suffix = "png";
        }

        return downloadImage(imageUrl, localFile, suffix);
    }












    public static void main(String[] args) {
//        String baiduLogoUrl = "https://www.baidu.com/img/bd_logo1.png";
        String baiduLogoUrl = "http://192.168.3.88:8080/v1/log/sceneImg/2904";
//        downloadImage(baiduLogoUrl,  "G:/bd_logo.png");
        HttpUtil.downloadFile(baiduLogoUrl, new File("g:/aaaxxx.png"));
//        String s = HttpUtil.downloadString(baiduLogoUrl, Charset.defaultCharset());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpUtil.download(baiduLogoUrl, out, true);
        byte[] bytes = out.toByteArray();
        String s = Base64.encodeBase64String(bytes);
        System.out.println(s);


    }
}
