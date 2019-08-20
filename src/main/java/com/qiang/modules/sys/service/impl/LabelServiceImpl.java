package com.qiang.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiang.common.utils.Constant;
import com.qiang.common.utils.RedisOperator;
import com.qiang.modules.sys.dao.LabelDao;
import com.qiang.modules.sys.entity.LabelEntity;
import com.qiang.modules.sys.service.AsyncService;
import com.qiang.modules.sys.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.service
 * @Description: 标签业务逻辑层
 * @Date: 2019/7/6 0006 11:50
 **/
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private AsyncService asyncService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<LabelEntity> selAllLabel() {
        List<LabelEntity> list = null;
        // 从redis中查询
        if(redisOperator.hasKey(Constant.LABEL_ALL)){
            list = (List<LabelEntity>)redisOperator.range(Constant.LABEL_ALL, 0, -1);
        }else{
            // 从数据库中查 并存入缓存中
            list = labelDao.selectList(null);
            if (list != null) {
                // 以异步形式存储到redis中
                asyncService.insLabelName(list);
            }
        }
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insLabel(String[] label) {
        int flag = 0;
        for(int i = 0; i < label.length; i++){
            Integer result = labelDao.selectCount(new QueryWrapper<LabelEntity>().eq("label_name", label[i]));
            if(result == 0){
                LabelEntity tags = new LabelEntity();
                String id = UUID.randomUUID().toString();
                tags.setId(id);
                tags.setLabelName(label[i]);
                flag = labelDao.insert(tags);
                // 把标签名加入缓存
                redisOperator.lpush(Constant.LABEL_ALL, tags);
                // 标签数加一
                redisOperator.incr(Constant.LABEL_ALL_COUNT, 1);
            }
        }
        return flag;
    }

}
