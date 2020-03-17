package cn.edu.scau.lxy.netdisk.admin.repository;

import cn.edu.scau.lxy.netdisk.admin.entity.DataDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDictionaryRepository {

    public int add(DataDictionary dataDictionary);

    public int deleteByID(long id);

    public DataDictionary findByID(long id);

    public List<DataDictionary> findAll(int index, int limit);//分页查询

    public int update(long id, String type, String description, long code, String value);

    public int count();
}
