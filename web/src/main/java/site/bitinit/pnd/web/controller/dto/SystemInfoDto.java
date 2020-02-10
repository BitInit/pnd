package site.bitinit.pnd.web.controller.dto;

import lombok.*;

/**
 * @author john
 * @date 2020-02-10
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfoDto {

    private Long totalCap;
    private Long usableCap;

    private Integer totalNum;
    private Integer folderNum;
    private Integer fileNum;

    private Integer videoNum;
    private Integer audioNum;

}
