//package com.shcode;
//
//
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
//public class GeneratoDemo {
//
//
//
//    public static void main(String[] args) {
//        Date date = new Date();
//        LocalDateTime localDateTime= LocalDateTime.parse("2021-11-11 23:00:20T");
//        Duration duration = Duration.between(LocalDateTime.now(),localDateTime); // 距离过期时间
//        System.out.println(duration.toMinutes());
////        // 代码生成器
////        AutoGenerator mpg = new AutoGenerator();
////        // 全局配置
////        GlobalConfig gc = new GlobalConfig();
////        String projectPath = System.getProperty("user.dir");
////        gc.setOutputDir(projectPath + "/src/main/java");
////        gc.setAuthor("pc");
////        gc.setOpen(false);
////        gc.setFileOverride(true); //是否覆盖
////         gc.setSwagger2(false); //实体属性 Swagger2 注解
////        mpg.setGlobalConfig(gc);
////        // 数据源配置
////        DataSourceConfig dsc = new DataSourceConfig();
////        dsc.setUrl("jdbc:mysql://localhost:3306/dzht?useUnicode=true&useSSL=false&characterEncoding=utf8");
////        // dsc.setSchemaName("public");
////        dsc.setDriverName("com.mysql.jdbc.Driver");
////        dsc.setUsername("root");
////        dsc.setPassword("123456");
////        mpg.setDataSource(dsc);
////        // 包配置
////        PackageConfig pc = new PackageConfig();
//////        pc.setModuleName("specific");
////        // 文件 生成路径
////        pc.setParent("com.hyt.dzht");
////        mpg.setPackageInfo(pc);
////        // 自定义配置
////        InjectionConfig cfg = new InjectionConfig() {
////            @Override
////            public void initMap() {
////                // to do nothing
////            }
////        };
////        // 如果模板引擎是 freemarker
//////        String templatePath = "/templates/mapper.xml.ftl";
////        // 如果模板引擎是 velocity
////         String templatePath = "/templates/mapper.xml.vm";
////        // 自定义输出配置
////        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
////        // 自定义配置会被优先输出
////        focList.add(new FileOutConfig(templatePath) {
////            @Override
////            public String outputFile(TableInfo tableInfo) {
////                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
////                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
////                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
////            }
////        });
////        /*
////        cfg.setFileCreate(new IFileCreate() {
////            @Override
////            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
////                // 判断自定义文件夹是否需要创建
////                checkDir("调用默认方法创建的目录，自定义目录用");
////                if (fileType == FileType.MAPPER) {
////                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
////                    return !new File(filePath).exists();
////                }
////                // 允许生成模板文件
////                return true;
////            }
////        });
////        */
////        cfg.setFileOutConfigList(focList);
////        mpg.setCfg(cfg);
////
////        // 配置模板
//////        TemplateConfig templateConfig = new TemplateConfig();
////        // 配置自定义输出模板
////        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
////        // templateConfig.setEntity("templates/entity2.java");
////        // templateConfig.setService();
////        // templateConfig.setController();
//////        templateConfig.setXml(null);
//////        mpg.setTemplate(templateConfig);
////        //        使用默认 模板引擎
//////        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
////
////        // 策略配置
////        StrategyConfig strategy = new StrategyConfig();
////        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
////        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//////        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
////        strategy.setEntityLombokModel(true);
////
////        strategy.setRestControllerStyle(true);
////        // 公共父类
//////        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
////        // 写于父类中的公共字段
//////        strategy.setSuperEntityColumns("userid");
//////        表名，多个英文逗号分割
////        strategy.setInclude("ht");
////        strategy.setControllerMappingHyphenStyle(true);
//////        System.out.println("ssss"+pc.getModuleName());
//////        strategy.setTablePrefix("pc_");   //去掉表中前缀
//////        strategy.setTablePrefix(pc.getModuleName() + "_");
////        mpg.setStrategy(strategy);
////        mpg.execute();
//    }
//
//}