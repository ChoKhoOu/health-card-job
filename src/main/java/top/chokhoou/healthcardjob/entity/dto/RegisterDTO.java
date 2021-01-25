package top.chokhoou.healthcardjob.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ChoKhoOu
 * @date 2021/1/25
 */
@Data
@Accessors(chain = true)
public class RegisterDTO {
    private String studentId;

    private String phoneNum;

    private String email;
}
