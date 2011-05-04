<html>
    <head>
        <title>#yougamers2</title>
		<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
		<script type="text/JavaScript">
			function updateMessages() {
				$.ajax({
		            type: 'GET',
		            url: 'ajaxMessages',
		            dataType: 'html',
		            success: function(html, textStatus) {
		                $('#messages').replaceWith(html);
						var i = 0;
						$('.message').each(function() {
							if( i == 100 ) {
								$(this).remove()
							} else {
								i += 1;
							}
						});
						
		            }
		        });
			}
		
			$(document).ready(function() {
    			$('a').click(function() {
			        updateMessages();
			    });
			});
			
			setInterval( "updateMessages()", 5000 );
		</script>
		<link rel="StyleSheet" href="../css/wirc.css"/>
    </head>
    <body>
		<g:form name="updateForm" action="sendMessage">
			<g:textArea name="message" rows="3" cols="50"/>
			<input type="hidden" name="channel" value="#yougamers2">
			<input type="submit" value="Yougamers" />
		</g:form>
		<g:form name="updateForm" action="sendMessage">
			<g:textArea name="message" rows="3" cols="50"/>
			<input type="hidden" name="channel" value="#ep-dev">
			<input type="submit" value="Ep-dev" />
		</g:form>
		<a href="#" id="update">Update now</a>
		<div id="messages">
			<g:each var="m" status="s" in="${messages}">
				<div class="${m.channel.replace('#', '')}">[${m.time.format("k:mm:ss")} &lt;${m.channel}:${m.sender}&gt;] ${m.text}</div>
			</g:each>
		</div>
    </body>
</html>