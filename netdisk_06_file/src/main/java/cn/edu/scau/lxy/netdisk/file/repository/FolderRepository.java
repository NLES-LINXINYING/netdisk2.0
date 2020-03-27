package cn.edu.scau.lxy.netdisk.file.repository;

import cn.edu.scau.lxy.netdisk.file.entity.Folder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository {
    public int add(Folder folder);
    public int deleteById(long id);
    public Folder findById(long id);
    public List<Object> findByPath(long uid,String path);
    public List<Folder> fuzzyQueryByPath(String path);  //模糊查找
    public List<Object> findByName(long uid,String name);
    public Folder findByNameAndPath(String name,String path);
    public int updateName(long id,String name);
    public int updatePath(long id,String path);
    public int count();
    public int countByUid(long uid);
}
