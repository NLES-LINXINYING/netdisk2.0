package cn.edu.scau.lxy.netdisk.base.dao;

import cn.edu.scau.lxy.netdisk.base.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleDao extends JpaRepository<Role,String>, JpaSpecificationExecutor<Role> {
}
