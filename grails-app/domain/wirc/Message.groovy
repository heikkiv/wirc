package wirc

class Message extends WircMessage {

	Date time = new Date()
	String sender = ''
	String text = ''
	String channel = ''
    String category = ''
    
    public Message(String tsv) {
        def words = tsv.split('\t')
        if(words.size() >= 4) {
            this.time = new Date(words[0] as Long)
            this.channel = words[1]
            this.sender = words[2]
            this.text = words[3]
            this.category = (words.size() >= 5) ? words[4] : 'unknown'
        }
    }
    
    public String toTsv() {
        def temp = text.replace('\t', ' ')
        return "$time.time\t$channel\t$sender\t$temp\t$category".toString()
    }
    
}
