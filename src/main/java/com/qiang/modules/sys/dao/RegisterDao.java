package com.qiang.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiang.modules.sys.entity.UsersEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author: qiang
 * @Description:
 * @Date: 2019/8/16 0016 21:29
 */
@Repository
public interface RegisterDao extends BaseMapper<UsersEntity> {
}
