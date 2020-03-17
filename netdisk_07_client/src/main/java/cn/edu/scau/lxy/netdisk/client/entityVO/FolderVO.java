package cn.edu.scau.lxy.netdisk.client.entityVO;

import cn.edu.scau.lxy.netdisk.client.entity.Folder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderVO {
    private int code;
    private String msg;
    private int count;
    private List<Folder> data;
}
