package cn.edu.scau.lxy.netdisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recyclebin {
    private long id;
    private Timestamp timeOfDelete;
    private Timestamp timeOfEffective;
    private String oldPath;
    private long uid;
    private long fid;
    private long ffid;
}
