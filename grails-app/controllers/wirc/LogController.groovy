package wirc

import grails.converters.*

class LogController {
	
	def ircService 

    def index = {
        if(params.channel) {
            session.channel = params.channel
        }
		return [messages: ircService.getMessages(''), channel: (session.channel) ? session.channel : '#ep-dev']
	}
	
    def mobile = {
		index()
	}
	
    def message = {
		def m = ircService.getMessages('')[params.index as int]
		[message: m, text: markLinks(m.text), channel: (session.channel) ? session.channel : '#ep-dev']
	}
	
	def multi = {
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
		if(params.mobile) {
			redirect(url: 'http://pertti.dyndns.org/wirc/log/mobile')
		} else {
			redirect(action:index)
		}
	}
	
	def markLinks(String message) {
		def words = message.split()
		def m = ""
		words.each {
		    if(it.startsWith("http://") || it.startsWith("https://")) {
		        m += "<a href='${it}'>${it}</a> "   
		    } else {
		        m += "${it.encodeAsHTML()} "
		    }
		}
		return m
	}
	
}
