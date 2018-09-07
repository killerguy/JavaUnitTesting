package com.mukul.dynamo.domain;

public enum DepartmentEnum {

    ACCOUNTS(1),
    ADMIN(2),
    IT(3);

    private int departmentId;

    public int getDepartmentId()
    {
        return this.departmentId;
    }

    private DepartmentEnum(int departmentId)
    {
        this.departmentId = departmentId;
    }

}
