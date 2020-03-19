package cn.edu.scau.lxy.netdisk.common.entity;

/**
 * @author linxinying
 * @description 状态码类，前后端统一数据，一般不修改
 * @date 2020/3/16 20:28
 */
public class StatusCode {
    public static final int OK=0;//成功
    public static final int ERROR=20001;//失败
    public static final int LOGINERROR=20002;//用户名或密码错误
    public static final int ACCESSERROR=20003;//权限不足
    public static final int REMOTEERROR=20004;//远程调用失败
    public static final int REPEATERROR=20005;//重复操作
}
