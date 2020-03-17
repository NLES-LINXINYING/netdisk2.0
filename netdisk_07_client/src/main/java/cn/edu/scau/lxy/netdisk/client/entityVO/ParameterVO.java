package cn.edu.scau.lxy.netdisk.client.entityVO;

import cn.edu.scau.lxy.netdisk.client.entity.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterVO {
    private int code;
    private String msg;
    private int count;
    private List<Parameter> data;
}
