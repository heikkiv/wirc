package wirc

import wirc.irc.IrcBot
import org.springframework.beans.factory.InitializingBean

class IrcService implements InitializingBean {

	IrcBot bot
	
	public IrcService() {
		bot = new IrcBot();
		println "IrcService created"
	}
	
	void afterPropertiesSet() {
		bot.setVerbose(true);
		bot.setEncoding("utf-8");
		try {
			bot.connect("irc1.inet.fi");
		} catch(Exception e) {
			println e.getMessage()
		}
		bot.joinChannel("#yougamers2");
		bot.joinChannel("#ep-dev");
	}
	
	
	def restart() {
		println "Restarting IrcService"
		bot.disconnect()
		bot = new IrcBot()
		afterPropertiesSet()
	}

    def getMessages(String channel) {
		return bot.getMessagesFromChannel(channel).reverse()
    }

	def sendMessage(String channel, String message) {
		def words = message.split()
		def m = ""
		words.each {
		    if(it.startsWith("http://")) {
				def shortLink = IsgdService.shortenUrl(it)
		        m += "${shortLink} "
		    } else {
		        m += "${it} "
		    }
		}
		bot.sendMessage(channel, m)
		bot.onMessage(channel, "HeikkiV", "", "", m)
	}
}
