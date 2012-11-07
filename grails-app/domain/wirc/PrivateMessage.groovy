package wirc

class PrivateMessage extends WircMessage {
    Date time = new Date()
    String sender = ''
    String login = ''
    String hostname = ''
    String text = ''
    
    public String toTsv() {
        def temp = text.replace('\t', ' ')
        return "$time.time\t$sender\t$sender\t$temp".toString()
    }
}
