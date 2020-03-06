package cn.edu.scau.lxy.netdisk.entity;

import lombok.Builder;
import lombok.Data;

@Data
public class User {
    private long id;
    private String name;
    private String password;
    private String password2;
    private double totalMemory;
    private double usedMemory;
    private String phone;
    private String email;
    private String picture;
    private int type;
}
