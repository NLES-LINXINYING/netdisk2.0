package cn.edu.scau.lxy.netdisk.repository;

import cn.edu.scau.lxy.netdisk.entity.DataDictionary;

import java.util.List;

public interface DataDictionaryRepository {

    public int add(DataDictionary dataDictionary);

    public int deleteByID(long id);

    public DataDictionary findByID(long id);

    public List<DataDictionary> findAll(int index, int limit);//分页查询

    public int update(long id, String type, String description, long code, String value);

    public int count();
}
