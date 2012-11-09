package wirc

import grails.test.*

class BoxCarNotificationServiceTests extends GrailsUnitTestCase {
    
    def boxCarNotificationService = new BoxCarNotificationService()
    
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
    
    void testNotification() {
        assertTrue boxCarNotificationService.sendNotification('Test notification', 'heikki.verta@gmail.com', 'http://pertti.dyndns.info/wirc/log/index')
        log.info('moi')
    }
    
}
