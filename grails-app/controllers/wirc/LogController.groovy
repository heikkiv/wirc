package wirc

import grails.converters.*

class LogController {
	
	def ircService 

    def index = {
        if(params.channel) {
            session.channel = params.channel
        }
        def channel = (session.channel) ? session.channel : '#ep-dev'
        def messages = []
        if(params.from) {
            messages = ircService.getPrivateMessages(params.from)
        } else {
            messages = ircService.getMessages(channel)
        }
		return [messages: messages, privateMessageSenders: ircService.getPrivateMessagesSenders(), channel: channel, from: params.from]
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
        println "Sending message to ${params.channel}: ${params.message}"
		ircService.sendMessage(params.channel, params.message)
		session.channel = params.channel
		if(params.mobile) {
			redirect(url: 'http://pertti.dyndns.org/wirc/log/mobile')
		} else {
			redirect(action: 'index')
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
