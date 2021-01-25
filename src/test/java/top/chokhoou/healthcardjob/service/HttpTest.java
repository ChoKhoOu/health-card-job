package top.chokhoou.healthcardjob.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import top.chokhoou.healthcardjob.common.constants.CommonConstant;
import top.chokhoou.healthcardjob.entity.Card;
import top.chokhoou.healthcardjob.entity.dto.CardDTO;

import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpTest {

    private static final String TITLE_FORMAT = "%s %s 健康卡填报";

    private static final String COMMIT_URL = "https://wxapp.jluzh.com/proxy_my_requests/?url=https://work.jluzh.edu.cn" +
            "/default/work/jlzh/jkxxtb/com.sudytech.portalone.base.db.saveOrUpdate.biz.ext";
    private static final String COMMIT_REFERER = "https://servicewechat.com/wxfedb987db81a675e/48/page-frame.html";

    @Test
    public void testHttp() {
        HttpResponse execute = HttpRequest
                .get("https://authserver.jluzh.edu.cn/cas/login?service=https%3A%2F%2Fmy.jluzh.edu.cn%2F")
                .timeout(2000)
                .execute();
        String body = execute.body();

        String[] split = body.split("<input type=\"hidden\" name=\"execution\" value=\"");
        if (split.length <= 1) {

        }
        String[] split1 = split[1].split("\"");
        String execution = split1[0];
//        System.out.println(execution);
//        System.out.println(execute.getCookie("JSESSIONID"));
//        System.out.println(split.length);
        HttpCookie sessionIdCookie = execute.getCookie("JSESSIONID");

        Map<String, Object> form = new HashMap<>();
        form.put("username", "04171208");
        form.put("password", "034339");
        form.put("execution", execution);
        form.put("_eventId", "submit");
        form.put("loginType", "1");
        form.put("submit", "登 录");
        HttpResponse response = HttpRequest.post("https://wxapp.jluzh.com/proxy_cas_login/")
                .cookie(sessionIdCookie)
                .form(form)
                .execute();
        String body1 = response.body();
        System.out.println(JSONUtil.isJson(body1));
        JSONObject jsonObject = JSONUtil.parseObj(body1);
        String CASTGC = jsonObject.getJSONObject("cookie").getStr("CASTGC");
        System.out.println(CASTGC);


        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject1.set("querySqlId", "com.sudytech.work.jlzh.jkxxtb.jkxxcj.queryNear");
        jsonObject2.set("empcode", "99999999");
        jsonObject1.set("params", jsonObject2);
        HttpResponse getInfo = HttpRequest
                .post("https://wxapp.jluzh.com/proxy_my_requests/?url=https://work.jluzh.edu.cn/default/work/jlzh/jkxxtb/com.sudytech.portalone.base.db.queryBySqlWithoutPagecond.biz.ext")
                .cookie(new HttpCookie("CASTGC", CASTGC), sessionIdCookie)
                .body(jsonObject1.toString())
                .execute();
        System.out.println(getInfo.body());
        List<String> list = JSONUtil.toList(JSONUtil.parseObj(getInfo.body()).getJSONArray("list").toString(), String.class);
        Card card = JSONUtil.parseObj(list.get(0), new JSONConfig().setIgnoreCase(true)).toBean(Card.class);
        System.out.println(JSONUtil.toJsonStr(card));


        List<String> cn = new LinkedList<>();
        cn.add("本人承诺登记后、到校前不再前往其他地区");
        card.set_ext("{}")
                .set__type("sdo:com.sudytech.work.jlzh.jkxxtb.jkxxcj.TJlzhJkxxtb")
                .setBz("(该健康卡通过 我的吉珠小程序 填写)")
                .setCn(cn);


        CardDTO cardDTO = buildCardDTO(card);
        JSONObject body2 = new JSONObject().set("entity", cardDTO);
        HttpResponse response2 = HttpRequest.post(COMMIT_URL)
                .body(body2.toString())
                .cookie(new HttpCookie("CASTGC", CASTGC), sessionIdCookie)
                .header("Referer", COMMIT_REFERER)
                .timeout(3000)
                .execute();

        System.out.println(response2.body());
    }

    private CardDTO buildCardDTO(Card card) {
        Date now = new Date();
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
                .setSqbmid(card.getSqbmid())
                .setId(card.getId());
    }
}
