package com.qiang.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 使用redisTemplate的操作实现类
 */
@Component
public class RedisOperator {

	/**
	 * @Autowired 默认以类型来比较
	 * @Resource 默认以名字来比较
	 */
	@Autowired
	private RedisTemplate redisTemplate;
	
	// Key（键），简单的key-value操作


    /**
     *  key 是否存在
     * @param key
     * @return
     */
	public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

	/**
	 * 实现命令：TTL key，以秒为单位，返回给定 key的剩余生存时间(TTL, time to live)。
	 * 
	 * @param key
	 * @return
	 */
	public long ttl(String key) {
		return redisTemplate.getExpire(key);
	}
	
	/**
	 * 实现命令：expire 设置过期时间，单位秒
	 * 
	 * @param key
	 * @return
	 */
	public void expire(String key, long timeout) {
		redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}
	
	/**
	 * 实现命令：INCR key，增加key一次
	 * 
	 * @param key
	 * @return
	 */
	public long incr(String key, long delta) {
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 实现命令：KEYS pattern，查找所有符合给定模式 pattern的 key
	 */
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * 实现命令：DEL key，删除一个key
	 * 
	 * @param key
	 */
	public void del(String key) {
		redisTemplate.delete(key);
	}

	// String（字符串）

	/**
	 * 实现命令：SET key value，设置一个key-value（将字符串值 value关联到 key）
	 * 
	 * @param key
	 * @param value
	 */
		public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 实现命令：SET key value EX seconds，设置key-value和超时时间（秒）
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            （以秒为单位）
	 */
	public void set(String key, String value, long timeout) {
		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 实现命令：GET key，返回 key所关联的字符串值。
	 * 
	 * @param key
	 * @return value
	 */
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 实现命令：SIZE key，返回key所存储的字符串值的长度
	 *
	 * @param key
	 * @return value
	 */
	public Long size(String key) {
		return redisTemplate.opsForValue().size(key);
	}

	// Hash（哈希表）

    /**
     * 实现命令： 判断哈希表中是否存在key，field
     * @param key
     * @param field
     * @return
     */
    public boolean hasHkey(String key, Object field){
	    return redisTemplate.opsForHash().hasKey(key, field);
    }

	/**
	 * 实现命令：HSET key field value，将哈希表 key中的域 field的值设为 value
	 *
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(String key, Object field, Object value) {
		redisTemplate.opsForHash().put(key, field, value);
	}


	/**
	 * 实现命令：hHSIZE key，将哈希表 key中数据
	 *
	 * @param key
	 */
	public Long hsize(String key){
		return 	redisTemplate.opsForHash().size(key);
	}

	/**
	 * 实现命令：HGET key field，返回哈希表 key中给定域 field的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public Object hget(String key, Object field) {
		return redisTemplate.opsForHash().get(key, field);
	}

	/**
	 * 实现命令：HDEL key field [field ...]，删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 * 
	 * @param key
	 * @param fields
	 */
	public void hdel(String key, Object... fields) {
		redisTemplate.opsForHash().delete(key, fields);
	}

	/**
	 * 实现命令：HGETALL key，返回哈希表 key中，所有的域和值。
	 * 
	 * @param key
	 * @return
	 */
	public Object hgetall(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	// List（列表）

	/**
	 * 实现命令：LPUSH key value，将一个值 value插入到列表 key的表头
	 * 
	 * @param key
	 * @param value
	 * @return 执行 LPUSH命令后，列表的长度。
	 */
	public long lpush(String key, Object value) {
		return redisTemplate.opsForList().leftPush(key, value);
	}

    /**
     * 截取集合元素长度，保留长度内的数据
     * @param key
     * @param start
     * @param end
     */
	public void ltrim(String key, long start, long end){
	    redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     *  实现命令：LPUSHALL key value 把多个value值存入到list集合中
     * @param key
     * @param value
     * @return 执行 LPUSHALL命令后，列表的长度。
     */
	public long lpushAll(String key, Object... value){

	    return redisTemplate.opsForList().leftPushAll(key, value);
    }


    /**
     * 从存储在键中的列表中删除等于值的元素的第一个计数事件。
     * count> 0：删除等于从左到右移动的值的第一个元素；
     * count< 0：删除等于从右到左移动的值的第一个元素；
     * count = 0：删除等于value的所有元素。
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long lremove(String key, long count, Object value){
        return redisTemplate.opsForList().remove(key, 0, value);
    }

	/**
	 * 实现命令：LPOP key，移除并返回列表 key的头元素。
	 * 
	 * @param key
	 * @return 列表key的头元素。
	 */
	public Object lpop(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 实现命令：RPUSH key value，将一个值 value插入到列表 key的表尾(最右边)。
	 * 
	 * @param key
	 * @param value
	 * @return 执行 LPUSH命令后，列表的长度。
	 */
	public long rpush(String key, Object value) {
		return redisTemplate.opsForList().rightPush(key, value);
	}

    /**
     *  实现命令：RANGE key start stop , 将列表key中的值下标从start到stop取出
     *  @param key
     * @param start
     * @param stop
     * @return 执行完命令后，返回List集合
     */
	public Object range(String key, long start, long stop){
	    return redisTemplate.opsForList().range(key, start, stop);
    }

    /**
     *  实现命令： LLEN key,列表中key的长度
     * @param key
     * @return
     */
    public long llen(String key){
	    return redisTemplate.opsForList().size(key);
    }

	/**
	 * 任务队列
	 * 命令实现 RPOPLPUSH sourceKey destinationKey，移除列表的最后一个元素，并将该元素添加到另一个列表并返回
	 * @param sourceKey
	 * @param destinationKey
	 */
	public void rpoplpush(String sourceKey, String destinationKey){
    	redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
	}


}