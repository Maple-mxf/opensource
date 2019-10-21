package io.jopen.distributelock;

/**
 * @author maxuefeng
 * @since 2019/10/21
 */
public abstract class Config {

    protected String uri;

    public Config(String uri) {
        this.uri = uri;
    }

    public abstract String host();

    public abstract Integer port();
}
