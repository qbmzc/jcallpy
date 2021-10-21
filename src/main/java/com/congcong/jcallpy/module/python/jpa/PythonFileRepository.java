package com.congcong.jcallpy.module.python.jpa;

import com.congcong.jcallpy.module.python.pojo.PythonFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cong
 * @since 2021/10/21 16:54
 */
@Repository
public interface PythonFileRepository extends JpaRepository<PythonFile, Long> {
}
