package cn.edu.scau.lxy.netdisk.file.repository;

import cn.edu.scau.lxy.netdisk.file.entity.Folder;

import java.util.List;

public interface FolderRepository {
    public int add(Folder folder);
    public int deleteById(long id);
    public Folder findById(long id);
    public List<Folder> findByPath(long uid,String path);
    public List<Folder> fuzzyQueryByPath(String path);
    public List<Folder> findByName(long uid,String name);
    public Folder findByNameAndPath(String name,String path);
    public int updateName(long id,String name);
    public int updatePath(long id,String path);
    public int count();
}
