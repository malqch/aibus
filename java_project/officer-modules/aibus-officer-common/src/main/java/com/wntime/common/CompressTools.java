package com.wntime.common;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ZipUtil;
import de.innosystec.unrar.Archive;
import de.innosystec.unrar.NativeStorage;
import de.innosystec.unrar.rarfile.FileHeader;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;


public class CompressTools {
    private static Logger logger = LoggerFactory.getLogger(CompressTools.class);

    public static void unrar(String sourceRarPath, String destDirPath) throws Exception {
        File sourceRar = new File(sourceRarPath);
        unrar(sourceRar, destDirPath);
    }

    public static void unrar(File sourceRar, String destDirPath) throws Exception {
        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        Archive archive = null;
        FileOutputStream fos = null;
        System.out.println("Starting 开始解压...");
        try {
            archive = new Archive(new NativeStorage(sourceRar));
            List<FileHeader> headers = archive.getFileHeaders();
            for (FileHeader header : headers) {
                if (!header.isDirectory()) {
                    String cfn = "";
                    if (StringUtils.isNotBlank(header.getFileNameW())) {
                        cfn = FilenameUtils.separatorsToSystem(header.getFileNameW());
                    } else {
                        cfn = FilenameUtils.separatorsToSystem(header.getFileNameString());
                    }
                    String path = FilenameUtils.getPathNoEndSeparator(cfn);
                    String dest = destDirPath + File.separatorChar + path;
                    File d = new File(dest);
                    if (!d.exists()) {
                        d.mkdirs();
                    }
                    String name = FilenameUtils.getBaseName(cfn);
                    String extension = FilenameUtils.getExtension(cfn);
                    File file = new File(dest + File.separatorChar + name + "." + extension);
                    fos = new FileOutputStream(file);
                    archive.extractFile(header, fos);
                }
            }
            System.out.println("Finished 解压完成!");
        } catch (Exception e) {
            throw e;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (Exception e) {
                }
            }
            if (archive != null) {
                try {
                    archive.close();
                    archive = null;
                } catch (Exception e) {
                }
            }
        }
    }

    public static void unzip(File zipFile, String descDir) {
        try (ZipArchiveInputStream inputStream = getZipFile(zipFile)) {
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            ZipArchiveEntry entry = null;
            while ((entry = inputStream.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = new File(descDir, entry.getName());
                    directory.mkdirs();
                } else if (!entry.getName().contains("__MACOSX") && !entry.isUnixSymlink()) {
                    OutputStream os = null;
                    try {
                        os = new BufferedOutputStream(new FileOutputStream(new File(descDir, entry.getName())));
                        //输出文件路径信息
                        logger.info("解压文件的当前路径为:{}", descDir + entry.getName());
                        IOUtils.copy(inputStream, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
            final File[] files = pathFile.listFiles();
            if (files != null && files.length == 1 && files[0].isDirectory()) {
                // 说明只有一个文件夹
                FileUtils.copyDirectory(files[0], pathFile);
                //免得删除错误， 删除的文件必须在/data/demand/目录下。
                boolean isValid = files[0].getPath().contains("/data/www/");
                if (isValid) {
                    FileUtils.forceDelete(files[0]);
                }
            }
            logger.info("******************解压完毕********************");

        } catch (Exception e) {
            logger.error("[unzip] 解压zip文件出错", e);
        }
    }


    /**
     * 解压Zip文件
     *
     * @param zipFileName  需要解压缩的文件位置
     * @param descFileName 将文件解压到某个路径
     * @throws IOException
     */
    @Deprecated
    public static void unZip(String zipFileName, String descFileName) throws IOException {
        System.out.println("文件解压开始...");
//        ZipFile file = new ZipFile(zipFileName);
        ZipUtil.unzip(zipFileName, descFileName, CharsetUtil.CHARSET_GBK);
        System.out.println("文件解压结束...");
    }

    private static ZipArchiveInputStream getZipFile(File zipFile) throws Exception {
        return new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
    }
}
