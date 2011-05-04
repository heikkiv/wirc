package wirc

import grails.converters.XML

class LogController {
	
	def ircService 

    def index = {
		[messages:ircService.getMessages(''), channel:'yougamers']
	}
	
	def ajaxMessages = {
		[messages:ircService.getMessages(''), channel:params.channel]
	}
	
	def sendMessage = {
		ircService.sendMessage(params.channel, params.message)
		redirect(action:index)
	}
	
}
