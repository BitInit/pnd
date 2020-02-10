package site.bitinit.pnd.web.controller.dto;

import lombok.Getter;
import lombok.Setter;
import site.bitinit.pnd.web.entity.File;

/**
 * @author john
 * @date 2020-01-27
 */
@Setter
@Getter
public class MergeFileDto extends File {

    private String identifier;
}
