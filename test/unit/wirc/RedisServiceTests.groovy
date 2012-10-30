package wirc

import grails.test.*
import redis.clients.jedis.*

class RedisServiceTests extends GrailsUnitTestCase {
    
    RedisService redisService = new RedisService()
    
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSetKey() {
		redisService.set('foo', 'bar')
        assertEquals redisService.get('foo'), 'bar'
    }
    
    void testLpushSingleValue() {
        int length = redisService.llen('list')
        int newLength = redisService.lpush('list', 'hello');
        assertEquals newLength, length + 1
    }
    
    void testLrange() {
        redisService.del('list')
        redisService.lpush('list', 'foo');
        redisService.lpush('list', 'world');
        redisService.lpush('list', 'hello');
        def list = redisService.lrange('list', 0, 1)
        assertEquals list.size(), 2
        assertEquals list[0], 'hello'
        assertEquals list[1], 'world'
    }
    
}