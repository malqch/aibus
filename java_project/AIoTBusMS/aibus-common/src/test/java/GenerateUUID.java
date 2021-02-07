import cn.hutool.core.lang.Snowflake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GenerateUUID.class)
public class GenerateUUID {

    @Test
    public void testGenerateUUID() {
        Snowflake uuid = new Snowflake(0, 0);
        String value = "小吃快餐；烧烤；自助餐；北京菜；韩国料理；川菜；奥才；湘菜；东北菜；素食；面食";
        String[] values = value.split("；");
        DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < values.length; i++) {
            System.out.println(String.format("%d\t%s\t0\t1\t1\t%s",
                    uuid.nextId(), values[i], df.format(new Date())));
        }
    }

}
