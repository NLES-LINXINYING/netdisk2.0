package cn.edu.scau.lxy.netdisk.repository;

import cn.edu.scau.lxy.netdisk.entity.Parameter;

public interface ParameterRepository {
    public Parameter findByName(String name);
}
