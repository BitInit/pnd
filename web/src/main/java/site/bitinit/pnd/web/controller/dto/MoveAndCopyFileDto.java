package site.bitinit.pnd.web.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author john
 * @date 2020-01-22
 */
@Setter
@Getter
public class MoveAndCopyFileDto {

    public static final String MOVE_TYPE = "move";
    public static final String COPY_TYPE = "copy";

    private List<Long> fileIds;
    private List<Long> targetIds;
    private String type;
}
