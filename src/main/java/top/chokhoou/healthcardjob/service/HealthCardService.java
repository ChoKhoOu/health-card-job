package top.chokhoou.healthcardjob.service;

import top.chokhoou.healthcardjob.entity.Card;

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
     * 注册学生信息
     *
     * @param studentId 学号
     * @return 成功与否
     */
    Card getStudent(String studentId,HttpCookie[] cookies);

    /**
     * 获取cookie
     * @return cookies
     */
    HttpCookie[] getCookies();
}
