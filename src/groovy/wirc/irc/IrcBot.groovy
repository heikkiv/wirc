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
		messages.add(new Message(text:'Moikka', sender: 'Heikki', time: '14:59:24', channel:'#ep_dev'))
		messages.add(new Message(text:'Terve', sender: 'Heikki', time: '14:59:30', channel:'#ep-dev'))
		messages.add(new Message(text:'http://is.gd/l2deBJ foo', sender: 'Heikki', time: '14:59:40', channel:'#ep-dev'))
		messages.add(new Message(text:'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum fringilla odio iaculis magna viverra cursus. Fusce nec aliquam orci. Praesent id varius eros. Donec mollis sagittis urna et porttitor. Ut at varius turpis. Mauris bibendum rhoncus nunc, molestie varius ipsum rhoncus id. In pellentesque eros ac sem imperdiet vel mollis eros convallis.', sender: 'Heikki', time: '14:59:45', channel:'#ep-dev'))
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


