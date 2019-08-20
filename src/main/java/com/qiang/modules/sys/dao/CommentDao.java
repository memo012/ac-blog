package com.qiang.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiang.modules.sys.entity.CommentEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.mapper
 * @Description: 评论数据库
 * @Date: 2019/7/22 0022 14:56
 **/
@Repository
public interface CommentDao extends BaseMapper<CommentEntity> {

    List<CommentEntity> findByBlogIdAndPid(long blogId);

    @Update("update comment set likes = likes - 1 where blog_id = #{blogId} and id = #{commentId}")
    int updDesCommIsLikes(@Param("blogId") Long blogId, @Param("commentId") Long commentId);

    @Update("update comment set likes = likes + 1 where blog_id = #{blogId} and id = #{commentId}")
    int updInsCommIsLikes(@Param("blogId") Long blogId, @Param("commentId") Long commentId);

    @Update("update comment set is_read = 0 where id = #{arg0}")
    int updOneBlogNotComm(Long id);

    @Update("update commentlikes set is_read = 0 where id = #{arg0}")
    int updOneBlogNotLikes(Long id);


}
