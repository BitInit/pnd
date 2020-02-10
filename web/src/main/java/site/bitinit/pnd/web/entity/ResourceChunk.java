package site.bitinit.pnd.web.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author john
 * @date 2020-01-27
 */
@Alias("resourceChunk")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceChunk {

    public static final String ALIAS = "resourceChunk";
    private Integer chunkNumber;
    private Long chunkSize;
    private Long currentChunkSize;
    private Long totalSize;
    private String identifier;
    private String filename;
    private String relativePath;
    private Integer totalChunks;

    private MultipartFile file;

    @Builder.Default
    private Date createTime = new Date();
    @Builder.Default
    private Date updateTime = new Date();

}
