package com.qiang.modules.sys.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.mapper
 * @Description:
 * @Date: 2019/8/8 0008 16:14
 **/
@Repository
public interface IndexDao {

    @Select("select count(*) from blog")
    Long findmyArticlesCount();

    @Select("select count(*) from label")
    int findMyLabelsCount();

    @Select("select count(*) from reportcomment")
    Integer findMyReportCount();

    @Select("select count(*) from comment")
    Integer findMyReportsCount();

    @Select("select count(*) from guest")
    Integer findmyGuestCount();

    @Select("select count(*) from repguest")
    Integer findmyGuestRepount();

    @Select("select visitor from web")
    int findWebVisitorCount();

    @Update("update web set visitor = #{argo}")
    int updWebVisitorCount(int count);

}
