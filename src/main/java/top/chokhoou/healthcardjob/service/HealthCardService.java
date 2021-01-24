package top.chokhoou.healthcardjob.service;

import top.chokhoou.healthcardjob.entity.Card;
import top.chokhoou.healthcardjob.job.HealthCardJob;

import java.net.HttpCookie;

/**
 * @author ChoKhoOu
 */
public interface HealthCardService {

    /**
     * 提交健康卡
     */
    void commitHealthCard(Card card, HttpCookie[] cookies);

    /**
     * 提交健康卡
     *
     * @param studentId 学号
     * @return 成功与否
     */
    boolean commitHealthCard(String studentId);

    /**
     * 注册学生信息
     *
     * @param studentId 学号
     * @return 成功与否
     */
    boolean registerStudent(String studentId);
}
