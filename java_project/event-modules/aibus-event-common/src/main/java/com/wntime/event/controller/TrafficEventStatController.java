package com.wntime.event.controller;

import com.wntime.common.exception.RRException;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.event.config.EventFileConfig;
import com.wntime.event.entity.InfoEventTargetEntity;
import com.wntime.event.service.InfoEventTargetService;
import com.wntime.event.service.LogEventDetailService;
import com.wntime.event.vo.EventTrafficStatVo;
import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.service.BusInfoService;
import com.wntime.service.common.util.HistogramUtil;
import com.wntime.service.common.vo.HistogramData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ysc
 * 2020/8/27 14:46
 */
@Api(value = "交通违章分析接口",tags = {"交通违章分析接口"})
@RequestMapping("/event/traffic")
@RestController
public class TrafficEventStatController {

    @Autowired
    private LogEventDetailService logEventDetailService;
    @Autowired
    private InfoEventTargetService infoEventTargetService;
    @Autowired
    private BusInfoService busInfoService;

    @Resource
    private EventFileConfig eventFileConfig;

    /**
     * 交通违章分析维度
     */
    private static final String BUS = "bus";
    private static final String TIME = "time";

    @ApiOperation(value = "公交车采集交通违章违规数统计", notes = "以车辆或者时间为维度", httpMethod = "GET")
    @GetMapping("/dim/{dim}/stat")
    public R getTrafficEventCountByDimAndCompanyId(@PathVariable String dim, @RequestParam Long companyId,
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) Date startTime,
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) Date endTime) {
        if (dim.equals(BUS)) {
            //按车辆统计数据
            List<Map<String, Object>> trafficEventCountList = logEventDetailService.getTrafficEventCountByCompanyIdGroupByBus(companyId, startTime);
            HistogramData histogramData = HistogramUtil.creatHistogramDataFromMap(trafficEventCountList, "vin_code", "traffic_event_count");
            return R.ok().put("data", histogramData);
        } else if (dim.equals(TIME)) {
            //按小时统计数据
            List<Map<String, Object>> trafficEventCountList = logEventDetailService.getTrafficEventCountByCompanyIdGroupByTimeWindow(companyId, startTime, endTime);
            HistogramData histogramData = HistogramUtil.creatHistogramDataFromMap(trafficEventCountList, "time_range", "traffic_event_count");
            return R.ok().put("data", histogramData);
        } else {
            return R.error(400, "目前只支持车辆和时间维度，其余暂不支持");
        }
    }

    @ApiOperation(value = "交通违章类别统计分析", notes = "采集交通违章类型统计", httpMethod = "GET")
    @GetMapping("/type/stat")
    public R getTrafficEventTypeStat(@RequestParam(value = "companyId", required = true) Long companyId) {

        List<Map<String, Object>> faultTypeCountList = logEventDetailService.getEventCountByCompanyIdGroupByTrafficEventType(companyId);

        HistogramData histogramData = HistogramUtil
                .creatHistogramDataFromMap(faultTypeCountList, "event_target_name", "event_target_count");
        return R.ok().put("data", histogramData);
    }

    @ApiOperation(value = "交通违章类型列表", notes = "", httpMethod = "GET")
    @GetMapping("/type/list")
    public R getTrafficEventTypeList() {
        List<InfoEventTargetEntity> list = infoEventTargetService.getTrafficEventTypeList();
        return R.ok().put("list", list);
    }

    @ApiOperation(value = "交通违规列表统计（指定公司）", notes = "交通违规列表统计（指定公司）", httpMethod = "GET")
    @GetMapping("/statByCompany/page")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公司Id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "eventTargetName", value = "违规类型", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "vinCode", value = "车辆VIN", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "plateCode", value = "车牌号", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "busCode", value = "车辆编号", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "string")
    })
    public R getTrafficStatByCompany(@RequestParam Map<String, Object> params) {
        // 查询公司下车辆
        List<InfoBusEntity> infoBusEntityList = busInfoService.getAllBusByQueryAreaId(params);
        if (infoBusEntityList == null || infoBusEntityList.size() == 0) {
            return R.ok().put("list", new PageUtils(new ArrayList<>(), 0, 0, 0));
        }
        List<Long> busIdList = infoBusEntityList.stream().map(InfoBusEntity::getBusId).collect(Collectors.toList());
        params.put("busIdList", busIdList);
        PageUtils<EventTrafficStatVo> pages = logEventDetailService.getTrafficStatByCompanyPage(params);
        if (pages != null && pages.getList() != null) {
            for (EventTrafficStatVo eventTrafficStatVo : pages.getList()) {
                List<String> imgList = new ArrayList<>();
                imgList.add(eventTrafficStatVo.getImg1());
                imgList.add(eventTrafficStatVo.getImg2());
                imgList.add(eventTrafficStatVo.getImg3());
                eventTrafficStatVo.setEvidenceImageList(imgList);
            }
        }
        return R.ok().put("list", pages);
    }
//    ------废弃------
//    @ApiOperation(value = "查询交通违规上次导出时间（指定公司）", notes = "查询交通违规上次导出时间（指定公司）", httpMethod = "GET")
//    @GetMapping("/statByCompany/lastExportTime")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query",name="companyId",value="公司Id",required = true,dataType = "long"),
//            @ApiImplicitParam(paramType = "query",name="params",value="参数",dataType = "string")
//    })
//    public R getTrafficStatByCompanyLastExportTime(@RequestParam Map<String, Object> params){
//        String exportTime = logEventDetailService.getTrafficStatByCompanyLastExportTime(params);
//        return R.ok().put("lastExportTime",exportTime);
//    }


    @ApiOperation(value = "交通违规列表导出（指定公司）", notes = "交通违规列表导出（指定公司）", httpMethod = "GET")
    @GetMapping("/statByCompany/export")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="companyId",value="公司Id",required = true,dataType = "long"),
            @ApiImplicitParam(paramType = "query",name="startTime",value="开始时间",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="endTime",value="结束时间",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="params",value="参数",dataType = "string")
    })
    public void getTrafficStatByCompanyExport(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam Map<String, Object> params){
        try {
            String fileName = "";
            List<EventTrafficStatVo> dataList = new ArrayList<>();
            // 查询公司下车辆
            List<Long> companyIdList = new ArrayList<>();
            companyIdList.add(Long.parseLong(String.valueOf(params.get("companyId")) ));
            List<InfoBusEntity> infoBusEntityList = busInfoService.getAllBusByAreaId(0L,companyIdList);
            if(infoBusEntityList != null && infoBusEntityList.size() > 0){
                List<Long> busIdList = infoBusEntityList.stream().map(InfoBusEntity::getBusId).collect(Collectors.toList());
                params.put("busIdList",busIdList);
                dataList = logEventDetailService.getTrafficStatByCompanyPageExport(params);
            }

            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            fileName =  String.valueOf(params.get("startTime")).substring(0,10)
                    + "-" +  String.valueOf(params.get("endTime")).substring(0,10);
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
            Font font = workbook.createFont();
            font.setFontName("黑体");
            font.setFontHeightInPoints((short) 16);//设置字体大小
            font.setBold(true);

            // 数据行数量
            int dataCount = dataList.size();
            CellRangeAddress region1 = new CellRangeAddress(0,0,0,10);
            sheet1.addMergedRegion(region1);

            //创建行
            Row row1 = sheet1.createRow(0);//标题
            row1.setHeight((short) (256*2));
            Cell cell1 = row1.createCell(0);
            cell1.setCellValue("交通违章统计"+"("+fileName+")");

            //创建行 头部
            Row row = sheet1.createRow(1);
            row.setHeight((short) (256*2));

            //创建单元格
            Cell cell = row.createCell(0);
            cell.setCellValue("公交车编码");
            sheet1.setColumnWidth(0,256*20);

            cell = row.createCell(1);
            cell.setCellValue("所属线路");
            sheet1.setColumnWidth(1,256*15);

            cell = row.createCell(2);
            cell.setCellValue("违规类型");
            sheet1.setColumnWidth(2,256*15);

            cell = row.createCell(3);
            cell.setCellValue("车牌号");
            sheet1.setColumnWidth(3,256*15);

            cell = row.createCell(4);
            cell.setCellValue("经度");
            sheet1.setColumnWidth(4,256*15);

            cell = row.createCell(5);
            cell.setCellValue("纬度");
            sheet1.setColumnWidth(5,256*15);

            cell = row.createCell(6);
            cell.setCellValue("证据图片1");
            sheet1.setColumnWidth(6,256*25);

            cell = row.createCell(7);
            cell.setCellValue("证据图片2");
            sheet1.setColumnWidth(7,256*25);

            cell = row.createCell(8);
            cell.setCellValue("证据图片3");
            sheet1.setColumnWidth(8,256*25);

            cell = row.createCell(9);
            cell.setCellValue("发生时间");
            sheet1.setColumnWidth(9,256*20);

            cell = row.createCell(10);
            cell.setCellValue("违规地点");
            sheet1.setColumnWidth(10,256*25);

            cell.setCellStyle(hssfCellStyle);//设置单元格的文本居中显示
            XSSFDrawing patriarch = ((XSSFSheet)sheet1).createDrawingPatriarch();
            for(int i = 0;i<dataCount;i++){
                //内容
                Row rows = sheet1.createRow(i+2);
                rows.setHeight((short) (256*8));
                Cell cells = rows.createCell(0);
                cells.setCellValue(dataList.get(i).getVinCode());//设置第一行第一格的值

                cells = rows.createCell(1);
                cells.setCellValue(dataList.get(i).getCompanyLineName());

                cells = rows.createCell(2);
                cells.setCellValue(dataList.get(i).getEventTargetName());

                cells = rows.createCell(3);
                cells.setCellValue(dataList.get(i).getNumberPlate());

                cells = rows.createCell(4);
                cells.setCellValue(dataList.get(i).getLongitude());

                cells = rows.createCell(5);
                cells.setCellValue(dataList.get(i).getLatitude());

                try{
                    cells = rows.createCell(6);
                    createSheetImg(workbook, patriarch,6,  i+2, dataList.get(i).getImg1());

                    cells = rows.createCell(7);
                    createSheetImg(workbook, patriarch,7,  i+2, dataList.get(i).getImg2());

                    cells = rows.createCell(8);
                    createSheetImg(workbook, patriarch,8,  i+2, dataList.get(i).getImg3());
                }catch (Exception e){
                    e.printStackTrace();
                    continue;
                }

                cells = rows.createCell(9);
                cells.setCellValue(DateUtils.format(dataList.get(i).getHappenTime(),DateUtils.DATE_TIME_PATTERN) );

                cells = rows.createCell(10);
                cells.setCellValue(dataList.get(i).getAddress());
                cells.setCellStyle(hssfCellStyle);
            }
            // 输出
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("文件导出异常",e);
        }
    }

    /**
     * Excel写入图片
     * @param workbook
     * @param patriarch
     * @param colIndex
     * @param rowIndex
     * @param pictureUrl
     * @throws Exception
     */
    private void createSheetImg(Workbook workbook, XSSFDrawing patriarch,
                                int colIndex , int rowIndex, String pictureUrl) throws Exception {
        pictureUrl = pictureUrl.replaceAll(" ","");
        if (pictureUrl.startsWith(eventFileConfig.getFileUrl())){
            pictureUrl = eventFileConfig.getFilePath() + pictureUrl.substring(eventFileConfig.getFileUrl().length());
        }
//        pictureUrl = "D:\\awork\\智能公交\\1597633273(1).jpg";
        if(StringUtils.isNotBlank(pictureUrl)) {
            File file = new File(pictureUrl);
            FileInputStream inStream = new FileInputStream(file);
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] byteData = readInputStream(inStream);

            /**anchor主要用于设置图片的属性
             * 该构造函数有8个参数
             * 前四个参数是控制图片在单元格的位置，分别是图片距离单元格left，top，right，bottom的像素距离
             * 后四个参数，前两个表示图片左上角所在的cellNum和 rowNum，后两个参数对应的表示图片右下角所在的cellNum和 rowNum，
             * excel中的cellNum和rowNum的index都是从0开始的
             *
             */
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short)colIndex,
                    rowIndex, (short) colIndex+1,rowIndex+1 );
            //Sets the anchor type （图片在单元格的位置）
            //0 = Move and size with Cells, 2 = Move but don't size with cells, 3 = Don't move or size with cells.

            anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);//设置图片随单元移动调整大小
            patriarch.createPicture(anchor, workbook.addPicture(byteData, XSSFWorkbook.PICTURE_TYPE_JPEG));
        }
    }

    /**
     * 读取图片流
     * @param inStream
     * @return
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}
