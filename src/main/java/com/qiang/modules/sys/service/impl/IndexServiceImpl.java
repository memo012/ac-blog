package com.qiang.modules.sys.service.impl;

import com.qiang.common.constatnt.BlogConstant;
import com.qiang.common.utils.RedisOperator;
import com.qiang.modules.sys.dao.IndexDao;
import com.qiang.modules.sys.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: qiang
 * @Description:
 * @Date: 2019/8/8 0008 16:13
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexDao indexDao;

    @Autowired
    private RedisOperator redisOperator;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Long myArticlesCount() {
        Long aLong = indexDao.findmyArticlesCount();
        redisOperator.set(BlogConstant.BLOG_COUNT.val(), aLong);
        return aLong;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int myLabelsCount() {
        int myLabelsCount = indexDao.findMyLabelsCount();
        redisOperator.set(BlogConstant.LABEL_ALL_COUNT.val(), myLabelsCount);
        return myLabelsCount;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer myReportsCount() {
        Integer l1 = indexDao.findMyReportsCount();
        Integer l2 = indexDao.findMyReportCount();
        Integer l3 = l1 + l2;
        redisOperator.set(BlogConstant.BLOG_REPORT_COUNT.val(), l3);
        return l3;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer myGuestCount() {
        Integer aLong = indexDao.findmyGuestCount();
        Integer aLong1 = indexDao.findmyGuestRepount();
        Integer al = aLong + aLong1;
        redisOperator.set(BlogConstant.BLOG_GUEST_COUNT.val(), al);
        return al;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int myWebCount() {
        int visitorCount = indexDao.findWebVisitorCount();
        redisOperator.set(BlogConstant.BLOG_VISIT_COUNT.val(), visitorCount);
        return visitorCount;
    }

}
