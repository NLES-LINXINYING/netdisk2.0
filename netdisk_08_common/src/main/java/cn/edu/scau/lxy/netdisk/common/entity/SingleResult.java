package cn.edu.scau.lxy.netdisk.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author linxinying
 * @description 返回数据包装类（单条记录），layui特定格式，不可修改
 * @date 2020/3/16 20:28
 */
@Data
@AllArgsConstructor
public class SingleResult {
    private int code;
    private String msg;
    private int count;
    private Object data;
}
