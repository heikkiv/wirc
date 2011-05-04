package wirc

import grails.test.*
import wirc.couchdb.CouchDb

class CouchDbServiceTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testAddMessage() {
		def id = System.currentTimeMillis()
		def m = new Message(id:id, sender:"heikki", text:"testi")
		def couchdb = new CouchDb()
		def actualId = couchdb.addMessage(m)
		assertEquals id, actualId
    }

	void testGetMessages() {
		def couchdb = new CouchDb()
		def messages = couchdb.getMessages()
		assertTrue messages.size() > 0
	}
}
