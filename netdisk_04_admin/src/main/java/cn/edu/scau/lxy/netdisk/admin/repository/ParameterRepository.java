package cn.edu.scau.lxy.netdisk.admin.repository;

import cn.edu.scau.lxy.netdisk.admin.entity.Parameter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterRepository {
    public int add(Parameter parameter);

    public int deleteByID(long id);

    public Parameter findByName(String name);

    public List<Object> findAll(int index,int limit);

    public int update(Parameter parameter);

    public int count();
}
