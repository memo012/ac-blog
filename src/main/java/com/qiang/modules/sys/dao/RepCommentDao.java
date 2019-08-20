package com.qiang.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiang.modules.sys.entity.ReportCommentEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.dao
 * @Description:
 * @Date: 2019/8/17 0017 19:01
 **/
@Repository
public interface RepCommentDao extends BaseMapper<ReportCommentEntity> {

    List<ReportCommentEntity> getUserRepMessNotRead(String username);

    @Select("select rid from reportcomment where com_name = #{arg0} and ris_read = 1")
    List<Long> selComByUsername(String usrename);

    int updComIsRead(List<Long> rid);

}
