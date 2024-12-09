package com.qbp.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.Scanner;

public class MybatisPlusGenerator {
    private static final String url;
    private static final String username;
    private static final String password;
    private static final String author;
    private static final String packageName;

    static {
        try (FileInputStream fis = new FileInputStream("src/main/resources/generator.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            url = properties.getProperty("spring.datasource.url");
            username = properties.getProperty("spring.datasource.username");
            password = properties.getProperty("spring.datasource.password");
            author = properties.getProperty("author");
            packageName = properties.getProperty("package.name");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    public static void main(String[] args) {
        // 获取项目路径
        String projectPath = System.getProperty("user.dir");
        // 模块名
        String moduleName = scanner("请输入模块名（可为空）：");
        //
        String[] tableNames = scanner("请输入表名（多个表英文逗号隔开，生成模块下的可使用`模块名_*`，为空则生成所有表）：").split(",");
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator(initDataSourceConfig());
        autoGenerator.global(initGlobalConfig(projectPath));
        autoGenerator.packageInfo(initPackageConfig(projectPath, moduleName));
        autoGenerator.injection(initInjectionConfig());
        autoGenerator.template(initTemplateConfig());
        autoGenerator.strategy(initStrategyConfig(tableNames));
        autoGenerator.execute(new VelocityTemplateEngine());
    }

    /**
     * 初始化策略配置
     */
    private static StrategyConfig initStrategyConfig(String[] tableNames) {
        StrategyConfig.Builder builder = new StrategyConfig.Builder();
        builder.entityBuilder()
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .enableLombok()
                .formatFileName("%s")
                .mapperBuilder()
                .enableBaseResultMap()
                .formatMapperFileName("%sMapper")
                .formatXmlFileName("%sMapper")
                .serviceBuilder()
                .formatServiceFileName("%sService")
                .formatServiceImplFileName("%sServiceImpl")
                .controllerBuilder()
                .enableRestStyle()
                .formatFileName("%sController");
        if (tableNames[0].contains("*")) {
            String[] likeStr = tableNames[0].split("_");
            String likePrefix = likeStr[0] + "_";
            builder.likeTable(new LikeTable(likePrefix));
        } else if (!tableNames[0].isEmpty()) {
            builder.addInclude(tableNames);
        }
        return builder.build();
    }

    /**
     * 配置模板
     */
    private static TemplateConfig initTemplateConfig() {
        //可以对controller、service、entity模板进行配置
        return new TemplateConfig
                .Builder()
                .entity("model/entity")
                .build();
    }

    /**
     * 配置自定义配置
     */
    private static InjectionConfig initInjectionConfig() {
        return new InjectionConfig.Builder().build();
    }

    /**
     * 配置包名
     */
    private static PackageConfig initPackageConfig(String projectPath, String moduleName) {
        return new PackageConfig.Builder()
                .moduleName(moduleName)
                .parent(packageName)
                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper/" + moduleName))
                .build();
    }

    /**
     * 配置全局参数
     */
    private static GlobalConfig initGlobalConfig(String projectPath) {
        return new GlobalConfig.Builder()
                .outputDir(projectPath + "/src/main/java")
                .author(author)
                .disableOpenDir()
                .fileOverride()
                .dateType(DateType.ONLY_DATE)
                .build();
    }

    /**
     * 读取控制台内容
     */
    private static String scanner(String str) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(str);
        return scanner.nextLine().trim();
    }

    /**
     * 初始化数据源配置
     */
    private static DataSourceConfig initDataSourceConfig() {
        return new DataSourceConfig.Builder(url, username, password).dbQuery(new MySqlQuery()).build();
    }
}
