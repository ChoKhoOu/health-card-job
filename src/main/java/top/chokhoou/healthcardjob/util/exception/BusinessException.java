package top.chokhoou.healthcardjob.util.exception;

/**
 * @author ChoKhoOu
 * @date 2021/1/25
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
