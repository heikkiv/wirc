package wirc.irc

import org.jibble.pircbot.*
import org.apache.commons.logging.LogFactory

import wirc.Message
import wirc.PrivateMessage
import wirc.RedisService
import wirc.NotificationService
import wirc.BoxCarNotificationService

public class IrcBot extends PircBot {
    
    private static final log = LogFactory.getLog(this)
	
	RedisService redisService
    NotificationService notificationService
    
    public IrcBot() {
        this.setName("HeikkiV__");
		redisService = new RedisService()
        notificationService = new BoxCarNotificationService()
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
		def m = new Message()
		m.sender = sender
		m.text = message
		m.channel = channel
		redisService.lpush('channel:'+channel, m.toTsv())
		redisService.ltrim('channel:'+channel, 0, 1000)
        log.info(m.toTsv())
        if(message.contains('HeikkiV__')) {
            notificationService.sendNotification(message, 'heikki.verta@gmail.com', 'http://pertti.dyndns.info/wirc/log/index')
        }
    }
    
    public void onPrivateMessage(String sender, String login, String hostname, String message) {
        def m = new PrivateMessage()
        m.sender = sender
        m.login = login
        m.hostname = hostname
        m.text = message
        redisService.sadd('private:message:senders', sender)
        redisService.lpush('channel:'+sender, m.toTsv())
        log.info('PM: ' + m.toTsv())
        notificationService.sendNotification(sender + ': ' + message, 'heikki.verta@gmail.com', 'http://pertti.dyndns.info/wirc/log/index?from=' + sender)
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


