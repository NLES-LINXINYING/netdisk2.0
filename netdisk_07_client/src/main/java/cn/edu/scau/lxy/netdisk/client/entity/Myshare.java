package cn.edu.scau.lxy.netdisk.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Myshare {
    private long id;
    private long timesOfBrowse;
    private long timesOfSave;
    private long timesOfDownload;
    private Timestamp timeOfShare;
    private Timestamp timeOfInvalid;
    private String code;
    private String link;
    private long uid;
    private long fid;
    private long ffid;
}
