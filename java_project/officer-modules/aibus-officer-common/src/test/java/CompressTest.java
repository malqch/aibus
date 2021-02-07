import com.wntime.common.CompressTools;
import org.apache.commons.io.FilenameUtils;

public class CompressTest {
    public static void main(String[] args) throws Exception {
//        CompressTools.unrar("/Users/now/Desktop/图片.rar","/Users/now/Documents/校车/数据库/图版/");
//        CompressTools.unZip(" /Users/now/Desktop/图片.zip","/Users/now/Documents/校车/数据库/zip/");
        String baseName = "任少卿082412";
//        String baseName = FilenameUtils.getBaseName(file1.getAbsolutePath());
        int length = baseName.length();
        String idNo = baseName.substring(length - 6, length);
        String name = baseName.substring(0, length - 6);
        System.out.println(idNo);
        System.out.println(name);
    }

}
