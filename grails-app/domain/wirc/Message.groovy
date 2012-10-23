package wirc

class Message {

	long id
	Date time = new Date()
	String sender
	String text
	String channel
    
	public String getTextWithLinks() {
		def words = text.split()
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
