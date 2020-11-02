package com.qiang.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiang.modules.sys.entity.FriendurlEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: qiang
 * @Description:
 * @Date: 2019/8/19 0019 21:34
 */
@Repository
@Mapper
public interface FriendurlDao extends BaseMapper<FriendurlEntity> {
}
