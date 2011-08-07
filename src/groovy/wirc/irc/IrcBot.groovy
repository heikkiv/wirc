package wirc.irc

import org.jibble.pircbot.*
import wirc.Message
import wirc.couchdb.CouchDb
import wirc.IsgdService

public class IrcBot extends PircBot {
	
	def messages = []
	def couchDb
    
    public IrcBot() {
        this.setName("HeikkiV__");
		couchDb = new CouchDb()
		//messages = couchDb.getMessages()
		messages.add(new Message(text:'Test', channel:'#yougamers2'))
		messages.add(new Message(text:'Test', channel:'#ep-dev'))
    }

	def getMessagesFromChannel(String channel) {
		if(channel) {
			return messages.findAll() {
				it.channel == channel
			}
		} else {
			return messages
		}
	}
    
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
		def text = markLinks(message)
		def m = new Message()
		m.id = System.currentTimeMillis()
		m.sender = sender
		m.text = text
		m.channel = channel
		messages.add(m)
		if( messages.size() > 200 ) {
			messages = messages.tail()
		}
		//couchDb.addMessage(m)
		//println "On message called, messages ${messages.size()}"
    }

	def markLinks(String message) {
		def words = message.split()
		def m = ""
		words.each {
		    if(it.startsWith("http://")) {
		        m += "<a href='${it}'>${it}</a> "   
		    } else {
		        m += "${it.encodeAsHTML()} "
		    }
		}
		return m
	}
	
	public void onConnect() {
		println "Connected"
	}
	
	public void onDisconnect() {
		println "Disconnected from irc server"
		while (!isConnected()) {
		    try {
				println "Trying to reconnect"
		        reconnect();
				joinChannel("#yougamers2");
				joinChannel("#ep-dev");
		    }
		    catch (Exception e) {
		        sleep(60000)
		    }
		}
	}
	
	/*
	public void onServerPing(String response) {
		super.onServerPing(response);
		def channels = getChannels() as List
		if( !channels.contains("#yougamers2") ) {
			println "Rejoining #yougamers2"
			joinChannel("#yougamers2");
		}
	}
	*/
}


