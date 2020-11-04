package com.qiang.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiang.modules.sys.entity.VO.BlogMessageVOEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: qiang
 * @Description: mybatis-plus的config配置类@MapperScan的扫描和与接口上的@Mapper的配置, 有一个就行了, 不然会有重复提示
 * @Date: 2019/8/17 0017 15:44
 */
@Repository
@Mapper
public interface BlogDao extends BaseMapper<BlogMessageVOEntity> {
}
