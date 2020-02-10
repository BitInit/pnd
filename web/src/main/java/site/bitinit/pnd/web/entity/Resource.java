package site.bitinit.pnd.web.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author john
 * @date 2020-01-08
 */
@Alias("resource")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Resource {
    public static final String ALIAS = "resource";
    private Long id;
    private Long size;
    private String md5;
    private String path;
    @Builder.Default
    private Integer link = 0;

    @Builder.Default
    private Date createTime = new Date();
    @Builder.Default
    private Date updateTime = new Date();
}
