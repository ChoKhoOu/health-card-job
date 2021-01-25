package top.chokhoou.healthcardjob.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.ResultSet;

/**
 * @author ChoKhoOu
 * @date 2021/1/25
 */
@Data
@Accessors(chain = true)
public class Result<T> {
    private Integer code;

    private boolean success;

    private String msg;

    private T data;

    private Result() {
    }

    public static <T> Result<T> succeed() {
        return new Result<T>().setSuccess(true).setCode(0);
    }

    public static <T> Result<T> succeed(T data) {
        return new Result<T>().setSuccess(true).setCode(0).setData(data);
    }

    public static <T> Result<T> failed() {
        return new Result<T>().setSuccess(true).setCode(-1);
    }

    public static <T> Result<T> failed(String msg) {
        return new Result<T>().setSuccess(false).setCode(-1).setMsg(msg);
    }

    public static <T> Result<T> failed(Integer code, String msg) {
        return new Result<T>().setSuccess(false).setCode(code).setMsg(msg);
    }

}
