package top.chokhoou.healthcardjob.common.enums;

/**
 * @author
 * @date 2021/1/25
 */
public enum ENeedCommit {
    /**
     * 需要
     */
    YES(1),
    /**
     * 不需要
     */
    NO(2);

    private final Integer value;

    ENeedCommit(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
