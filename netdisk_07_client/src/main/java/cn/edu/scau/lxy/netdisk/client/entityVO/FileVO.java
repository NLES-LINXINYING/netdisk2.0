package cn.edu.scau.lxy.netdisk.client.entityVO;

import cn.edu.scau.lxy.netdisk.client.entity.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVO {
    private int code;
    private String msg;
    private int count;
    private List<File> data;
}
