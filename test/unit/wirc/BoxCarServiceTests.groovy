package wirc

import grails.test.*

class BoxCarServiceTests extends GrailsUnitTestCase {
    
    def boxCarService = new BoxCarService()
    
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSomething() {
        def resp = boxCarService.test()
        assertEquals resp.value(), 200
    }
    
    void testNotification() {
        assertTrue boxCarService.sendNotification('Test notification', 'heikki.verta@gmail.com')
    }
}
