package cn.edu.scau.lxy.netdisk.user.repository;

import cn.edu.scau.lxy.netdisk.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    public int add(User user);

    public int deleteByName(String name);

    public User findById(long id);

    public User findByName(String name);

    public User findByNameAndPassword(String name,String password);

    public List<User> findAll(int index, int limit);//分页查询

    public int updatePassword1(String name,String password);

    public int updatePassword2(String name,String password2);

    public int updateUsedMemory(long id,long usedM);

    public int updatePhone(long id,String phone);

    public int updateEmail(long id,String email);

    public int updatePicture(long id,String picture);

    public int count();
}
