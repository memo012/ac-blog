package com.qiang.modules.sys.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Author: qiang
 * @Description:
 * @Date: 2019/7/30 0030 18:24
 */
public class CustomSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        return "qzBlog" + UUID.randomUUID().toString().replace("-", "");
    }
}
