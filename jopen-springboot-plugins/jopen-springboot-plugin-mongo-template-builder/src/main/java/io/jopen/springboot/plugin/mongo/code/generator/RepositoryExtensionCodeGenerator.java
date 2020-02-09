package io.jopen.springboot.plugin.mongo.code.generator;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;

/**
 * @author maxuefeng
 * @since 2020/2/9
 */
public class RepositoryExtensionCodeGenerator {

    private final VelocityEngine ve = new VelocityEngine();

    private final VelocityContext ctx = new VelocityContext();

    private final Class<?> entityClass;

    /**
     * service包名
     */
    private String servicePackage;

    /**
     * 实体类的包名
     */
    private String entityPackage;

    /**
     * 实体类名称
     */
    private String entityName;

    /**
     * 实体类ID的类型
     */
    private String IDTypeSimpleName;

    private String author = "";
    private String time = DateFormatUtils.format(new Date(), "yyyy/MM/dd");

    /**
     *
     */
    private Class<?> idClass;


    public RepositoryExtensionCodeGenerator(Class<?> entityClass,
                                            Class<? extends Serializable> idClass,
                                            String servicePackage
    ) {
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        this.entityClass = entityClass;
        this.servicePackage = servicePackage;
        this.entityName = entityClass.getSimpleName();
        this.entityPackage = entityClass.getPackage().getName();
        this.idClass = idClass;
        this.IDTypeSimpleName = idClass.getSimpleName();
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void generateService(String serviceFilePath) throws IOException {
        Template serviceTemplate = ve.getTemplate("service.vm");
        VelocityContext serviceCtx = new VelocityContext();

        serviceCtx.put("servicePackage", servicePackage);
        serviceCtx.put("entityPackage", entityPackage);
        serviceCtx.put("entityName", entityName);
        serviceCtx.put("IDTypeSimpleName", IDTypeSimpleName);
        serviceCtx.put("author", author);
        serviceCtx.put("time", time);

        StringWriter sw = new StringWriter();
        serviceTemplate.merge(serviceCtx, sw);
        System.out.println(sw.toString());

        String filePath;

        if (serviceFilePath.endsWith("/") || serviceFilePath.endsWith("\\")) {
            filePath = serviceFilePath + entityName + "Service.java";
        } else {

            filePath = serviceFilePath + "/" + entityName + "Service.java";
        }

        FileWriter fileWriter = new FileWriter(filePath);
        serviceTemplate.merge(serviceCtx, fileWriter);
        serviceTemplate.process();
    }


}
