package init;

import init.util.mybatisplus.generator.AutoGenerator;
import init.util.mybatisplus.generator.InjectionConfig;
import init.util.mybatisplus.generator.config.*;
import init.util.mybatisplus.generator.config.rules.DbType;
import init.util.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Author hejj
 * mybatis自动生成，数据库表需要在table.txt定义，每行一个表名
 *
 */
public class MybatisGenerator {
    /**
     * 数据库相关，这里默认是wcyl数据库
     */
    private final String dbDriverName = "com.mysql.jdbc.Driver";
    private final String dbUser ="root";
    private final String dbPassword ="123456";
    private final String dbUrl = "jdbc:mysql://127.0.0.1:3306/jcala_blog";

    /**
     * 本机相关,项目所在地址和作者等
     */
    private final String projectDir = "D://Demo//";
    private final String outputDir = projectDir+"me//src//main//java//";
    private final String author = "hejj";

    //项目相关
    private final String packgaeParentName = "com.blog.module";//父包名
    private final String moduleName = "test1";//开发的系统的模块名，这里也用到在controller的url中

    private String[] tables;


    @Before
    public void getTables(){
        try {
            String encoding="UTF-8";
            File file=new File("./src/test/java/init/table.txt");
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                List<String> tmp = new ArrayList<String>();//因为数组需要指定数组长度，所以这里用list
                String lineTxt = null;
                for(int i = 0;(lineTxt = bufferedReader.readLine()) != null;i++ ){
                    tmp.add(lineTxt);
                    System.out.println(lineTxt);
                }
                tables = tmp.toArray(new String[tmp.size()]);
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }


    @Test
    public void generator(){
        AutoGenerator mpg = new AutoGenerator();

        /**
         *  全局配置,生成文件名，xml具体生成策略等
         */
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir);
        gc.setFileOverride(true);
        gc.setActiveRecord(false);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor(author);


        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        /**
         *  数据源配置
         */
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(dbDriverName);
        dsc.setUsername(dbUser);
        dsc.setPassword(dbPassword);
        dsc.setUrl(dbUrl);
        mpg.setDataSource(dsc);

        /**
         * 策略配置,设置文件名生成策略
         */
        StrategyConfig strategy = new StrategyConfig();
        //strategy.setTablePrefix("bmd_");// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.remove_prefix_and_camel);// 表名生成策略
        strategy.setInclude(tables); // 需要生成的表
        //strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 字段名生成策略
        strategy.setFieldNaming(NamingStrategy.underline_to_camel);
        // 自定义实体父类
         strategy.setSuperEntityClass("org.opsteel.wcyl.commons.base.model.BaseModel");

        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        strategy.setSuperControllerClass("org.opsteel.wcyl.commons.base.BaseController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
         //strategy.setEntityBuliderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packgaeParentName);//包名
        pc.setModuleName(moduleName);//模块名，在url中也有体现
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);

        // 自定义模板配置
        Properties properties = new Properties();
        String basePath = "/wcyl/init/util/template/";


        TemplateConfig tc = new TemplateConfig();
        tc.setController("/template/controller.java.vm");
//         tc.setEntity("template/model.java.vm");
//         tc.setMapper("...");
//         tc.setXml("...");
//         tc.setService("...");
//        tc.setServiceImpl("...");
         mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }
}
