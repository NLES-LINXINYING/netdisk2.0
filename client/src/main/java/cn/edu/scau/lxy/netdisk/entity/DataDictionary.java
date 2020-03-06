package cn.edu.scau.lxy.netdisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataDictionary {
    private long id;
    private String dict_type;
    private String dict_description;
    private long code;
    private String code_value;
}
