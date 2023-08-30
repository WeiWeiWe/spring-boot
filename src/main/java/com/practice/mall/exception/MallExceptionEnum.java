package com.practice.mall.exception;

/**
 * 異常枚舉
 */
public enum MallExceptionEnum {
    NEED_USER_NAME(10001, "用戶名不能為空"),
    NEED_PASSWORD(10002, "密碼不能為空"),
    PASSWORD_TOO_SHORT(10002, "密碼不能小於8位"),
    NAME_EXISTED(10004, "不允許重名，註冊失敗"),
    INSERT_FAILED(10005, "插入失敗，請重試"),
    WRONG_PASSWORD(10006, "密碼錯誤"),
    NEED_LOGIN(10007, "用戶未登入"),
    UPDATE_FAILED(10008, "更新失敗"),
    NEED_ADMIN(10009, "無管理員權限"),
    SYSTEM_ERROR(20000, "系統異常");

    Integer code;

    String msg;

    MallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
