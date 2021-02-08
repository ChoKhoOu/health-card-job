package top.chokhoou.healthcardjob.common.constants;

/**
 * @author ChoKhoOu
 */
public interface CommonConst {
    String COLON = ":";

    String DATE_FORMAT_WITHOUT_SECOND = "yyyy-MM-dd HH:mm";

    /**
     * cookie key
     */
    String CASTGC = "CASTGC";
    String JSESSION_ID = "JSESSIONID";

    String COMMIT_URL = "https://wxapp.jluzh.com/proxy_my_requests/?url=https://work.jluzh.edu.cn" +
            "/default/work/jlzh/jkxxtb/com.sudytech.portalone.base.db.saveOrUpdate.biz.ext";

    String COMMIT_REFERER = "https://servicewechat.com/wxfedb987db81a675e/48/page-frame.html";

    String EXECUTION_URL = "https://authserver.jluzh.edu.cn/cas/login?service=https%3A%2F%2Fmy.jluzh.edu.cn%2F";

    String LOGIN_URL = "https://wxapp.jluzh.com/proxy_cas_login/";

    String QUERY_URL = "https://wxapp.jluzh.com/proxy_my_requests/?url=https://work.jluzh.edu.cn/default/work/jlzh/" +
            "jkxxtb/com.sudytech.portalone.base.db.queryBySqlWithoutPagecond.biz.ext";
}
