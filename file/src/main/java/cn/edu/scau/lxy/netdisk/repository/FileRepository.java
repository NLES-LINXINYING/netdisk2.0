package cn.edu.scau.lxy.netdisk.repository;

import cn.edu.scau.lxy.netdisk.entity.File;

import java.util.List;

public interface FileRepository {
    public int add(File file);
    public int deleteById(long id);
    public File findById(long id);
    public List<File> findByUid(long uid);
    public List<File> findByPath(long uid,String path);
    public List<File> fuzzyQueryByPath(String Path);
    public List<File> findByName(long uid,String name);
    public List<File> findByType(long uid,long type);
    public List<File> findByNameAndType(long uid,long type,String name);
    public File findByNameAndPath(String name,String path);
    public int updateName(long id, String name);
    public int updatePath(long id,String path);
    public int count();
}
