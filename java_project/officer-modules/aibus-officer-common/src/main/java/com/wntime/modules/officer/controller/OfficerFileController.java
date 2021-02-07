package com.wntime.modules.officer.controller;

import cn.hutool.http.ContentType;
import com.wntime.common.MultFileUtils;
import com.wntime.common.utils.R;
import com.wntime.modules.officer.service.StudentService;
import com.wntime.util.DateUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Controller
@Api(value = "人员类上传接口", tags = {"上传类"})
@RequestMapping("officer")
public class OfficerFileController {

    @Value("${wntime.officer.upload.filePath}")
    private String filePath;
    @Value("${wntime.officer.upload.fileUrl}")
    private String fileUrl;

    @Value("${wntime.officer.upload.imagePath}")
    private String imagePath;
    @Value("${wntime.officer.upload.imageUrl}")
    private String imageUrl;

    @Autowired
    private StudentService studentService;


    @RequestMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,method = RequestMethod.POST)
    @ApiOperation(value = "上传文件", notes = "上传文件", tags = {"表格", "照片压缩包"}, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",
                    paramType = "query",
                    dataType = "string",
                    value = "文件类型(take,noCriminal)",
                    required = true)
    })
    @ResponseBody
    public R upload(@ApiParam(value = "上传", required = true) @RequestParam("filename") MultipartFile file, String type) throws Exception {
        String filename = file.getOriginalFilename();
        String dateStr = DateUtil.date2String(new Date(), DateUtil.yyyyMMddHHmmss);
        long aLong = Long.parseLong(dateStr);
        String path = File.separatorChar
                + type + File.separatorChar
                + Long.toString(aLong, 36)
                + File.separatorChar;
        File save = MultFileUtils.save(file, filePath + path);

        return R.ok().put("url", path + filename);
    }

}
