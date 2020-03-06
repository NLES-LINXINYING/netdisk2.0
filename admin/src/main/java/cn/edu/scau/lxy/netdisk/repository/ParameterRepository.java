package cn.edu.scau.lxy.netdisk.repository;

import cn.edu.scau.lxy.netdisk.entity.Parameter;

import java.util.List;

public interface ParameterRepository {
    public int add(Parameter parameter);

    public int deleteByID(long id);

    public Parameter findByName(String name);

    public List<Parameter> findAll(int index,int limit);

    public int update(Parameter parameter);

    public int count();
}
