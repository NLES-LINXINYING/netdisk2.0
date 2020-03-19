package cn.edu.scau.lxy.netdisk.user.repository;


import cn.edu.scau.lxy.netdisk.user.entity.Parameter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterRepository {
    public Parameter findByName(String name);
}
