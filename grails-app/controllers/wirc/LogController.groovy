package wirc

import grails.converters.*

class LogController {
	
	def ircService 

    def index = {
		[messages:ircService.getMessages(''), channel: (session.channel) ? session.channel : '#ep-dev']
	}
	
    def mobile = {
		index()
	}
	
	def ajaxMessages = {
		[messages:ircService.getMessages(''), channel:params.channel]
	}
	
	def jsonMessages = {
		ajaxMessages()
	}
	
	def sendMessage = {
		ircService.sendMessage(params.channel, params.message)
		session.channel = params.channel
		redirect(action:index)
	}
	
}
