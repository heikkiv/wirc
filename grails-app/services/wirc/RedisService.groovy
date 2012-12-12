package wirc

import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

class RedisService {
    
    JedisPool pool
    
    public RedisService() {
        pool = new JedisPool('localhost', 6379);
    }

    public void set(String key, String value) {
        Jedis jedis = getJedis()
        jedis.set(key, value)
        releaseJedis(jedis)
    }
    
    public String get(String key) {
        Jedis jedis = getJedis()
        String value = jedis.get(key)
        releaseJedis(jedis)
        return value
    }
    
    public void del(String key) {
        Jedis jedis = getJedis()
        jedis.del(key)
        releaseJedis(jedis)
    }
    
    public int llen(String key) {
        Jedis jedis = getJedis()
        int n = jedis.llen(key).intValue()
        releaseJedis(jedis)
        return n
    }
    
    public void ltrim(String key, int start, int stop) {
        Jedis jedis = getJedis()
        jedis.ltrim(key, start, stop)
        releaseJedis(jedis)
    }
    
    public int lpush(String key, String value) {
        Jedis jedis = getJedis()
        int n = jedis.lpush(key, value).intValue()
        releaseJedis(jedis)
        return n
    }
    
    public List<String> lrange(String key, int start, int stop) {
        Jedis jedis = getJedis()
        def l = jedis.lrange(key, start, stop)
        releaseJedis(jedis)
        return l
    }
    
    public int sadd(String key, String value) {
        Jedis jedis = getJedis()
        int n = jedis.sadd(key, value).intValue()
        releaseJedis(jedis)
        return n
    }
    
    public Set<String> smembers(String key) {
        Jedis jedis = getJedis()
        def s = jedis.smembers(key)
        releaseJedis(jedis)
        return s
    }
    
    public void incr(String key) {
        Jedis jedis = getJedis()
        jedis.incr(key);
        releaseJedis(jedis)
    }
    
    private Jedis getJedis() {
        Jedis jedis = pool.getResource();
        return jedis;
    }
    
    private void releaseJedis(Jedis jedis) {
        pool.returnResource(jedis);
    }

}