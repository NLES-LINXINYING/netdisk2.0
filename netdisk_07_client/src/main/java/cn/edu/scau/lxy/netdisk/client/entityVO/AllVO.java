package cn.edu.scau.lxy.netdisk.client.entityVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllVO {
    private int code;
    private String msg;
    private int count;
    private List<Object> data;
}
