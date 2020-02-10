package site.bitinit.pnd.web.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;
import site.bitinit.pnd.web.config.FileType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author john
 * @date 2020-01-08
 */
@Alias("file")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File {
    public static final String ALIAS = "file";
    public static final File ROOT_FILE = File.builder()
            .id(0L).fileName("全部文件").type(FileType.FOLDER.toString()).parentId(0L)
            .build();

    private Long id;
    @NotNull(message = "parentId不能为null")
    private Long parentId;
    @NotEmpty(message = "fileName不能为空")
    private String fileName;
    @NotEmpty(message = "type不能为空")
    private String type;
    private Long size;
    private Long resourceId;

    @Builder.Default
    private Date createTime = new Date();
    @Builder.Default
    private Date updateTime = new Date();
}
