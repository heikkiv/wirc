package wirc.irc

import grails.test.*
import wirc.NotificationService

class IrcBotTests extends GrailsUnitTestCase {
    
    def bot
    def lastNotification = ''
    
    protected void setUp() {
        super.setUp()
        def service = [sendNotification:{ message, email, url -> lastNotification = message; return true }] as NotificationService
        bot = new IrcBot()
        bot.notificationService = service
    }

    protected void tearDown() {
        super.tearDown()
    }
    
    void testMentionNotification() {
        bot.onMessage('#test', '', '', '', 'HeikkiV__: Test message for you')
        assertEquals lastNotification, 'HeikkiV__: Test message for you'
    }
    
    void testPrivateMessageNotification() {
        bot.onPrivateMessage('HeikkiV__', '', '', 'How Ya Doin')
        assertEquals lastNotification, 'HeikkiV__: How Ya Doin'
    }
    
}
