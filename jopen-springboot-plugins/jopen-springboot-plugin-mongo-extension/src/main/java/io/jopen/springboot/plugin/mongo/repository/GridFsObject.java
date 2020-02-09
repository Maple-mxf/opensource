package io.jopen.springboot.plugin.mongo.repository;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

/**
 * @author maxuefeng
 * @see com.mongodb.client.gridfs.model.GridFSFile
 * @since 2020/2/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Getter
@Setter
@Builder
public class GridFsObject implements java.io.Serializable {

    @Id
    @Indexed
    protected String id;
    protected String filename;
    protected long length;
    protected int chunkSize;
    protected Date uploadDate;
    protected String md5;
    protected org.bson.Document metadata;
    protected org.bson.Document extraElements;

    // 避免序列化字段
    protected transient byte[] fileBytes;
}
