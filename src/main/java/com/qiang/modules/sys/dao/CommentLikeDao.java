package com.qiang.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiang.modules.sys.entity.CommentLikesEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.dao
 * @Description:
 * @Date: 2019/8/17 0017 19:10
 **/
@Repository
public interface CommentLikeDao extends BaseMapper<CommentLikesEntity> {

    List<CommentLikesEntity> findLikes(String username);

    @Select("select cl.id from comment c INNER JOIN commentlikes cl on c.id = cl.comment_id where c.author_name = #{arg0} and cl.is_read = 1")
    List<Long> findNotReadLikesIdByUsername(String username);

    int updLikesIsRead(List<Long> rid);



}
