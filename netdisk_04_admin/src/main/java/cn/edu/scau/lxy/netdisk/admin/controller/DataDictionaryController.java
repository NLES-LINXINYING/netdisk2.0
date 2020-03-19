package cn.edu.scau.lxy.netdisk.admin.controller;

import cn.edu.scau.lxy.netdisk.admin.entity.DataDictionary;
import cn.edu.scau.lxy.netdisk.admin.repository.DataDictionaryRepository;
import cn.edu.scau.lxy.netdisk.common.entity.MultiResult;
import cn.edu.scau.lxy.netdisk.common.entity.SingleResult;
import cn.edu.scau.lxy.netdisk.common.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dataDictionary")
public class DataDictionaryController {

    @Autowired
    private DataDictionaryRepository dataDictionaryRepository;


    @PostMapping("/add")
    public SingleResult add(@RequestBody DataDictionary dataDictionary) {
        try {
            dataDictionaryRepository.add(dataDictionary);
            return new SingleResult(StatusCode.OK, "增加成功", 1, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new SingleResult(StatusCode.ERROR, "增加失败", 0, null);
        }
    }

    @DeleteMapping("/deleteByID/{id}")
    public SingleResult deleteByID(@PathVariable long id) {
        System.out.println(" "+id);
        try {
            dataDictionaryRepository.deleteByID(id);
            return new SingleResult(StatusCode.OK, "删除成功", 1, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new SingleResult(StatusCode.ERROR, "删除失败", 0, null);
        }
    }

    @GetMapping("/findByID/{id}")
    public SingleResult findByID(@PathVariable long id) {
        try {
            DataDictionary dataDictionary = dataDictionaryRepository.findByID(id);
            return new SingleResult(StatusCode.OK, "查询成功", 1, dataDictionary);
        } catch (Exception e) {
            e.printStackTrace();
            return new SingleResult(StatusCode.ERROR, "查询失败", 0, null);
        }
    }

    @GetMapping("/findAll")
    public MultiResult findAll(@RequestParam int page, @RequestParam int limit) {

        try {
            int count = dataDictionaryRepository.count();
            List<Object> list = dataDictionaryRepository.findAll((page - 1) * limit, limit);
            return new MultiResult(StatusCode.OK, "查询成功", count, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new MultiResult(StatusCode.ERROR, "查询失败", 0, null);
        }
    }

    @PutMapping("/update")
    public SingleResult update(@RequestBody DataDictionary dataDictionary) {
        try {
            dataDictionaryRepository.update(dataDictionary);
            return new SingleResult(StatusCode.OK, "修改成功", 1, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new SingleResult(StatusCode.ERROR, "修改失败", 0, null);
        }
    }

    @GetMapping("/count")
    public int count() {
        return dataDictionaryRepository.count();
    }
}
