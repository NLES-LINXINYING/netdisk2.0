package cn.edu.scau.lxy.netdisk.repository;

import cn.edu.scau.lxy.netdisk.entity.Recyclebin;

import java.util.List;

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
