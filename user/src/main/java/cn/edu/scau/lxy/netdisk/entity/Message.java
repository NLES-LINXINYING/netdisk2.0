package cn.edu.scau.lxy.netdisk.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Message {
    private long id;
    private String content;
    private Timestamp time;
    private long uid1;
    private long uid2;
}
