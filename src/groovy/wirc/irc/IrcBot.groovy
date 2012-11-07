package wirc.irc

import org.jibble.pircbot.*
import wirc.Message
import wirc.PrivateMessage
import wirc.couchdb.CouchDb
import wirc.RedisService

public class IrcBot extends PircBot {
	
	RedisService redisService
    
    public IrcBot() {
        this.setName("HeikkiV__");
		redisService = new RedisService()
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
		def m = new Message()
		m.sender = sender
		m.text = message
		m.channel = channel
		redisService.lpush('channel:'+channel, m.toTsv())
    }
    
    public void onPrivateMessage(String sender, String login, String hostname, String message) {
        def m = new PrivateMessage()
        m.sender = sender
        m.login = login
        m.hostname = hostname
        m.text = message
        redisService.sadd('private:message:senders', sender)
        redisService.lpush('channel:'+sender, m.toTsv())
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


