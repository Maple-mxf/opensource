package io.jopen.springboot.plugin.mongo.code.generator;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * @author maxuefeng
 * @since 2020/2/9
 */
public class RepositoryExtensionCodeGenerator {

    private final VelocityEngine ve = new VelocityEngine();


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
    private String serviceImplPackage;

    private String author = "";
    private String time = DateFormatUtils.format(new Date(), "yyyy/MM/dd");


    /**
     *
     */
    private Class<?> idClass;

    private String repositoryPackage;


    public RepositoryExtensionCodeGenerator(Class<?> entityClass,
                                            Class<? extends Serializable> idClass,
                                            String servicePackage,
                                            String serviceImplPackage,
                                            String repositoryPackage

    ) {
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        this.entityClass = entityClass;
        this.servicePackage = servicePackage;
        this.entityName = entityClass.getSimpleName();
        this.entityPackage = entityClass.getName();
        this.idClass = idClass;
        this.IDTypeSimpleName = idClass.getSimpleName();
        this.serviceImplPackage = serviceImplPackage;
        this.repositoryPackage = repositoryPackage;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void generateService(String serviceFilePath) throws IOException {
        checkupPath(serviceFilePath);
        Template serviceTemplate = ve.getTemplate("service.vm");
        VelocityContext serviceCtx = new VelocityContext();

        serviceCtx.put("servicePackage", servicePackage);
        serviceCtx.put("entityPackage", entityPackage);
        serviceCtx.put("entityName", entityName);
        serviceCtx.put("IDTypeSimpleName", IDTypeSimpleName);
        serviceCtx.put("author", author);
        serviceCtx.put("time", time);

        String filePath;

        if (serviceFilePath.endsWith("/") || serviceFilePath.endsWith("\\")) {
            filePath = serviceFilePath + entityName + "Service.java";
        } else {

            filePath = serviceFilePath + "/" + entityName + "Service.java";
        }

        FileWriter fileWriter = new FileWriter(filePath);
        serviceTemplate.merge(serviceCtx, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }

    public void generateServiceImpl(String serviceImplFilePath) throws IOException {
        checkupPath(serviceImplFilePath);
        Template serviceImplTemplate = ve.getTemplate("service_impl.vm");
        VelocityContext serviceImplCtx = new VelocityContext();

        serviceImplCtx.put("serviceImplPackage", serviceImplPackage);
        serviceImplCtx.put("entityPackage", entityPackage);
        serviceImplCtx.put("entityName", entityName);
        serviceImplCtx.put("IDTypeSimpleName", IDTypeSimpleName);
        serviceImplCtx.put("author", author);
        serviceImplCtx.put("time", time);

        serviceImplCtx.put("servicePackage", servicePackage);
        serviceImplCtx.put("repositoryPackage", repositoryPackage);

        String filePath;

        if (serviceImplFilePath.endsWith("/") || serviceImplFilePath.endsWith("\\")) {
            filePath = serviceImplFilePath + entityName + "ServiceImpl.java";
        } else {

            filePath = serviceImplFilePath + "/" + entityName + "ServiceImpl.java";
        }

        FileWriter fileWriter = new FileWriter(filePath);
        serviceImplTemplate.merge(serviceImplCtx, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }


    public void generateRepository(String repositoryFilePath) throws IOException {
        checkupPath(repositoryFilePath);
        Template serviceTemplate = ve.getTemplate("repository.vm");
        VelocityContext serviceCtx = new VelocityContext();

        serviceCtx.put("serviceImplPackage", serviceImplPackage);
        serviceCtx.put("entityPackage", entityPackage);
        serviceCtx.put("entityName", entityName);
        serviceCtx.put("IDTypeSimpleName", IDTypeSimpleName);
        serviceCtx.put("author", author);
        serviceCtx.put("time", time);
        serviceCtx.put("servicePackage", servicePackage);
        serviceCtx.put("repositoryPackage", repositoryPackage);

        String filePath;

        if (repositoryFilePath.endsWith("/") || repositoryFilePath.endsWith("\\")) {
            filePath = repositoryFilePath + entityName + "Repository.java";
        } else {

            filePath = repositoryFilePath + "/" + entityName + "Repository.java";
        }

        FileWriter fileWriter = new FileWriter(filePath);
        serviceTemplate.merge(serviceCtx, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }

    public void generatorAll(String serviceLocationPath, String serviceImplLocationPath, String repositoryLocationPath) throws IOException {
        this.generateService(serviceLocationPath);
        this.generateServiceImpl(serviceImplLocationPath);
        this.generateRepository(repositoryLocationPath);
    }

    public void checkupPath(String path) {
        File file = new File(path);
        boolean exists = file.exists();
        if (!exists) {
            file.mkdirs();
        }
    }
}
