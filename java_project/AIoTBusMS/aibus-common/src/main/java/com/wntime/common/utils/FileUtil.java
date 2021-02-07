package com.wntime.common.utils;

import com.wntime.util.IDUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.Map;

public class FileUtil {

    /**
     * 将map中的参数生成配置文件
     *
     * @param fileName
     * @param filePath
     * @param params
     */
    public static R createPropertiesFile(String fileName, String filePath, Map<String, String> params) {
        if (params == null || params.size() < 0) {
            return R.error();
        }
        if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(filePath)) {
            return R.error();
        }

        //创建目录
        File dir = new File(filePath);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File f : files) {
                if (fileName.equals(f.getName())) {
                    f.delete();
                }
            }
        } else {
            dir.mkdirs();
        }

        //生成文件
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File(dir, fileName)));
            for (Map.Entry<String, String> entry : params.entrySet()) {
                bw.write(String.format("%s=%s", entry.getKey(), entry.getValue()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("文件生成错误：" + e.getMessage());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return R.ok();
    }

    public static R createFile(String fileName, String filePath, String content) {
        if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(content) || StringUtils.isEmpty(content)) {
            return R.error();
        }

        //创建目录
        File dir = new File(filePath);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File f : files) {
                if (fileName.equals(f.getName())) {
                    f.delete();
                }
            }
        } else {
            dir.mkdirs();
        }

        //生成文件
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File(dir, fileName)));
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("文件生成错误：" + e.getMessage());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return R.ok();
    }

    /**
     * 保存文件,文件以时间戳进行重命名
     *
     * @param file
     * @param path 文件路径,末尾不写分隔符
     * @return 空值则保存失败
     */
    public static String saveFile(MultipartFile file, String path) {
        if (!file.isEmpty()) {
            try {
                File filePath = new File(path);
                if (!filePath.exists()) {
                    boolean mk = filePath.mkdirs();
                    if (!mk) return null;
                }
                //获取文件名后缀
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                //文件重新命名
                String fileName = IDUtil.createTimeID() + suffix;
                //文件保存路径
                String savePath = path + File.separator + fileName;
                // 保存文件
                file.transferTo(new File(savePath));
                return fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static InputStream base64UrlToInputStream(String base64Str) {
        byte[] bytes = Base64.getDecoder().decode(base64Str);
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 将图片转换成Base64编码
     * @param imgFile 待处理图片
     * @return
     */
    public static String getImgStr(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(data);
    }

    public static void main(String[] args) {
        System.out.println(getImgStr("C:\\Users\\Buxl\\Downloads\\02.png"));
    }
}
