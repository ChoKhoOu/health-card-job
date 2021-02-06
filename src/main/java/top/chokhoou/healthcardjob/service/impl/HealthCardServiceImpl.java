package top.chokhoou.healthcardjob.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import top.chokhoou.healthcardjob.common.constants.CardConstant;
import top.chokhoou.healthcardjob.common.constants.CommonConstant;
import top.chokhoou.healthcardjob.common.enums.ENeedCommit;
import top.chokhoou.healthcardjob.common.enums.ESchoolApiStatus;
import top.chokhoou.healthcardjob.entity.Card;
import top.chokhoou.healthcardjob.entity.dto.CardDTO;
import top.chokhoou.healthcardjob.service.HealthCardService;
import top.chokhoou.healthcardjob.util.cache.CacheHolder;

import top.chokhoou.healthcardjob.util.exception.ServiceException;

import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ChoKhoOu
 */
@Slf4j
@Service
public class HealthCardServiceImpl implements HealthCardService {

    private static final String TITLE_FORMAT = "%s %s 健康卡填报";

    @Override
    @Retryable(value = Exception.class, backoff = @Backoff(delay = 5000, multiplier = 2))
    public void commitHealthCard(Card card, HttpCookie[] cookies) {

        CardDTO cardDTO = buildCardDTO(card);
        JSONObject body = new JSONObject().set("entity", cardDTO);
        HttpResponse response = HttpRequest.post(CommonConstant.COMMIT_URL)
                .body(body.toString())
                .cookie(cookies)
                .header("Referer", CommonConstant.COMMIT_REFERER)
                .timeout(3000)
                .execute();

        int status = response.getStatus();
        if (status != HttpStatus.HTTP_OK) {
            log.error("fail to call card commit api: studentId={}, http status={}, cookie={}", card.getGh(), status, cookies);
            throw new ServiceException();
        }
        String result = JSONUtil.parseObj(response.body()).getStr("result");
        if (!ESchoolApiStatus.SUCCESS.getStatus().equals(result)) {
            log.error("fail to call card commit api: studentId={},response body={}", card.getGh(), response.body());
            throw new ServiceException();
        }

        //success
        getCommitLogMap().put(card.getGh(), "Success");
    }


    @Override
    public Card getStudent(String studentId, HttpCookie[] cookies) {

        JSONObject root = new JSONObject();
        JSONObject param = new JSONObject();
        root.set("querySqlId", "com.sudytech.work.jlzh.jkxxtb.jkxxcj.queryNear");
        param.set("empcode", studentId);
        root.set("params", param);
        HttpResponse getInfo = HttpRequest
                .post(CommonConstant.QUERY_URL)
                .cookie(cookies)
                .body(root.toString())
                .execute();

        List<String> list = JSONUtil.toList(JSONUtil.parseObj(getInfo.body()).getJSONArray("list").toString(), String.class);
        if (list == null || list.isEmpty()) {
            return null;
        }
        Card card = JSONUtil.parseObj(list.get(0), new JSONConfig().setIgnoreCase(true)).toBean(Card.class);
        card.setNeedCommit(ENeedCommit.YES.getValue())
                .setCn(CardConstant.CN)
                .set_ext(CardConstant.EXT)
                .set__type(CardConstant.TYPE)
                .setBz(CardConstant.BZ);
        card.setId(null);
        return card;
    }

    @Override
    public HttpCookie[] getCookies() {
        HttpResponse executionResponse = HttpRequest
                .get(CommonConstant.EXECUTION_URL)
                .timeout(2000)
                .execute();
        String body = executionResponse.body();
        String[] split = body.split("<input type=\"hidden\" name=\"execution\" value=\"");
        if (split.length <= 1) {
            log.error("execution not found");
        }

        String[] bodySplit = split[1].split("\"");
        String execution = bodySplit[0];
        HttpCookie jSessionId = executionResponse.getCookie(CommonConstant.JSESSION_ID);

        // login
        Map<String, Object> form = new HashMap<>();
        form.put("username", "04171208");
        form.put("password", "034339");
        form.put("execution", execution);
        form.put("_eventId", "submit");
        form.put("loginType", "1");
        form.put("submit", "登 录");
        HttpResponse response = HttpRequest.post(CommonConstant.LOGIN_URL)
                .cookie(jSessionId)
                .form(form)
                .execute();
        String loginBody = response.body();
        JSONObject jsonObject = JSONUtil.parseObj(loginBody);

        String CastgcCookie = jsonObject.getJSONObject("cookie").getStr(CommonConstant.CASTGC);
        LinkedList<HttpCookie> httpCookies = new LinkedList<>();
        httpCookies.add(new HttpCookie(CommonConstant.CASTGC, CastgcCookie));
        httpCookies.add(jSessionId);

        return httpCookies.toArray(new HttpCookie[0]);
    }

    @Recover
    private void fallback(Exception ex, Card card, HttpCookie[] cookies) {
        getCommitLogMap().put(card.getGh(), "Maximum number of retries: " + ex.getMessage());
        log.error("commit card fail: studentId={}, reason=exceeded maximum number of retries.", card.getGh());
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
                .setTjsj(DateUtil.format(now, new SimpleDateFormat(CommonConstant.DATE_FORMAT_WITHOUT_SECOND)))
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
                .setSqbmid(card.getSqbmid());
    }

    private Map<String, String> getCommitLogMap() {
        return getCommitLogMap(new DateTime());
    }

    private Map<String, String> getCommitLogMap(DateTime key) {
        return getCommitLogMap(DateUtil.format(key, new SimpleDateFormat(CommonConstant.DATE_FORMAT_WITHOUT_SECOND)));
    }

    private Map<String, String> getCommitLogMap(String key) {
        return CacheHolder.getStudentCommitLogCache().get(key, e -> new ConcurrentHashMap<>(16));
    }


}
