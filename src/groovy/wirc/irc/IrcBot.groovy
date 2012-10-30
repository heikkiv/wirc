package wirc.irc

import org.jibble.pircbot.*
import wirc.Message
import wirc.PrivateMessage
import wirc.couchdb.CouchDb
import wirc.RedisService

public class IrcBot extends PircBot {
	
	def messages = []
    def privateMessages = [:]
	RedisService redisService
    
    public IrcBot() {
        this.setName("HeikkiV__");
		redisService = new RedisService()
		messages.add(new Message(text:'Moikka', sender: 'Heikki', time: '14:59:24', channel:'#ep_dev'))
		messages.add(new Message(text:'Terve', sender: 'Heikki', time: '14:59:30', channel:'#ep-dev'))
		messages.add(new Message(text:'http://is.gd/l2deBJ foo', sender: 'Heikki', time: '14:59:40', channel:'#ep-dev'))
		messages.add(new Message(text:'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum fringilla odio iaculis magna viverra cursus. Fusce nec aliquam orci. Praesent id varius eros. Donec mollis sagittis urna et porttitor. Ut at varius turpis. Mauris bibendum rhoncus nunc, molestie varius ipsum rhoncus id. In pellentesque eros ac sem imperdiet vel mollis eros convallis.', sender: 'Heikki', time: '14:59:45', channel:'#ep-dev'))
		messages.add(new Message(text:'<Yammer|Test user>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum fringilla odio iaculis magna viverra cursus. Fusce nec aliquam orci. Praesent id varius eros. Donec mollis sagittis urna et porttitor. Ut at varius turpis. Mauris bibendum rhoncus nunc, molestie varius ipsum rhoncus id. In pellentesque eros ac sem imperdiet vel mollis eros convallis.', sender: 'Heikki', time: '14:59:45', channel:'#ep-dev'))
        def temp = []
        temp.add(new PrivateMessage(text: 'moikka moi', sender: 'FonHeikki', login: 'FonHeikki', hostname: 'localhost'))
        privateMessages.put('FonHeikki', temp)
    }

    def loadMessages(String channel) {
        int n = 0
        redisService.lrange('channel:' + channel, 0, 99).each {
            messages.add(new Message(it))
            n++
        }
        println "Loaded $n messages from $channel"
    }

    def getPrivateMessagesSenders() {
        return privateMessages.keySet()
    }
    
    def getPrivateMessages(String sender) {
        return privateMessages[sender]
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
		def m = new Message()
		m.sender = sender
		m.text = message
		m.channel = channel
		messages.add(m)
		if( messages.size() > 200 ) {
			messages = messages.tail()
		}
		redisService.lpush('channel:'+channel, m.toTsv())
    }
    
    public void onPrivateMessage(String sender, String login, String hostname, String message) {
        def m = new PrivateMessage()
        m.sender = sender
        m.login = login
        m.hostname = hostname
        m.text = message
        addPrivateMessage(m)
    }
    
    private void addPrivateMessage(PrivateMessage message) {
        if(privateMessages[message.sender]) {
            privateMessages[message.sender].add(message)
        } else {
            privateMessages[message.sender] = []
            privateMessages[message.sender].add(message)
        }
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
	
}


