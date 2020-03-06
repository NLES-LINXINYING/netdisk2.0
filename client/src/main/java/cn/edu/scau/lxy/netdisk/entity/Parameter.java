package cn.edu.scau.lxy.netdisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter {
    private long id;
    private String name;
    private String value;
    private String description;
}
