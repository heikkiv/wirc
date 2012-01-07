package wirc

import grails.converters.XML

class LogController {
	
	def ircService 

    def index = {
		[messages:ircService.getMessages(''), channel: (session.channel) ? session.channel : '#yougamers2']
	}
	
	def ajaxMessages = {
		[messages:ircService.getMessages(''), channel:params.channel]
	}
	
	def sendMessage = {
		ircService.sendMessage(params.channel, params.message)
		session.channel = params.channel
		redirect(action:index)
	}
	
}
