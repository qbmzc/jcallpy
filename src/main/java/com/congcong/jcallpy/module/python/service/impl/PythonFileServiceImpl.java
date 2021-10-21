package com.congcong.jcallpy.module.python.service.impl;

import com.congcong.jcallpy.module.python.mapper.PythonFileMapper;
import com.congcong.jcallpy.module.python.pojo.PythonFile;
import com.congcong.jcallpy.module.python.service.PythonFileService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author cong
 * @since 2021/10/21 15:01
 */
@Service
public class PythonFileServiceImpl implements PythonFileService {

    private final PythonFileMapper pythonFileMapper;

    public PythonFileServiceImpl(PythonFileMapper pythonFileMapper) {
        this.pythonFileMapper = pythonFileMapper;
    }

    /**
     * 保存
     *
     * @param pythonFile
     * @return
     */
    @Override
    public int save(PythonFile pythonFile) {
        pythonFile.setCreateTime(new Date());
        pythonFile.setUpdateTime(new Date());
        pythonFile.setStatus(0);
        pythonFile.setVersion(0L);
        pythonFile.setIsDeleted(0);
        return 0;
    }

    /**
     * 更新
     *
     * @param pythonFile
     * @return
     */
    @Override
    public int update(PythonFile pythonFile) {
        return 0;
    }

    /**
     * 执行
     *
     * @param name
     * @return
     */
    @Override
    public String exec(String name) {
        return null;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public int delete(Long id) {
        return 0;
    }

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<PythonFile> queryPage(Integer pageNum, Integer pageSize) {
        return null;
    }

    /**
     * 根据名称查询单个记录
     *
     * @param name
     * @return
     */
    @Override
    public PythonFile queryOneByName(String name) {
        return null;
    }
}
