package com.petrovskiy.mds.service.exception;

public class ExceptionCode {

    public static final int BAD_REQUEST = 40000;
    public static final int UNREADABLE_MESSAGE = 40001;
    public static final int EMPTY_OBJECT = 40010;
    public static final int NON_EXISTENT_ENTITY = 40410;
    public static final int NON_EXISTENT_PAGE = 40051;

    public static final int INVALID_NAME = 40030;

    public static final int USED_ENTITY = 40910;
    public static final int DUPLICATE_NAME = 40911;
    public static final int DATA_BASE_ERROR = 50300;

    private ExceptionCode() {
    }
}
