package cn.edu.scau.lxy.netdisk.user.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LoginHistory {
    private long id;
    private Timestamp time;
    private String ip;
    private String browser;
    private String wayOfLogin;
    private String device;
    private long uid;
}
