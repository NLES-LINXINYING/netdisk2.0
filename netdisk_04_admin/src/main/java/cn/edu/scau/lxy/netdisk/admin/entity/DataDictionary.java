package cn.edu.scau.lxy.netdisk.admin.entity;

import lombok.Data;

@Data
public class DataDictionary {
    private long id;
    private String type;
    private String description;
    private long code;
    private String value;
}
