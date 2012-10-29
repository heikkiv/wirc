package wirc

abstract class WircMessage {

    long id = System.currentTimeMillis()

	public String getTextWithLinks() {
		def words = this.text.split()
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

    @Override
    public String toString() {
        return "[${this.sender} ${this.text}]".toString()
    }
    

}