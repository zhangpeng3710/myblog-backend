package com.roc.blog.server.mybatis.plus.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 快速生成
 * </p>
 *
 * @author lanjerry
 * @since 2021-09-16
 */
public class MybatisPlusAutoGenerator {
    /**
     * 执行数据库脚本
     */
    protected static void initDataSource(DataSourceConfig dataSourceConfig) throws SQLException {
        Connection conn = dataSourceConfig.getConn();
        InputStream inputStream = com.roc.blog.server.BlogServerApplication.class.getResourceAsStream("/db/h2/schema-h2.sql");
        ScriptRunner scriptRunner = new ScriptRunner(conn);
        scriptRunner.setAutoCommit(true);
        scriptRunner.runScript(new InputStreamReader(inputStream));
        conn.close();
    }

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;", "root", "");

    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {
        List<String> tables = Arrays.asList(
               // "sys_user",
                "app_user"
        );
        String path = System.getProperty("user.dir");

        // 初始化数据库脚本
        initDataSource(DATA_SOURCE_CONFIG.build());
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig(builder -> builder
                        .author("mybatis-plus-auto-generator")
                        .disableOpenDir()
                        .commentDate("yyyy--MM-dd HH:mm:ss")
                        .dateType(DateType.ONLY_DATE)
                        .outputDir(path + "/src/main/java")
                )
                // 包配置
                .packageConfig(builder -> builder
                        .parent("com.roc.blog.server")
                        .moduleName("dao")
                        //设置mapper xml位置
//                        .pathInfo(Collections.singletonMap(OutputFile.xml,"/Users/zp/IdeaProjects/mybatis-plus-samples/demo/src/main/resources/mapper"))
                )
                // 策略配置
                .strategyConfig(builder -> builder
                        .addInclude(tables)
                        .entityBuilder()
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        .enableActiveRecord()
                        .enableChainModel()
                        .mapperBuilder()
                        .enableMapperAnnotation()
                )
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker 或 Enjoy
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                   .templateEngine(new EnjoyTemplateEngine())
                 */
                .templateEngine(new FreemarkerTemplateEngine())
                .templateConfig(builder -> {
                    builder.disable(TemplateType.CONTROLLER)
                            .disable(TemplateType.SERVICE)
                            .disable(TemplateType.SERVICE_IMPL).disable(TemplateType.XML);

                })
                .execute();
    }
}
