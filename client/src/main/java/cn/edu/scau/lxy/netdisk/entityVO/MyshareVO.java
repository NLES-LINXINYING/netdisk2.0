package cn.edu.scau.lxy.netdisk.entityVO;

import cn.edu.scau.lxy.netdisk.entity.Myshare;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyshareVO {
    private int code;
    private String msg;
    private int count;
    private List<Myshare> data;
}
