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
        Message m = new Message(text:'Moikka\tmoi', sender: 'Heikki', time: new Date(0), channel:'#ep-dev', category: 'work')
        assertEquals '0\t#ep-dev\tHeikki\tMoikka moi\twork', m.toTsv()
    }
    
    void testTsvConstructor() {
        Message m = new Message('1\t#ep-dev\tHeikki\tMoikka moi')
        assertEquals m.time.getTime(), 1
        assertEquals m.channel, '#ep-dev'
        assertEquals m.sender, 'Heikki'
        assertEquals m.text, 'Moikka moi'
        assertEquals m.category, 'unknown'
    }
    
    void testTsvConstructorWithCategory() {
        Message m = new Message('1\t#ep-dev\tHeikki\tMoikka moi\twork')
        assertEquals m.time.getTime(), 1
        assertEquals m.channel, '#ep-dev'
        assertEquals m.sender, 'Heikki'
        assertEquals m.text, 'Moikka moi'
        assertEquals m.category, 'work'
    }
    
}
