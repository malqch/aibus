package com.wntime;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Generator.
 *
 * <p>generate mybatis code
 *
 * @author <a href="https://blog.csdn.net/yidichaxiang" target="_blank">Sleeber</a>
 * @since 1.3.0
 *
 * <p><b>Note:</b>
 * <p>2020/8/25 9:26: created.<br>
 */
public class CodeGenerator {
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        GlobalConfig gc = new GlobalConfig();

        Properties prop = new Properties();
        FileInputStream ferr = null;
        try {
            ferr = new FileInputStream("D:\\WorkProject.Materials\\AIoTBus\\java1.x\\test-modules\\aibus-test\\src\\main\\resources\\generator.properties");
            prop.load(ferr);
            ferr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        gc.setOutputDir(prop.getProperty("relativeParent.path") + "/src/main/java/autocode");
        gc.setAuthor("sleeber");   // 作者
        gc.setOpen(false);      //生成代码后是否打开文件夹
        gc.setServiceName("%sService");  // 设置Service接口生成名称,这样生成接口前面就不会有‘I’
        gc.setMapperName("%sDao");
        mpg.setGlobalConfig(gc);

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(prop.getProperty("datasource.url"));
        dsc.setDriverName(prop.getProperty("datasource.driver-class-name"));
        dsc.setUsername(prop.getProperty("datasource.username"));
        dsc.setPassword(prop.getProperty("datasource.password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("event"); // 模块名称
        pc.setParent("com.wntime"); // 父包名
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperControllerClass("com.wntime.controller.AbstractController");
        strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
        strategy.setSuperEntityClass("com.wntime.entity.BaseEntity");
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(true);
        //event
        strategy.setInclude(new String[]{"info_event_type", "info_event_target", "info_event_level", "info_collect_event", "info_event_extend", "log_event_detail", "log_event_attach"});
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.execute();
    }

}
