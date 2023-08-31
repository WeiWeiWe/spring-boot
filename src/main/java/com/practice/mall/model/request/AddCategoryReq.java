package com.practice.mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddCategoryReq {
    @Size(min = 2, max = 5)
    @NotNull(message = "name不能為null")
    private String name;
    @Max(3)
    @NotNull(message = "type不能為null")
    private Integer type;
    @NotNull(message = "parentId不能為null")
    private Integer parentId;
    @NotNull(message = "orderNum不能為null")
    private Integer orderNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
