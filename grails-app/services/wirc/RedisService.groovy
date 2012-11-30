package wirc

import redis.clients.jedis.Jedis

class RedisService {

    Jedis jedis

    public RedisService() {
        jedis = new Jedis("localhost");
    }

    public void set(String key, String value) {
        jedis.set(key, value)
    }
    
    public String get(String key) {
        return jedis.get(key)
    }
    
    public void del(String key) {
        jedis.del(key)
    }
    
    public int llen(String key) {
        return jedis.llen(key).intValue()
    }
    
    public void ltrim(String key, int start, int stop) {
        jedis.ltrim(key, start, stop)
    }
    
    public int lpush(String key, String value) {
        return jedis.lpush(key, value).intValue()
    }
    
    public List<String> lrange(String key, int start, int stop) {
        return jedis.lrange(key, start, stop)
    }
    
    public int sadd(String key, String value) {
        return jedis.sadd(key, value).intValue()
    }
    
    public Set<String> smembers(String key) {
        return jedis.smembers(key)
    }
    
    public void incr(String key) {
        jedis.incr(key);
    }

}