package com.congcong.jcallpy.module.python.service;

import com.congcong.jcallpy.module.python.pojo.PythonFile;

import org.springframework.data.domain.Page;


/**
 * @author cong
 * @since 2021/10/21 15:01
 */
public interface PythonFileService {

    /**
     * 保存
     * @param pythonFile
     * @return
     */
    int save(PythonFile pythonFile);

    /**
     * 更新
     * @param pythonFile
     * @return
     */
    int update(PythonFile pythonFile);

    /**
     * 执行
     * @param name
     * @return
     */
    String exec(String name);

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<PythonFile> queryPage(Integer pageNum , Integer pageSize);

    /**
     * 根据名称查询单个记录
     * @param name
     * @return
     */
    PythonFile  queryOneByName(String name);
}
