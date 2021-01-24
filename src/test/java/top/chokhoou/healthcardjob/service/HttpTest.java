package top.chokhoou.healthcardjob.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import top.chokhoou.healthcardjob.entity.Card;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpTest {
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
        jsonObject2.set("empcode", "04171211");
        jsonObject1.set("params", jsonObject2);
        HttpResponse getInfo = HttpRequest
                .post("https://wxapp.jluzh.com/proxy_my_requests/?url=https://work.jluzh.edu.cn/default/work/jlzh/jkxxtb/com.sudytech.portalone.base.db.queryBySqlWithoutPagecond.biz.ext")
                .cookie(new HttpCookie("CASTGC", CASTGC), sessionIdCookie)
                .body(jsonObject1.toString())
                .execute();
        List<String> list = JSONUtil.toList(JSONUtil.parseObj(getInfo.body()).getJSONArray("list").toString(), String.class);
        System.out.println(JSONUtil.toBean(list.get(0), Card.class));
    }
}
