package com.practice.mall.exception;

/**
 * 異常枚舉
 */
public enum MallExceptionEnum {
    NEED_USER_NAME(10001, "用戶名不能為空"),
    NEED_PASSWORD(10002, "密碼不能為空"),
    PASSWORD_TOO_SHORT(10002, "密碼不能小於8位"),
    NAME_EXISTED(10004, "不允許重名"),
    INSERT_FAILED(10005, "插入失敗，請重試"),
    WRONG_PASSWORD(10006, "密碼錯誤"),
    NEED_LOGIN(10007, "用戶未登入"),
    UPDATE_FAILED(10008, "更新失敗"),
    NEED_ADMIN(10009, "無管理員權限"),
    PARA_NOT_NULL(10010, "參數不能為空"),
    CREATE_FAILED(10011, "新增失敗"),
    REQUEST_PARAM_ERROR(10012, "參數錯誤"),
    DELETE_FAILED(10013, "刪除失敗"),
    MKDIR_FAILED(10014, "文件夾創建失敗"),
    UPLOAD_FAILED(10015, "圖片上傳失敗"),
    NOT_SALE(10016, "不可售出，商品已下架或不存在此商品"),
    NOT_ENOUGH(10017, "商品庫存不足"),
    CART_EMPTY(10018, "購物車已勾選的商品為空"),
    NO_ENUM(10019, "未找到對應的枚舉"),
    NO_ORDER(10020, "訂單不存在"),
    NOT_YOUR_ORDER(10021, "不是此用戶的訂單"),
    WRONG_ORDER_STATUS(10022, "訂單狀態不符"),
    WRONG_EMAIL(10023, "非正確的email地址"),
    EMAIL_ALREADY_BEEN_REGISTERED(10024, "email已被註冊"),
    EMAIL_ALREADY_BEEN_SEND(10025, "email已發送，若無法收到，請稍候再試"),
    NEED_EMAIL_ADDRESS(10026, "email不能為空"),
    NEED_VERIFICATION_CODE(10027, "驗證碼不能為空"),
    WRONG_VERIFICATION_CODE(10028, "驗證碼錯誤"),
    TOKEN_EXPIRED(10029, "token過期"),
    TOKEN_WRONG(10030, "token解析失敗"),
    CANCEL_WRONG_ORDER_STATUS(10031, "訂單狀態有誤，付款後暫不支持取消訂單"),
    PAY_WRONG_ORDER_STATUS(10032, "訂單狀態有誤，僅能在未付款時付款"),
    DELIVER_WRONG_ORDER_STATUS(10033, "訂單狀態有誤，僅能在付款後發貨"),
    FINISH_WRONG_ORDER_STATUS(10034, "訂單狀態有誤，僅能在發貨後完單"),
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
