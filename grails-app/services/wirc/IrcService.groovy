package wirc

import wirc.irc.IrcBot
import javax.annotation.PostConstruct

class IrcService {

	IrcBot bot
	
	public IrcService() {
		bot = new IrcBot();
	}
	
	@PostConstruct
	void connectToIrcServer() {
		bot.setVerbose(true);
		bot.setEncoding("utf-8");
		/*
		try {
			println 'Connecting to irc1.inet.fi ...'
			bot.connect("irc1.inet.fi");
			println 'Connected'
		} catch(Exception e) {
			println e.getMessage()
		}
		bot.joinChannel("#yougamers2");
		bot.joinChannel("#ep-dev");
		*/
		println 'Connected to server and joined channels'
	}
	
	
	def restart() {
		println "Restarting IrcService"
		bot.disconnect()
		bot = new IrcBot()
		connectToIrcServer()
	}

    def getMessages(String channel) {
		return bot.getMessagesFromChannel(channel).reverse()
    }

	def sendMessage(String channel, String message) {
		def words = message.split()
		def m = ""
		words.each {
		    if(it.startsWith("http://") || it.startsWith("https://")) {
				def shortLink = IsgdService.shortenUrl(it)
		        m += "${shortLink} "
		    } else {
		        m += "${it} "
		    }
		}
		bot.sendMessage(channel, m)
		bot.onMessage(channel, "HeikkiV__", "", "", m)
	}
}
