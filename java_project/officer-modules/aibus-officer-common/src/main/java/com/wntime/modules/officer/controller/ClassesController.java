package com.wntime.modules.officer.controller;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.utils.ShiroUtils;
import com.wntime.controller.AbstractController;
import com.wntime.modules.officer.entity.ClassesEntity;
import com.wntime.modules.officer.service.ClassesService;
import com.wntime.modules.officer.vo.DeleteVo;
import com.wntime.modules.sys.service.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * @author raute
 * @desc 班级信息
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@RestController
@RequestMapping("officer/classes")
@Api(value = "班级接口", tags = {"班级接口"})
public class ClassesController extends AbstractController {
    @Autowired
    private ClassesService classesService;

    @Autowired
    AdminUserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("officer:classes:list")
    @ApiOperation(value = "班级列表接口", httpMethod = "POST")
    public R list(@RequestBody(required = false) Map<String, Object> params) {

        PageUtils page = classesService.queryPage(params);

        for (Object o : page.getList()) {
            ClassesEntity entity = (ClassesEntity) o;
            entity.setCreateUserName(userService.getById(entity.getCreateUserId()).getUserName());
            if (null != entity.getModifyUserId()) {
                entity.setModifiedUserName(userService.getById(entity.getModifyUserId()).getUserName());
            }
        }

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("officer:classes:info")
    @ApiOperation(value = "班级详情接口", httpMethod = "GET")
    public R info(@PathVariable("id") Long id) {
        ClassesEntity entity = classesService.getById(id);
        entity.setCreateUserName(userService.getById(entity.getCreateUserId()).getUserName());
        if (null != entity.getModifyUserId()) {
            entity.setModifiedUserName(userService.getById(entity.getModifyUserId()).getUserName());
        }
        return R.ok().put("data", entity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("officer:classes:save")
    @ApiOperation(value = "班级接口,保存接口", httpMethod = "POST")
    public R save(@RequestBody ClassesEntity classes) {
        classes.setId(null);
        classes.setCreateUserId(getUserId());
        classes.setCreateDt(new Date());
        try {
            classes.setSchoolId(getUser().getAreaOrgIds().get(0));
        }catch (Exception e){

            return R.error("session 超时,请重新登录!");
        }
        classesService.save(classes);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("officer:classes:update")
    @ApiOperation(value = "班级接口,更新接口", httpMethod = "GET")
    public R update(@RequestBody ClassesEntity classes) {
        classes.setModifyDt(new Date());
        classes.setModifyUserId(getUserId());
        classesService.updateById(classes);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("officer:classes:delete")
    @ApiOperation(value = "删除班级接口", httpMethod = "POST")
    public R delete(@RequestBody DeleteVo deleteVo) {
//        Long[] ids,String validatePassword
        //校验当前用户的密码是否正确
        if (!validatePassword(deleteVo.getValidatePassword())) {
            return R.error("密码不正确");
        }
        classesService.removeByIds(Arrays.asList(deleteVo.getIds()));

        return R.ok();
    }

    /**
     * 下载导入学生表格模板
     */
    @ApiOperation(value = "下载导入学生表格模板")
    @CrossOrigin(origins = "*")
    @RequestMapping("/download")
    public void downloadExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/import_students.xlsx");
        response.reset();
        response.setContentType("application/octet-stream;charset=utf-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode("hostModel.xls", "UTF-8"));//下载文件的名称
            response.setHeader("Access-Control-Allow-Origin", "*");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream sout = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(inputStream);
            bos = new BufferedOutputStream(sout);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();

            bos.close();
            bis.close();
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }


    }
}
