package com.qiang.common.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: qiang
 * @Description: 封装分页后的数据格式
 * @Date: 2019/7/31 0031 19:20
 */
@Getter
@Setter
@Component
public class PagedResult {

    /**
     * 当前页数
     */
    private int page;

    /**
     * 总页数
     */
    private Long total;

    /**
     * 总记录数
     */
    private long records;

    /**
     * 每行显示的内容
     */
    private List<?> rows;

}
