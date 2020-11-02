package com.qiang.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiang.modules.sys.entity.GuestLikesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: qiang
 * @Description:
 * @Date: 2019/8/17 0017 20:41
 */
@Repository
@Mapper
public interface GuestLikesDao extends BaseMapper<GuestLikesEntity> {
}
