package wirc

import grails.plugins.rest.client.RestBuilder

class BoxCarService {
    
    private String notificationUrl = 'http://boxcar.io/devices/providers/w0HwjplsXU2ujtJCi4U1/notifications' 

    public def test() {
        def rest = new RestBuilder()
        def resp = rest.get('http://google.com')
        return resp.getStatusCode()
    }
    
    public boolean sendNotification(String message, String email) {
        def rest = new RestBuilder()
        def resp = rest.post(notificationUrl) {
            setProperty 'email', email
            setProperty 'notification[message]', message
        }
        return resp.getStatusCode().value() == 200
    }

}
