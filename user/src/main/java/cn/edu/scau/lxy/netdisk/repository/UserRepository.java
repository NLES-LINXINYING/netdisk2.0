package cn.edu.scau.lxy.netdisk.repository;

import cn.edu.scau.lxy.netdisk.entity.User;
import java.util.List;

public interface UserRepository {

    public int add(User user);

    public int deleteByName(String name);

    public User findByName(String name);

    public User findByNameAndPassword(String name,String password);

    public List<User> findAll(int index, int limit);//分页查询

    public int updatePassword1(String name,String password);

    public int updatePassword2(String name,String password2);

    public int updateUsedMemory(long id,long usedM);

    public int updatePhone(long id,String phone);

    public int updateEmail(long id,String email);

    public int count();
}
