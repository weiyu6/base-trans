package com.wybase.trans.serve.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * mybatis-flex代码生成器
 * @author weiyu
 * @date 2023/7/29
 */
public class MybatisFlexGenerator {
    public static void main(String[] args) {
        //配置数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://192.168.24.10:3306/trans?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    private static GlobalConfig createGlobalConfigUseStyle1() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置生成 mapper
        globalConfig.enableEntity().setWithLombok(true);
        globalConfig.enableMapper();
//        globalConfig.enableController();
        globalConfig.enableService();
        globalConfig.enableServiceImpl();
        globalConfig.enableMapperXml();

        // 注释配置 JavadocConfig
        globalConfig.getJavadocConfig()
                .setAuthor("weiyu");
        //设置根包
        globalConfig.getPackageConfig()
                .setSourceDir(System.getProperty("user.dir") + "/trans-serve/src/main/java")
                .setBasePackage("com.wybase.trans.serve")
//                .setControllerPackage("com.wybase.trans.serve.controller")
                .setServicePackage("com.wybase.trans.serve.service")
                .setServiceImplPackage("com.wybase.trans.serve.service.impl")
                .setEntityPackage("com.wybase.trans.serve.entity.generate")
                .setMapperXmlPath(System.getProperty("user.dir") + "/trans-serve/src/main/java/"+"com/wybase/trans/serve/mapper/generate")
                .setMapperPackage("com.wybase.trans.serve.mapper.generate");

        //设置表前缀和只生成哪些表，setGenerateTable 未配置时，生成所有表
        Set<String> tables = new HashSet<>();
//        tables.add("b_sys_log");
//        tables.add("b_trans_record");
        tables.add("b_user_info");
//        tables.add("b_enum_list");
//        tables.add("b_menu");
//        tables.add("b_role");
//        tables.add("b_role_menu_relatn");
        globalConfig.getStrategyConfig()
                .setTablePrefix("b_")
                .setGenerateTables(tables);

        // Entity 生成配置 EntityConfig
        /*globalConfig.getEntityConfig()
                .setClassPrefix("Tbl")
                .setClassSuffix("Entity");*/

        // Mapper 生成配置 MapperConfig
        globalConfig.getMapperConfig()
//                .setClassPrefix("Tbl")
                .setClassSuffix("Mapper");
        // MapperXml 生成配置 MapperXmlConfig
        globalConfig.getMapperXmlConfig()
//                .setFilePrefix("Tbl")
                .setFileSuffix("Mapper");

        globalConfig.getServiceConfig()
                .setClassPrefix("I");

        return globalConfig;
    }
}
