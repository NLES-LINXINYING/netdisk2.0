package cn.edu.scau.lxy.netdisk.user.repository;

import cn.edu.scau.lxy.netdisk.user.entity.File;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository {
    public List<File> findByUid(long uid);
}
