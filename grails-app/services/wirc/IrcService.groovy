package wirc

import wirc.irc.IrcBot
import com.heikkiv.ml.thomas.*
import com.heikkiv.ml.thomas.mongo.*

import javax.annotation.PostConstruct
import grails.util.Environment

class IrcService {

	IrcBot bot
    RedisService redisService
	
	public IrcService() {
		bot = new IrcBot();
		def classifier = new NaiveBayesClassifier()
		classifier.repository = new MongoBayesClassifierRepository()
		classifier.setThreshold('work', 2)
		bot.classifier = classifier
	}
	
	@PostConstruct
	void connectToIrcServer() {
        println "Running in ${Environment.current} environment"
		bot.setVerbose(true);
		bot.setEncoding("utf-8");
        if(Environment.current == Environment.PRODUCTION) {
    		try {
    			println 'Connecting to irc1.inet.fi ...'
    			bot.connect("irc1.inet.fi");
    			println 'Connected to server'
    		} catch(Exception e) {
    			println e.getMessage()
    		}
        } else {
            println "In ${Environment.current} environment skipping bot.connect(...)"
        }
		bot.joinChannel("#yougamers2");
		bot.joinChannel("#ep-dev");
        println 'Joined channels'
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
        println "Loaded $senders.size private message senders"
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
    
    def getMessageCount(String channel) {
        String countString = redisService.get('channel:' + channel + ':messagecount') 
        int n = (countString) ? countString as int : 0;
        return n 
    }
    
    def getUsers(String channel) {
        return bot.getUsers(channel)
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
