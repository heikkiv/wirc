package wirc

import grails.test.*

class MessageTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testToTabSeparatedValues() {
        Message m = new Message(text:'Moikka\tmoi', sender: 'Heikki', time: new Date(0), channel:'#ep-dev')
        assertEquals m.toTsv(), '0\t#ep-dev\tHeikki\tMoikka moi'
    }
    
    void testTsvConstructor() {
        Message m = new Message('1\t#ep-dev\tHeikki\tMoikka moi')
        assertEquals m.time.getTime(), 1
        assertEquals m.channel, '#ep-dev'
        assertEquals m.sender, 'Heikki'
        assertEquals m.text, 'Moikka moi'
    }
    
}
