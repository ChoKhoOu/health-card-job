package top.chokhoou.healthcardjob.common.enums;

/**
 * 学校Api状态
 *
 * @author ChoKhoOu
 * @date 2021/1/25
 */
public enum ESchoolApiStatus {
    /**
     * 成功
     */
    SUCCESS("1");


    private final String status;

    ESchoolApiStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
