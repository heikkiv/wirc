package wirc

import grails.test.*

class IrcServiceTests extends GrailsUnitTestCase {

	RedisService redisService = new RedisService()
	IrcService ircService = new IrcService()
	
    protected void setUp() {
        super.setUp()
        ircService.redisService = redisService
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testResetUnreadMessageCount() {
    	ircService.resetUnreadMessageCount('test_channel')
    	assertEquals redisService.get('channel:test_channel:unreadmessagecount'), '0'
    }

    void testGetUnreadMessageCount() {
    	redisService.set('channel:test_channel:unreadmessagecount', '10')
    	assertEquals ircService.getUnreadMessageCount('test_channel'), 10
    }

}
