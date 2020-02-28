package cn.edu.scau.lxy.netdisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Folder {
    private long id;
    private String name;
    private String path;
    private Timestamp modifyTime;
    private long uid;
    private long ffid;
}
