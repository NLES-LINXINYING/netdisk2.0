package cn.edu.scau.lxy.netdisk.entity;

import lombok.Data;

@Data
public class DataDictionary {
    private long id;
    private String dict_type;
    private String dict_description;
    private long code;
    private String code_value;
}
