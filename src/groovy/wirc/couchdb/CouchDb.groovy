package wirc.couchdb

import static groovyx.net.http.ContentType.JSON
import groovyx.net.http.RESTClient

import wirc.Message

public class CouchDb {
	
	def client
	
	public CouchDb() {
		client = new RESTClient("http://heikkiv.couchone.com/")
		client.handler.failure = { resp, reader ->
		    println "Unexpected failure: ${resp.statusLine}"
			System.out << reader
		}
	}
	
	def addMessage(Message message) {
		def response = client.put(
			path: "messages/${message.id}", 
			contentType: JSON,
		    requestContentType: JSON,
		    body: [ id: message.id,
					time: message.time.time,
		            sender: message.sender,
		            text: message.text])
		return response.data.id.toLong()
	}
	
	def getMessages() {
		def messages = []
		client.get(
			path: "messages/_all_docs", 
			contentType: JSON,
		    query: [ descending:'true', limit: 100, include_docs:'true']) { resp, json ->
			json.rows.each {
				def m = new Message(id:it.id.toLong(), time:new Date(it.doc.time.toLong()), sender:it.doc.sender, text:it.doc.text)
				messages.add(m)
			}
		}
		return messages.reverse()
	}
		
}