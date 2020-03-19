package cn.edu.scau.lxy.netdisk.file.repository;

import cn.edu.scau.lxy.netdisk.file.entity.Myshare;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyshareRepository {
    public int addfile(Myshare myshare);
    public int addfolder(Myshare myshare);
    public int deleteById(long id);
    public List<Myshare> findAll(long uid);
    public Myshare findById(long id);
    public List<Myshare> findByLink(String link);
    public int updateTimesOfBrowse(long id,long timesOfBrowse);
    public int updateTimesOfSave(long id,long timesOfSave);
    public int updateTimesOfDownload(long id,long timesOfDownload);
    public int count();
}
