package cn.edu.scau.lxy.netdisk.repository;

import cn.edu.scau.lxy.netdisk.entity.User;

import java.util.List;

public interface UserRepository {

    public List<User> findAll(int index, int limit);//分页查询

    public int count();
}
