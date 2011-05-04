package wirc

class IrcMonitorJob {
	
    //def timeout = 5 * 60 * 1000 // Every 5 mins
	//def delay = 5 * 60 * 1000 // Every 5 mins
	def timeout = 60 * 1000 
	def delay = 30 * 1000 
	
	def ircService

    def execute() {
	/*
        if( !ircService.bot.isConnected() ) {
			ircService.restart()
		}
		def channels = ircService.bot.getChannels() as List
		if( channels.contains("#yougamers2") ) {
			ircService.restart()
		}
		println "IrcService checked"
		*/
    }
}
