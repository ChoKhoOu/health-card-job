package top.chokhoou.healthcardjob.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.extra.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.chokhoou.healthcardjob.common.Result;
import top.chokhoou.healthcardjob.entity.dto.RegisterDTO;
import top.chokhoou.healthcardjob.service.HealthCardService;
import top.chokhoou.healthcardjob.util.exception.BusinessException;

/**
 * @author ChoKhoOu
 */
@Slf4j
@Controller
@RequestMapping("/api/v1")
public class HealthCardController {

    @Autowired
    private HealthCardService healthCardService;

    @ResponseBody
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO registerDTO) {

        if (registerDTO.getEmail() == null && registerDTO.getPhoneNum() == null) {
            throw new BusinessException("邮箱或手机号至少需要提供一个！");
        }
        if (registerDTO.getEmail() != null) {
            Validator.validateEmail(registerDTO.getEmail(), "邮箱格式不正确");
        }
        if (registerDTO.getPhoneNum() != null) {
            Validator.validateMobile(registerDTO.getPhoneNum(), "手机号格式不正确");
        }
        Validator.validateNumber(registerDTO.getStudentId(), "学号非法");
        boolean b = healthCardService.registerStudent(registerDTO);

        if (!b) {
            return Result.failed("绑定失败");
        }
        return Result.succeed();
    }

    @ResponseBody
    @GetMapping("/commit/{studentId}")
    public Result commit(@PathVariable("studentId") String studentId) {
        healthCardService.commitHealthCard(studentId);
        return Result.succeed();
    }

}
