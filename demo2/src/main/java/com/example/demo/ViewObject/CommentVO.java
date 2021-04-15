package com.example.demo.ViewObject;

import lombok.Data;

import java.util.List;

/**
 * @author ：chenjiajun
 * @description：供layui获取后端数据的封装对象,注意code,count都要为String类型
 * @date ：2021/3/25 21:58
 */
@Data
public class CommentVO<T> {
    private Integer code;
    private String msg;
    private Long count;
    private List<T> res;

    public CommentVO(Integer code, String msg, Long count, List<T> res) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.res = res;
    }
}
