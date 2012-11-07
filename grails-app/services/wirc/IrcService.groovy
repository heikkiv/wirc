package wirc

import wirc.irc.IrcBot
import javax.annotation.PostConstruct

class IrcService {

	IrcBot bot
    RedisService redisService
	
	public IrcService() {
		bot = new IrcBot();
	}
	
	@PostConstruct
	void connectToIrcServer() {
		bot.setVerbose(true);
		bot.setEncoding("utf-8");
		try {
			println 'Connecting to irc1.inet.fi ...'
			bot.connect("irc1.inet.fi");
			println 'Connected'
		} catch(Exception e) {
			println e.getMessage()
		}
		bot.joinChannel("#yougamers2");
		bot.joinChannel("#ep-dev");
        println 'Connected to server and joined channels'
	}
	
	def restart() {
		println "Restarting IrcService"
		bot.disconnect()
		bot = new IrcBot()
		connectToIrcServer()
	}

    def getPrivateMessagesSenders() {
        def senders = []
        redisService.smembers('private:message:senders').each {
            senders.add(it)
        }
        println "Loaded $senders.size private message sender"
		return senders
    }
    
    def getMessages(String channel) {
        def messages = []
        redisService.lrange('channel:' + channel, 0, 99).each {
            messages.add(new Message(it))
        }
        println "Loaded $messages.size messages from $channel"
		return messages
    }

	def sendMessage(String channel, String message) {
		def m = shortenUrls(message)
		bot.sendMessage(channel, m)
        if(channel.startsWith('#')) {
            bot.onMessage(channel, "HeikkiV__", "", "", m)
        } else {
            def pm = new PrivateMessage()
            pm.sender = 'HeikkiV__'
            pm.text = message
            redisService.sadd('private:message:senders', channel)
            redisService.lpush('channel:'+channel, pm.toTsv())
        }
	}
    
    def shortenUrls(String message) {
		def words = message.split()
		def m = ""
		words.each {
		    if((it.startsWith("http://") || it.startsWith("https://")) && it.endsWith("##shorten")) {
                println "Shortening url $it"
				def shortLink = IsgdService.shortenUrl(it.replace("##shorten", ""))
		        m += "${shortLink} "
		    } else {
		        m += "${it} "
		    }
		}
        return m
    }
}
