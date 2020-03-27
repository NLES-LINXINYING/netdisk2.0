package cn.edu.scau.lxy.netdisk.file.repository;

import cn.edu.scau.lxy.netdisk.file.entity.File;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository {
    public int add(File file);
    public int deleteById(long id);
    public File findById(long id);
    public List<Object> findByUid(long uid);
    public List<Object> findByPath(long uid,String path);
    public List<File> fuzzyQueryByPath(String Path);
    public List<Object> findByName(long uid,String name);
    public List<Object> findByType(long uid,long type);
    public List<Object> findByNameAndType(long uid,long type,String name);
    public File findByNameAndPath(String name,String path);
    public int updateName(long id, String name);
    public int updatePath(long id,String path);
    public int count();
    public int countByUid(long uid);
}
