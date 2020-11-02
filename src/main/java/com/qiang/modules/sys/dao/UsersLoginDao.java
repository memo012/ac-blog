package com.qiang.modules.sys.dao;

import com.qiang.modules.sys.entity.VO.UsersVOEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author: qiang
 * @Description:
 * @Date: 2019/8/17 0017 12:44
 */
@Repository
public interface UsersLoginDao {

    UsersVOEntity findByPhone(String phone);

}
