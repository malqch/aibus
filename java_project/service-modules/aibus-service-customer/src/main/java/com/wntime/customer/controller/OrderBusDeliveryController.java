package com.wntime.customer.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import com.wntime.common.exception.RRException;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.common.annotation.SysLog;
import com.wntime.controller.AbstractController;
import com.wntime.customer.entity.OrderBusCompanyEntity;
import com.wntime.service.common.form.OrderBusDeliveryBatchForm;
import com.wntime.customer.service.OrderBusCompanyService;
import com.wntime.customer.vo.OrderBusDeliveryVo;
import com.wntime.service.common.service.BusBatteryTypeInfoService;
import com.wntime.service.common.service.BusCompanyService;
import com.wntime.service.common.service.BusMotorTypeInfoService;
import com.wntime.service.common.service.BusTypeInfoService;
import com.wntime.service.common.util.UniqueCheckHelper;
import com.wntime.service.common.vo.BusInfoBatteryTypeVo;
import com.wntime.service.common.vo.BusInfoMotorTypeVo;
import com.wntime.service.common.vo.BusInfoTypeVo;
import com.wntime.service.common.vo.DeleteBatchVo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.customer.entity.OrderBusDeliveryEntity;
import com.wntime.customer.service.OrderBusDeliveryService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 车辆交付表
 *
 * @author Mark
 * @date 2020-08-25 14:00:24
 */
@RestController
@RequestMapping("customer/orderbusdelivery")
@Api(value = "车辆交付管理", tags = {"车辆交付管理"})
public class OrderBusDeliveryController extends AbstractController {
    @Autowired
    private OrderBusDeliveryService orderBusDeliveryService;

    @Autowired
    private BusBatteryTypeInfoService busBatteryInfoService;

    @Autowired
    private BusMotorTypeInfoService busMotorInfoService;

    @Autowired
    private BusTypeInfoService busTypeInfoService;

    @Autowired
    private BusCompanyService busCompanyService;

    @Autowired
    private OrderBusCompanyService orderBusCompanyService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "车辆交付表分页列表", httpMethod = "GET", notes = "车辆交付表管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("车辆交付表分页列表,参数:" + JSON.toJSONString(params));
        // 登录用户权限下公司列表
        params.put("companyIdList", busCompanyService.getUserCompanyIdList(getUserId()));
        PageUtils page = orderBusDeliveryService.queryPage(params);
        return R.ok().put("page", page);
    }

    @ApiOperation(value = "获取单条车辆交付表", httpMethod = "GET", notes = "获取单条车辆交付表")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条车辆交付表,参数ID:" + id);
        OrderBusDeliveryVo orderBusDeliveryVo;
        try {
            orderBusDeliveryVo = orderBusDeliveryService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条车辆交付表失败");
        }
        return R.ok().put("data", orderBusDeliveryVo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存车辆交付表")
    @ApiOperation(value = "添加车辆交付表", httpMethod = "POST", notes = "车辆交付表管理页面中添加车辆交付表")
    public R save(@RequestBody OrderBusDeliveryEntity orderBusDeliveryEntity) {
        logger.info("保存车辆交付表,参数:" + JSON.toJSONString(orderBusDeliveryEntity));
        ValidatorUtils.validateEntity(orderBusDeliveryEntity);
        UniqueCheckHelper.assertIsUnique(orderBusDeliveryService, "plate_code", orderBusDeliveryEntity.getPlateCode(), "车牌号已存在！");

        orderBusDeliveryService.save(orderBusDeliveryEntity, getUserId());
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改车辆交付表")
    @ApiOperation(value = "修改车辆交付表", httpMethod = "POST", notes = "车辆交付表管理页面中修改车辆交付表")
    public R update(@RequestBody OrderBusDeliveryEntity orderBusDeliveryEntity) {
        logger.info("修改车辆交付表,参数:" + JSON.toJSONString(orderBusDeliveryEntity));
        ValidatorUtils.validateEntity(orderBusDeliveryEntity);
        UniqueCheckHelper.assertIsUnique(orderBusDeliveryService, "plate_code", orderBusDeliveryEntity.getPlateCode(), "bus_delivery_id", orderBusDeliveryEntity.getBusDeliveryId(), "车牌号已存在！");

        orderBusDeliveryService.updateById(orderBusDeliveryEntity, getUserId());
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除车辆交付表", httpMethod = "POST", notes = "车辆交付表管理页面中删除车辆交付表")
    @SysLog("单条删除车辆交付表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除车辆交付表,id:" + id);
        orderBusDeliveryService.delById(id, getUserId());
        return R.ok();
    }

    @ApiOperation(value = "批量删除车辆交付表", httpMethod = "POST", notes = "批量删除车辆交付表")
    @SysLog("批量删除车辆交付表")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除车辆交付表,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            orderBusDeliveryService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!"+e.getMessage());
        }

        return R.ok("批量删除成功!");
    }

    @ApiOperation(value = "车辆批量交付导入模板下载", notes = "车辆批量交付导入模板下载", httpMethod = "GET")
    @GetMapping("/templateExcel/downLoad")
    public void getTemplateExport(HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileName = "车辆批量交付导入模板";

            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            OutputStream out = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");// 设置contentType为excel格式
            response.setHeader("Content-Disposition", "Attachment;Filename=" +
                    new String((fileName + ".xls").getBytes(), "utf-8"));
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet1 = workbook.createSheet("交通违章统计");
            //设置单元格样式
            XSSFCellStyle hssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();
            hssfCellStyle.setAlignment(HorizontalAlignment.CENTER);//居中显示
            hssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//纵向居中
            hssfCellStyle.setWrapText(true);
            Font font = workbook.createFont();
            font.setFontName("黑体");
            font.setFontHeightInPoints((short) 16);//设置字体大小
            font.setBold (true);

            // 数据行数量
            CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 12);
            sheet1.addMergedRegion(region1);

            //创建行
            Row row1 = sheet1.createRow(0);//标题
            row1.setHeight((short) (256 * 4));
            Cell cell0 = row1.createCell(0);
            cell0.setCellValue("车辆批量交付操作（说明：**需在智能公交后台系统中提前录入，" +
                    "*必填项，其他选填；从Excel第3行起，每一行数据对应一辆交付车信息）");

            //创建行 头部
            Row row = sheet1.createRow(1);
            row.setHeight((short) (256 * 3));

            //创建单元格
            Cell cell = row.createCell(0);
            cell.setCellValue("订单编码**");
            sheet1.setColumnWidth(0, 256 * 20);

            cell = row.createCell(1);
            cell.setCellValue("车型编码**");
            sheet1.setColumnWidth(1, 256 * 15);

            cell = row.createCell(2);
            cell.setCellValue("车辆VIN码*");
            sheet1.setColumnWidth(2, 256 * 20);

            cell = row.createCell(3);
            cell.setCellValue("车牌号*");
            sheet1.setColumnWidth(3, 256 * 15);

            cell = row.createCell(4);
            cell.setCellValue("车辆编码");
            sheet1.setColumnWidth(4, 256 * 20);

            cell = row.createCell(5);
            cell.setCellValue("交付日期*");
            sheet1.setColumnWidth(5, 256 * 15);

            cell = row.createCell(6);
            cell.setCellValue("交付说明");
            sheet1.setColumnWidth(6, 256 * 25);

            cell = row.createCell(7);
            cell.setCellValue("出保日期");
            sheet1.setColumnWidth(7, 256 * 15);

            cell = row.createCell(8);
            cell.setCellValue("出保说明");
            sheet1.setColumnWidth(8, 256 * 25);

            cell = row.createCell(9);
            cell.setCellValue("电机型号**");
            sheet1.setColumnWidth(9, 256 * 20);

            cell = row.createCell(10);
            cell.setCellValue("车辆电机编号*");
            sheet1.setColumnWidth(10, 256 * 25);

            cell = row.createCell(11);
            cell.setCellValue("电池型号**");
            sheet1.setColumnWidth(11, 256 * 20);

            cell = row.createCell(12);
            cell.setCellValue("车辆电池编号*\n" +
                    "（多块电池用英文逗号,分隔）");
            sheet1.setColumnWidth(12, 256 * 30);

            cell.setCellStyle(hssfCellStyle);//设置单元格的文本居中显示

            // 默认数据 第2行
            Row rows2 = sheet1.createRow(2);
            rows2.setHeight((short) (256 * 3));
            Cell cells = rows2.createCell(0);
            cells.setCellValue("order_bus_2");//设置第一行第一格的值

            cells = rows2.createCell(1);
            cells.setCellValue("JYB6850BEV01");

            cells = rows2.createCell(2);
            cells.setCellValue("HYBABA247L1000014");

            cells = rows2.createCell(3);
            cells.setCellValue("吉H-07700D");

            cells = rows2.createCell(4);
            cells.setCellValue("202009001");

            cells = rows2.createCell(5);
            cells.setCellValue("2020/9/1");

            cells = rows2.createCell(6);
            cells.setCellValue("9月交付普通公交车1辆");

            cells = rows2.createCell(7);
            cells.setCellValue("2025/9/1");

            cells = rows2.createCell(8);
            cells.setCellValue("质保年限5年");

            cells = rows2.createCell(9);
            cells.setCellValue("DJ-Motor-M300");

            cells = rows2.createCell(10);
            cells.setCellValue("Motor-20201001");

            cells = rows2.createCell(11);
            cells.setCellValue("DC-Battery-B300");

            cells = rows2.createCell(12);
            cells.setCellValue("2020B001,2020B002,2020B003,2020B004,2020B005,2020B006,2020B007,2020B008");
            cells.setCellStyle(hssfCellStyle);

            // 默认数据 第3行
            Row rows3 = sheet1.createRow(3);
            rows3.setHeight((short) (256 * 3));
            Cell cell1 = rows3.createCell(0);
            cell1.setCellValue("order_bus_2");//设置第一行第一格的值

            cell1 = rows3.createCell(1);
            cell1.setCellValue("JYB1030BEV");

            cell1 = rows3.createCell(2);
            cell1.setCellValue("HYBAAA213L1010002");

            cell1 = rows3.createCell(3);
            cell1.setCellValue("吉H-0WL11D");

            cell1 = rows3.createCell(4);
            cell1.setCellValue("202009101");

            cell1 = rows3.createCell(5);
            cell1.setCellValue("2020/9/1");

            cell1 = rows3.createCell(6);
            cell1.setCellValue("9月交付小型电动车1辆");

            cell1 = rows3.createCell(7);

            cell1 = rows3.createCell(8);

            cell1 = rows3.createCell(9);
            cell1.setCellValue("DJ-Motor-MS100");

            cell1 = rows3.createCell(10);
            cell1.setCellValue("MotorMin-20209002");

            cell1 = rows3.createCell(11);
            cell1.setCellValue("DC-BatteryMin-BS300");

            cell1 = rows3.createCell(12);
            cell1.setCellValue("2020BS211,2020BS212,2020B213,2020B214");
            cell1.setCellStyle(hssfCellStyle);
            // 输出
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("文件导出异常", e);
        }
    }

    @ApiOperation(value = "车辆批量交付导入模板上传", notes = "车辆批量交付导入模板上传", httpMethod = "GET")
    @PostMapping("/templateExcel/upLoad")
    @CrossOrigin
    public R uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty() || file.getSize() == 0) {
                return R.error("上传Excel文件不能为空");
            }
            String fName = file.getOriginalFilename();//获取文件名（包括后缀）

            //创建Excel工作薄
            Workbook workbook = null;
            String fileType = fName.substring(fName.lastIndexOf("."));
            if (!".xls".equals(fileType) && !".xlsx".equals(fileType)) {
                return R.error("请选择上传Excel文件");
            }

//            if(POIFSFileSystem.hasPOIFSHeader(file.getInputStream())) {
//                workbook = new HSSFWorkbook(file.getInputStream());
//            }
//            if(POIXMLDocument.hasOOXMLHeader(file.getInputStream())) {
//                workbook = new XSSFWorkbook(OPCPackage.open(file.getInputStream()));
//            }

            if (".xls".equals(fileType)) {
                workbook = WorkbookFactory.create(file.getInputStream());
            } else if (".xlsx".equals(fileType)) {
                workbook = WorkbookFactory.create(file.getInputStream());
            } else {
                return R.error("请选择上传Excel文件");
            }
            if (null == workbook) {
                return R.error("创建上传Excel工作薄为空");
            }
            Sheet sheet = workbook.getSheetAt(0);
            Row row = null;
            Cell cell = null;
            List<OrderBusDeliveryBatchForm> list = new ArrayList<>();
            // 第三行开始，获取每一行数据
            Long userId = getUserId();
            int index = 0;
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                index = i + 1;
                OrderBusDeliveryBatchForm data = new OrderBusDeliveryBatchForm();
                // 前2列为空
                if (row == null || ((row.getCell(0) == null || StringUtils.isEmpty(row.getCell(0).getStringCellValue()))
                        && (row.getCell(1) == null || StringUtils.isEmpty(row.getCell(1).getStringCellValue())))) {
                    continue;
                }
                // 订单编号
                if (row.getCell(0) == null) {
                    return R.error("第" + index + "行[订单编码]不能为空!");
                } else {
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    if (!StringUtils.isEmpty(row.getCell(0).getStringCellValue())) {
                        // 检查是否存在
                        OrderBusCompanyEntity orderBusCompanyEntity = orderBusCompanyService.getDetailByOrderCode(
                                String.valueOf(row.getCell(0).getStringCellValue()));
                        if (orderBusCompanyEntity == null) {
                            return R.error("根据第" + index + "行[订单编码]未查询到该订单信息!");
                        }
                        data.setOrderId(orderBusCompanyEntity.getOrderId());
                        data.setOrderCode(String.valueOf(row.getCell(0).getStringCellValue()));
                    } else {
                        return R.error("第" + index + "行[订单编码]不能为空!");
                    }
                }

                // 车型编码
                if (row.getCell(1) == null) {
                    return R.error("第" + index + "行[车型编码]不能为空!");
                } else {
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    if (!StringUtils.isEmpty(row.getCell(1).getStringCellValue())) {
                        // 检查是否存在
                        BusInfoTypeVo busInfoTypeVo = busTypeInfoService.queryByCode(
                                String.valueOf(row.getCell(1).getStringCellValue()));
                        if (busInfoTypeVo == null) {
                            return R.error("根据第" + index + "行[车型编码]未查询到该公交车型信息!");
                        }
                        data.setBusTypeId(busInfoTypeVo.getBusTypeId());
                        data.setBusTypeCode(String.valueOf(row.getCell(1).getStringCellValue()));
                    } else {
                        return R.error("第" + index + "行[车型编码]不能为空!");
                    }

                }

                // 车辆VIN码
                if (row.getCell(2) == null) {
                    return R.error("第" + index + "行[车辆VIN码]不能为空!");
                } else {
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    if (!StringUtils.isEmpty(row.getCell(2).getStringCellValue())) {
                        data.setVinCode(String.valueOf(row.getCell(2).getStringCellValue()));
                    } else {
                        return R.error("第" + index + "行[车辆VIN码]不能为空!");
                    }
                }

                // 车牌号
                if (row.getCell(3) == null) {
                    return R.error("第" + index + "行[车牌号]不能为空!");
                } else {
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    if (!StringUtils.isEmpty(row.getCell(3).getStringCellValue())) {
                        data.setPlateCode(String.valueOf(row.getCell(3).getStringCellValue()));
                    } else {
                        return R.error("第" + index + "行[车牌号]不能为空!");
                    }
                }

                // 车辆编码
                if (row.getCell(4) == null) {
                } else {
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    if (!StringUtils.isEmpty(row.getCell(4).getStringCellValue())) {
                        data.setBusCode(String.valueOf(row.getCell(4).getStringCellValue()));
                    }
                }

                // 交付日期
                if (row.getCell(5) == null || row.getCell(5).getDateCellValue() == null) {
                    return R.error("第" + index + "行[交付日期]不能为空!");
                } else {
                    Date date = row.getCell(5).getDateCellValue();
                    data.setOrderDeliveryDate(new Timestamp(date.getTime()));
                }

                // 交付说明
                if (row.getCell(6) == null) {
                } else {
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    if (!StringUtils.isEmpty(row.getCell(6).getStringCellValue())) {
                        data.setOrderDeliveryDesc(String.valueOf(row.getCell(6).getStringCellValue()));
                    }
                }

                // 出保日期
                if (row.getCell(7) == null || row.getCell(7).getDateCellValue() == null) {
                } else {
                    Date date = row.getCell(7).getDateCellValue();
                    data.setOrderOutDate(new Timestamp(date.getTime()));
                }

                // 出保说明
                if (row.getCell(8) == null) {
                } else {
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    if (!StringUtils.isEmpty(row.getCell(8).getStringCellValue())) {
                        data.setOrderDeliveryDesc(String.valueOf(row.getCell(8).getStringCellValue()));
                    }
                }

                // 电机型号
                if (row.getCell(9) == null) {
                    return R.error("第" + index + "行[电机型号]不能为空!");
                } else {
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    if (!StringUtils.isEmpty(row.getCell(9).getStringCellValue())) {
                        // 检查是否存在
                        BusInfoMotorTypeVo busInfoMotorTypeVo = busMotorInfoService.queryByCode(
                                String.valueOf(row.getCell(9).getStringCellValue()));
                        if (busInfoMotorTypeVo == null) {
                            return R.error("根据第" + index + "行[电机型号]未查询到该电机类型信息!");
                        }
                        data.setMotorTypeId(busInfoMotorTypeVo.getMotorTypeId());
                        data.setMotorTypeName(String.valueOf(row.getCell(9).getStringCellValue()));
                    } else {
                        return R.error("第" + index + "行[电机型号]不能为空!");
                    }

                }

                // 车辆电机编号
                if (row.getCell(10) == null) {
                    return R.error("第" + index + "行[车辆电机编号]不能为空!");
                } else {
                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    if (!StringUtils.isEmpty(row.getCell(10).getStringCellValue())) {
                        data.setBusMotorCode(String.valueOf(row.getCell(10).getStringCellValue()));
                    } else {
                        return R.error("第" + index + "行[车辆电机编号]不能为空!");
                    }
                }

                // 电池型号
                if (row.getCell(11) == null) {
                    return R.error("第" + index + "行[电池型号]不能为空!");
                } else {
                    row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    if (!StringUtils.isEmpty(row.getCell(11).getStringCellValue())) {
                        // 检查是否存在
                        BusInfoBatteryTypeVo busInfoBatteryTypeVo = busBatteryInfoService.queryByCode(
                                String.valueOf(row.getCell(11).getStringCellValue()));
                        if (busInfoBatteryTypeVo == null) {
                            return R.error("根据第" + index + "行[电池型号]未查询到该电池类型信息!");
                        }
                        data.setBatteryTypeId(busInfoBatteryTypeVo.getBatteryTypeId());
                        data.setBatteryTypeName(String.valueOf(row.getCell(11).getStringCellValue()));
                    } else {
                        return R.error("第" + index + "行[电池型号]不能为空!");
                    }
                }

                // 电池电机编号
                if (row.getCell(12) == null) {
                    return R.error("第" + index + "行[车辆电池编号]不能为空!");
                } else {
                    row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                    if (!StringUtils.isEmpty(row.getCell(12).getStringCellValue())) {
                        data.setBusBatteryCodes(Arrays.asList(String.valueOf(row.getCell(12).getStringCellValue()).split(",")));
                    } else {
                        return R.error("第" + index + "行[车辆电池编号]不能为空!");
                    }
                }
                data.setUserId(userId);
                list.add(data);
            }
            workbook.close();

            // 数据库保存
            if (list.size() != 0) {
                orderBusDeliveryService.saveImportBatch(list, userId);
            }
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量导入失败!"+e.getMessage());
        }


    }

}
