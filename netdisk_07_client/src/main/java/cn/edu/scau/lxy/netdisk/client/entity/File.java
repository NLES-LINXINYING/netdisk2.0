package cn.edu.scau.lxy.netdisk.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private long id;
    private String name;
    private String path;
    private long type;
    private long size;
    private Timestamp modifyTime;
    private long uid;
    private long ffid;
}
