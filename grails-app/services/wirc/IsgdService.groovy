package wirc

class IsgdService {

    static String shortenUrl(String longUrl) {
		try {
			return new URL("http://is.gd/api.php?longurl=${longUrl.encodeAsHTML()}").text 
		} catch(Exception e) {
			return longUrl
		}
    }
}
