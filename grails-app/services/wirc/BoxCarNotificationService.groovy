package wirc

import grails.plugins.rest.client.RestBuilder

class BoxCarNotificationService implements NotificationService {
    
    private String notificationUrl = 'http://boxcar.io/devices/providers/w0HwjplsXU2ujtJCi4U1/notifications' 
    
    public boolean sendNotification(String message, String email, String url) {
        def rest = new RestBuilder()
        def resp = rest.post(notificationUrl) {
            setProperty 'email', email
            setProperty 'notification[message]', message
            setProperty 'notification[source_url]', url
        }
        return resp.getStatusCode().value() == 200
    }

}
