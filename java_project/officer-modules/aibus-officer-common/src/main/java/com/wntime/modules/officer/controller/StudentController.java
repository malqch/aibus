package com.wntime.modules.officer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.utils.ShiroUtils;
import com.wntime.controller.AbstractController;
import com.wntime.modules.officer.dto.StudentInfoDto;
import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;
import com.wntime.modules.officer.entity.StudentEntity;
import com.wntime.modules.officer.from.StudentFrom;
import com.wntime.modules.officer.service.PeopleBasicFactsService;
import com.wntime.modules.officer.service.StudentService;
import com.wntime.modules.officer.vo.DeleteVo;
import com.wntime.modules.officer.vo.LineStationVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author raute
 * @desc 学生信息表
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@RestController
@RequestMapping("officer/student")
@Api(value = "学生信息接口", tags = {"学生信息接口"})
public class StudentController extends AbstractController {
    @Autowired
    private StudentService studentService;

    @Autowired
    PeopleBasicFactsService factsService;


    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("officer:student:list")
    @ApiOperation(value = "学生列表接口", httpMethod = "POST")
    public R list(@RequestBody(required = false) Map<String, Object> params) {
        PageUtils page = studentService.queryPage(params);
//        List<Map<String, Object>> result = new ArrayList<>();
//        for (Object o : page.getList()) {
//            StudentEntity entity = (StudentEntity) o;
//            Map<String, Object> d = BeanUtil.beanToMap(o);
//            PeopleBasicFactsEntity factsEntity = factsService.getById(entity.getBasicId());
//            Map<String, Object> map = BeanUtil.beanToMap(factsEntity);
//            map.putAll(d);
//            result.add(map);
//        }
//        page.setList(result);
        return R.ok().put("page", page);
    }

    @GetMapping("/station/{lineId}")
    @ApiOperation(value = "学生站点接口", httpMethod = "GET")
    public R station(@PathVariable Long lineId) {
        Set<LineStationVO> station = studentService.getLineStation(lineId);
        return R.ok().put("data", station);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("officer:student:info")
    @ApiOperation(value = "学生详情接口", httpMethod = "GET")
    public R info(@PathVariable("id") Long id) {
        StudentInfoDto student = studentService.getStudentInfoById(id);
        //PeopleBasicFactsEntity factsEntity = factsService.getById(student.getBasicId());
//        Map<String, Object> merge = BeanMerged.merge(student, factsEntity);

        return R.ok().put("data", student);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("officer:student:save")
    @ApiOperation(value = "学生保存接口", httpMethod = "POST")
    public R save(@RequestBody StudentFrom student) {
        PeopleBasicFactsEntity facts = student.getPeopleBasicFacts();
        UpdateWrapper<PeopleBasicFactsEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id_no", facts.getIdNo());
        factsService.saveOrUpdate(facts, updateWrapper);
        facts = factsService.getOne(new QueryWrapper<>(facts));
        StudentEntity entity = student.getStudent();
        entity.setBasicId(facts.getId());
        studentService.save(entity);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("officer:student:update")
    @ApiOperation(value = "学生更新接口", httpMethod = "POST")
    public R update(@RequestBody StudentInfoDto student) {
        studentService.updateStudentInfo(student);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{id}")
//    @RequiresPermissions("officer:student:delete")
    @ApiOperation(value = "学生删除接口", httpMethod = "POST")
    public R delete(@PathVariable Long[] ids) {
        studentService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/deleteBatch")
//    @RequiresPermissions("officer:student:delete")
    @ApiOperation(value = "学生删除接口", httpMethod = "POST")
    public R deleteBatch(@RequestBody DeleteVo vo) {
        if (!validatePassword(vo.getValidatePassword())) {
            return R.error("用户密码错误");
        }

        studentService.removeByIds(Arrays.asList(vo.getIds()));
        return R.ok();
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传表格或照片压缩包", notes = "上传表格或照片压缩包", tags = {"表格", "照片压缩包"}, httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", paramType = "query", dataType = "string", value = "文件分类(excel|zip|rar)", required = true)
    })
    public R upload(String type,
                    @ApiParam(value = "上传", required = true) MultipartFile attach,
                    @RequestParam Long classId) throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        if (type.equals("excel")) {
            Long schoolId = ShiroUtils.getUserEntity().getAreaOrgIds().get(0);
            result = studentService.importInfo(attach, schoolId, classId);
            return R.ok(result);
        } else if (type.equals("zip") || type.equals("rar")) {
            result = studentService.importCompress(attach, type);
            return R.ok(result);
        }
        return R.error("文件分类为(excel|zip|rar)");
    }

    @Value(value = "${path.upload.file}")
    private String filePath;

    @RequestMapping(path = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation(value = "查看图片", notes = "上传表格或照片压缩包", tags = {"表格", "照片压缩包"}, httpMethod = "GET")
    public void getImage(@PathVariable Long id, HttpServletResponse response) throws Exception {
        StudentEntity studentEntity = studentService.getById(id);
        Long basicId = studentEntity.getBasicId();
        PeopleBasicFactsEntity factsEntity = factsService.getById(basicId);
        String takePhoto = factsEntity.getTakePhoto();
        BufferedImage image = ImageIO.read(new File(filePath + takePhoto));
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }
}
