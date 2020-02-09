package io.jopen.springboot.plugin.mongo.code.generator.test;

import io.jopen.springboot.plugin.mongo.code.generator.RepositoryExtensionCodeGenerator;
import org.junit.Test;

import java.io.IOException;

/**
 * @author maxuefeng
 * @see RepositoryExtensionCodeGenerator
 * @since 2020/2/9
 */
public class RepositoryExtensionCodeGeneratorTest {

    @Test
    public void testRepositoryExtensionCodeGeneratorService() throws IOException {
        RepositoryExtensionCodeGenerator codeGenerator =
                new RepositoryExtensionCodeGenerator(User.class,
                        String.class,
                        "io.jopen.springboot.plugin.mongo.code.generator",
                        "io.jopen.springboot.plugin.mongo.code.generator",
                        "io.jopen.springboot.plugin.mongo.code.generator"
                        );

        codeGenerator.setAuthor("maxuefeng");
        codeGenerator.generateService("E:\\java-workplace\\opensource\\jopen-springboot-plugins\\jopen-springboot-plugin-mongo-template-builder\\src\\test\\java\\io\\jopen\\springboot\\plugin\\mongo\\code\\generator");

    }

    @Test
    public void testRepositoryExtensionCodeGeneratorServiceImpl() throws IOException {
        RepositoryExtensionCodeGenerator codeGenerator =
                new RepositoryExtensionCodeGenerator(User.class,
                        String.class,
                        "io.jopen.springboot.plugin.mongo.code.generator",
                        "io.jopen.springboot.plugin.mongo.code.generator",
                        "io.jopen.springboot.plugin.mongo.code.generator");

        codeGenerator.setAuthor("maxuefeng");
        codeGenerator.generateServiceImpl("E:\\java-workplace\\opensource\\jopen-springboot-plugins\\jopen-springboot-plugin-mongo-template-builder\\src\\test\\java\\io\\jopen\\springboot\\plugin\\mongo\\code\\generator");
    }


    @Test
    public void testRepositoryExtensionCodeGeneratorRepository() throws IOException {
        RepositoryExtensionCodeGenerator codeGenerator =
                new RepositoryExtensionCodeGenerator(User.class,
                        String.class,
                        "io.jopen.springboot.plugin.mongo.code.generator",
                        "io.jopen.springboot.plugin.mongo.code.generator",
                        "io.jopen.springboot.plugin.mongo.code.generator");

        codeGenerator.setAuthor("maxuefeng");
        codeGenerator.generateRepository("E:\\java-workplace\\opensource\\jopen-springboot-plugins\\jopen-springboot-plugin-mongo-template-builder\\src\\test\\java\\io\\jopen\\springboot\\plugin\\mongo\\code\\generator");
    }


    @Test
    public  void testGenerateAllFile() throws IOException {
        RepositoryExtensionCodeGenerator codeGenerator =
                new RepositoryExtensionCodeGenerator(User.class,
                        String.class,
                        "io.jopen.springboot.plugin.mongo.code.generator.test.other.service",
                        "io.jopen.springboot.plugin.mongo.code.generator.test.other.service.impl",
                        "io.jopen.springboot.plugin.mongo.code.generator.test.other.repository");

        codeGenerator.setAuthor("maxuefeng");


        codeGenerator.generatorAll(
              "E:\\java-workplace\\opensource\\jopen-springboot-plugins\\jopen-springboot-plugin-mongo-template-builder\\src\\test\\java\\io\\jopen\\springboot\\plugin\\mongo\\code\\generator\\test\\other\\service",
              "E:\\java-workplace\\opensource\\jopen-springboot-plugins\\jopen-springboot-plugin-mongo-template-builder\\src\\test\\java\\io\\jopen\\springboot\\plugin\\mongo\\code\\generator\\test\\other\\service\\impl",
              "E:\\java-workplace\\opensource\\jopen-springboot-plugins\\jopen-springboot-plugin-mongo-template-builder\\src\\test\\java\\io\\jopen\\springboot\\plugin\\mongo\\code\\generator\\test\\other\\repository"
        );

    }

}
