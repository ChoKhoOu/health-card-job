package top.chokhoou.healthcardjob.common.enums;

/**
 * @author ChoKhoOu
 */
public enum ECommitLogStatus {
    /**
     * 成功
     */
    SUCCESS("成功"),

    /**
     * 失败
     */
    FAILED("失败");

    private final String status;

    ECommitLogStatus(String status) {
        this.status = status;
    }

    public String getVal() {
        return this.status;
    }
}
