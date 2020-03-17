package cn.edu.scau.lxy.netdisk.base.service;

import cn.edu.scau.lxy.netdisk.base.dao.RoleDao;
import cn.edu.scau.lxy.netdisk.base.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.edu.scau.lxy.netdisk.common.util.SnowflakeIdWorker;

import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<Role> findAll(){
        return roleDao.findAll();
    }

    public Role findById(String id){
        Role role=(Role)redisTemplate.opsForValue().get("role_"+id);
        if(role==null){
            role=roleDao.findById(id).get();
            redisTemplate.opsForValue().set("role_"+id,role);
        }
        return role;
    }

    public void save(Role role){
        role.setId(snowflakeIdWorker.nextId()+"");
        roleDao.save(role);
    }

    public void update(Role role){
        redisTemplate.delete("role_"+role.getId());
        roleDao.save(role);
    }

    public void deleteById(String id){
        redisTemplate.delete("role_"+id);
        roleDao.deleteById(id);
    }
}
