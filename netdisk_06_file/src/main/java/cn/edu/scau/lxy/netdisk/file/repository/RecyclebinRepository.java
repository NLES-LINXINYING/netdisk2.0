package cn.edu.scau.lxy.netdisk.file.repository;

import cn.edu.scau.lxy.netdisk.file.entity.Recyclebin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecyclebinRepository {
    public int addFile(Recyclebin recyclebin);
    public int addFolder(Recyclebin recyclebin);
    public int delete(long id);
    public int deleteByFFid(long ffid);
    public int deleteByFid(long fid);
    public List<Recyclebin> findAll(long uid);
    public Recyclebin findById(long id);
    public int count();
}
