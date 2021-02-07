package com.wntime.advert.util;

import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * @author ysc
 * 2020/11/6 11:05
 */
@Component
public class FileHelper {

    @Value("${wntime.advert.upload.file-path}")
    private String filePath;

    @Value("${wntime.advert.upload.file-resource-path}")
    private String fileResourcePath;

    @Value("${wntime.advert.upload.file-url}")
    private String fileUrl;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取存储目录的相对路径 (后面拼接文件后存储在数据库)
     * @param userId
     * @param advertNo
     * @return
     */
    public String getRelativeDir(Long userId, String advertNo) {
        return userId + "/" + advertNo;
    }

    /**
     * 获取url路径
     * @param relativeFilePath 数据库存储的路径
     * @return
     */
    public String getUrl(String relativeFilePath) {
        return fileUrl + "/" + relativeFilePath;
    }

    /**
     * 获取硬盘上的路径
     * @param relativeFilePath 数据库存储的路径
     * @return
     */
    public String getAbsoluteFilePath(String relativeFilePath) {
        return filePath + "/" + relativeFilePath;
    }

    /**
     * 获取硬盘上的文件
     * @param relativeFilePath 数据库存储的路径
     * @return
     */
    public File getDiskFile(String relativeFilePath) {
        return new File(filePath + "/" + relativeFilePath);
    }

    /**
     * 保存广告素材文件，并返回相对路径
     * @param file
     * @param userId
     * @param advertNo
     * @return
     */
    public String saveFile(MultipartFile file, Long userId,String advertNo) {
        return saveFile(file,getRelativeDir(userId, advertNo));
    }
    public String saveFile(MultipartFile file, String relativePath) {
        String fileName = FileUtil.saveFile(file, getAbsoluteFilePath(relativePath));
        if(fileName == null){
            return null;
        }
        return fileName;
    }

    public boolean deleteFile(String relativeFilePath) {
        File file = new File(getAbsoluteFilePath(relativeFilePath));
        if (file.exists()) {
            return file.delete();
        }
        //没有对应文件的时候，认为已经删除了
        return true;
    }

    public boolean deleteDir(String relativeFilePath) {
        File dir = new File(getAbsoluteFilePath(relativeFilePath));
        return deleteDir(dir);
    }

    public boolean deleteDir(File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
            if(dir.delete()) {
                log.info(dir.getAbsolutePath() + "目录已被删除！");
                return true;
            } else {
                log.info(dir.getAbsolutePath() + "目录删除失败！");
                return false;
            }
        }
        //没有对应文件的时候，认为已经删除了
        return true;
    }
}
