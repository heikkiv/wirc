package wirc

import grails.converters.*

class LogController {
	
	def ircService 

    def index = {
    	def channel = '#ep-dev'
        if(params.channel) {
            channel = params.channel
        }
        if(params.from) {
            channel = params.from
        }
        session.channel = channel
        def messages = ircService.getMessages(channel)
        def privateMessageSenders = ircService.getPrivateMessagesSenders()
        def users = ircService.getUsers(channel)
        def unreadMessages = [:]
        privateMessageSenders.each { sender ->
        	unreadMessages[sender] = ircService.getUnreadMessageCount(sender)
        }
        def currentChannelUnreadMessages = ircService.getUnreadMessageCount(channel)
        def yougamersUnreadMessages = ircService.getUnreadMessageCount('#yougamers2')
        def epdevUnreadMessages = ircService.getUnreadMessageCount('#ep-dev')
        ircService.resetUnreadMessageCount(channel)
		return [
			messages: messages, 
			privateMessageSenders: privateMessageSenders, 
			unreadMessages: unreadMessages,
			currentChannelUnreadMessages: currentChannelUnreadMessages,
			yougamersUnreadMessages: yougamersUnreadMessages,
			epdevUnreadMessages: epdevUnreadMessages, 
			users: users, 
			channel: channel, 
			from: params.from
		]
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
    
    def messageCount = {
        def channel = (session.channel) ? session.channel : '#ep-dev'
        def n = ircService.getMessageCount(channel)
        render(contentType: 'text/json') {[count: n]}
    }
	
}
