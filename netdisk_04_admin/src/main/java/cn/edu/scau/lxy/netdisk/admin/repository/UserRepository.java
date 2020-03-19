package cn.edu.scau.lxy.netdisk.admin.repository;

import cn.edu.scau.lxy.netdisk.admin.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    public List<Object> findAll(int index, int limit);//分页查询

    public int count();
}
