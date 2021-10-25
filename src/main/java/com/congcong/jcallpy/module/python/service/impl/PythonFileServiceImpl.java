package com.congcong.jcallpy.module.python.service.impl;

import com.congcong.jcallpy.module.python.jpa.PythonFileRepository;
import com.congcong.jcallpy.module.python.pojo.PythonFile;
import com.congcong.jcallpy.module.python.service.PythonFileService;

import com.congcong.jcallpy.util.CommandUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author cong
 * @since 2021/10/21 15:01
 */
@Slf4j
@Service
public class PythonFileServiceImpl implements PythonFileService {

    private final PythonFileRepository repository;

    public PythonFileServiceImpl(PythonFileRepository repository) {
        this.repository = repository;
    }

    /**
     * 保存
     *
     * @param pythonFile
     * @return
     */
    @Override
    public int save(PythonFile pythonFile) {
        log.info("脚本名称：{}", pythonFile.getFileName());
        PythonFile queryOneByName = this.queryOneByName(pythonFile.getFileName());
        if (null != queryOneByName) {
            queryOneByName.setFilePath(pythonFile.getFilePath());;
            queryOneByName.setUpdateTime(new Date());
            queryOneByName.setVersion(queryOneByName.getVersion()+1);
            repository.save(queryOneByName);
            log.info("已存在同名脚本，将更新脚本 {}", pythonFile.getFileName());
            return 1;
        }
        pythonFile.setCreateTime(new Date());
        pythonFile.setUpdateTime(new Date());
        pythonFile.setState(0);
        pythonFile.setVersion(0L);
        pythonFile.setIsDeleted(0);
        repository.save(pythonFile);
        log.info("新增记录:{}", pythonFile);
        return 1;
    }

    /**
     * 更新
     *
     * @param pythonFile
     * @return
     */
    @Override
    public int update(PythonFile pythonFile) {
        log.info("更新记录：{}", pythonFile.toString());
        repository.save(pythonFile);
        return 1;
    }

    /**
     * 执行
     *
     * @param name
     * @return
     */
    @Override
    public String exec(String name) {
        log.info("要执行的脚本名称为：{}", name);
        PythonFile file = new PythonFile();
        file.setFileName(name);
        Example<PythonFile> example = Example.of(file);
        Optional<PythonFile> optional = this.repository.findOne(example);
        PythonFile pythonFile = optional.get();
        String path = pythonFile.getFilePath();
        return CommandUtils.doExec(path);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public int delete(Long id) {
        PythonFile file = new PythonFile();
        file.setId(id);
        file.setIsDeleted(1);
        repository.save(file);
        return 1;
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
        PythonFile file = new PythonFile();
        file.setIsDeleted(0);
        log.info("分页查询第{}页，每页{}条", pageNum, pageSize);
        return repository.findAll(Example.of(file), PageRequest.of(pageNum, pageSize));
    }

    /**
     * 根据名称查询单个记录
     *
     * @param name
     * @return
     */
    @Override
    public PythonFile queryOneByName(String name) {
        log.info("查询脚本名称为：{} 的记录", name);
        PythonFile file = new PythonFile();
        file.setFileName(name);
        Optional<PythonFile> one = repository.findOne(Example.of(file));
        return one.get();
    }

    @Override
    public void active(Long id) {
        Optional<PythonFile> findById = this.repository.findById(id);
        PythonFile pythonFile = findById.get();
        pythonFile.setUpdateTime(new Date());
        pythonFile.setState(pythonFile.getState() == 1 ? 0 : 1);
        String s = "禁用";
        if (pythonFile.getState() == 1) {
            s = "激活";
        }
        log.info("{} 用脚本 {}", s, pythonFile.getFileName());
        this.update(pythonFile);
    }
}
