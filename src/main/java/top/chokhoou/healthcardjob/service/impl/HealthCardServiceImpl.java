package top.chokhoou.healthcardjob.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import top.chokhoou.healthcardjob.common.constants.CommonConstant;
import top.chokhoou.healthcardjob.common.enums.ECommitLogStatus;
import top.chokhoou.healthcardjob.dao.CardCommitLogDao;
import top.chokhoou.healthcardjob.dao.CardDao;
import top.chokhoou.healthcardjob.entity.Card;
import top.chokhoou.healthcardjob.entity.CardCommitLog;
import top.chokhoou.healthcardjob.entity.dto.CardDTO;
import top.chokhoou.healthcardjob.service.HealthCardService;
import top.chokhoou.healthcardjob.util.exception.ServiceException;

import java.net.HttpCookie;
import java.time.format.DateTimeFormatter;

/**
 * @author ChoKhoOu
 */
@Slf4j
@Service
public class HealthCardServiceImpl implements HealthCardService {

    private static final String TITLE_FORMAT = "%s %s 健康卡填报";

    private static final String COMMIT_URL = "https://wxapp.jluzh.com/proxy_my_requests/?url=https://work.jluzh.edu.cn" +
            "/default/work/jlzh/jkxxtb/com.sudytech.portalone.base.db.saveOrUpdate.biz.ext";
    private static final String COMMIT_REFERER = "https://servicewechat.com/wxfedb987db81a675e/48/page-frame.html";

    @Autowired
    private CardDao cardDao;
    @Autowired
    private CardCommitLogDao cardCommitLogDao;


    @Override
    @Retryable(value = Exception.class, backoff = @Backoff(delay = 5000, multiplier = 2))
    public void commitHealthCard(Card card, HttpCookie[] cookies) {
        CardDTO cardDTO = buildCardDTO(card);
        JSONObject body = new JSONObject().set("entity", cardDTO);
        HttpResponse response = HttpRequest.post(COMMIT_URL)
                .body(body.toString())
                .cookie(cookies)
                .header("Referer", COMMIT_REFERER)
                .timeout(3000)
                .execute();

        int status = response.getStatus();
        if (status != HttpStatus.HTTP_OK) {
            log.error("fail to call card commit api: studentId={}, http status={}, cookie={}", card.getGh(), status, cookies);
            throw new ServiceException();
        }
        String result = JSONUtil.parseObj(response.body()).getStr("result");
        if (!"1".equals(result)) {
            log.error("fail to call card commit api: studentId={},response body={}", card.getGh(), response.body());
            throw new ServiceException();
        }

        // success
        cardCommitLogDao.save(new CardCommitLog()
                .setStudentId(card.getGh())
                .setState(ECommitLogStatus.SUCCESS.getVal()));
    }

    @Recover
    public void fallback(Exception ex, Card card, HttpCookie[] cookies) {
        log.error("commit card fail: studentId={}, reason=exceeded maximum number of retries.", card.getGh());
        // TODO 发送短信 or 邮件

        // failed
        cardCommitLogDao.save(new CardCommitLog()
                .setStudentId(card.getGh())
                .setState(ECommitLogStatus.FAILED.getVal()));
    }

    @Override
    public boolean commitHealthCard(String studentId) {
        return false;
    }

    @Override
    public boolean registerStudent(String studentId) {

        return false;
    }

    private CardDTO buildCardDTO(Card card) {
        DateTime now = new DateTime();
        return new CardDTO().set__type(card.get__type())
                .set_ext(card.get_ext())
                .setBz(card.getBz())
                .setCn(card.getCn())
                .setJkqk(card.getJkqk())
                .setQsjkzk(card.getQsjkzk())
                .setJrtw(card.getJrtw())
                .setGrjkzk(card.getGrjkzk())
                .setJtdz(card.getJtdz())
                .setXrywz(card.getXrywz())
                .setSfjwhygjdq(card.getSfjwhygjdq())
                .setSfjwhy(card.getSfjwhy())
                .setSfjchbjry(card.getSfjchbjry())
                .setSfqwhb(card.getSfqwhb())
                .setJqqx(card.getJqqx())
                .setXjzdz(card.getXjzdz())
                .setTjsj(DateUtil.format(now, DateTimeFormatter.ofPattern(CommonConstant.DATE_FORMAT_WITHOUT_SECOND)))
                .setTbrq(DateUtil.formatDate(now))
                .setLxdh(card.getLxdh())
                .setSsh(card.getSsh())
                .setFdymc(card.getFdymc())
                .setBjmc(card.getBjmc())
                .setZymc(card.getZymc())
                .setNj(card.getNj())
                .setSqbmcc(card.getSqbmcc())
                .setXb(card.getXb())
                .setGh(card.getGh())
                .setSqrmc(card.getSqrmc())
                .setBt(String.format(TITLE_FORMAT, DateUtil.formatDate(now), card.getSqrmc()))
                .setRysf(card.getRysf())
                .setFdygh(card.getFdygh())
                .setSqbmid(card.getSqbmid())
                .setId(card.getId());
    }
}
