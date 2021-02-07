import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.List;
import java.util.Map;

public class PoiTest {
    public static void main(String[] args) {
        ExcelReader reader = ExcelUtil.getReader("/Users/now/Desktop/学生信息导入模板.xlsx");
        List<Map<String,Object>> readAll = reader.readAll();
        for (Map<String, Object> map : readAll) {
            System.out.println(map);
        }
    }
}
