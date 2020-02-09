package io.jopen.springboot.plugin.mongo.code.generator;

import org.junit.Test;

import java.io.IOException;

/**
 * @author maxuefeng
 * @see RepositoryExtensionCodeGenerator
 * @since 2020/2/9
 */
public class RepositoryExtensionCodeGeneratorTest {

    @Test
    public void testRepositoryExtensionCodeGenerator() throws IOException {
        RepositoryExtensionCodeGenerator codeGenerator =
                new RepositoryExtensionCodeGenerator(User.class, String.class, "io.jopen.springboot.plugin.mongo.code.generator");
        codeGenerator.setAuthor("maxuefeng");
        codeGenerator.generateService("E:\\java-workplace\\opensource\\jopen-springboot-plugins\\jopen-springboot-plugin-mongo-template-builder\\src\\test\\java\\io\\jopen\\springboot\\plugin\\mongo\\code\\generator");

    }
}
