package com.congcong.jcallpy.module.python.mapper;

import com.congcong.jcallpy.module.python.pojo.PythonFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author cong
 * @since 2021/10/21 15:46
 */
@Mapper
public interface PythonFileMapper {

    @Insert("")
    int save(PythonFile pythonFile);
}
