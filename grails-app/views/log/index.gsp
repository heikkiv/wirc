<html>
    <head>
        <title>#yougamers2</title>
		<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
		<script type="text/JavaScript" src="${resource(dir:'js',file:'bootstrap-tabs.js')}"></script>
		<script type="text/JavaScript">
		
			function updateMessages(channel) {
				console.log(channel.substring(1));
				$.get('ajaxMessages?channel=' + channel.substring(1), function(html, textStatus) {
	               	$('#messages').replaceWith(html);
					var i = 0;
					$('.message').each(function() {
						if( i == 100 ) {
							$(this).remove()
						} else {
							i += 1;
						}
					});
		        });
			}
		
			$(document).ready(function() {
				
    			$('a').click(function() {
			        //updateMessages('#ep-dev');
			    });
			
				$('.tabs').tabs();
				$('.tabs').bind('change', function (e) {
				    var channel = $(e.target).attr('href');
					console.log('Changing channel to: ' + channel);
					updateMessages(channel);
					if(channel === '#ep-dev') {
						$('#yougamers-form').hide();
						$('#ep-dev-form').show();
					} else {
						$('#yougamers-form').show();
						$('#ep-dev-form').hide();
					}
				});
				
				updateMessages('#ep-dev');
			});
			
			//setInterval( "updateMessages()", 5000 );
		</script>
		<link rel="StyleSheet" href="../css/wirc.css"/>
		<link rel="stylesheet" href="${resource(dir:'css',file:'bootstrap.css')}" />
    </head>
    <body>
		<div class="container">
		<ul class="tabs" data-tabs="tabs">
		  <li class="active"><a href="#ep-dev">#ep-dev</a></li>
		  <li><a href="#yougamers2">#yougamers2</a></li>
		</ul>
		<div id="yougamers-form">
			<g:form name="updateForm" action="sendMessage">
				<g:textArea name="message" rows="3" cols="50"/>
				<input type="hidden" name="channel" value="#yougamers2">
				<input type="submit" value="Yougamers" />
			</g:form>
		</div>
		<div id="ep-dev-form">
			<g:form name="updateForm" action="sendMessage">
				<g:textArea name="message" rows="3" cols="50"/>
				<input type="hidden" name="channel" value="#ep-dev">
				<input type="submit" value="Ep-dev" />
			</g:form>
		</div>
		<a href="#" id="update">Update now</a>
		<div id="messages"></div>
		<!--
		<div id="messages">
			<g:each var="m" status="s" in="${messages}">
				<div class="${m.channel.replace('#', '')}">[${m.time.format("k:mm:ss")} &lt;${m.channel}:${m.sender}&gt;] ${m.text}</div>
			</g:each>
		</div>
		-->
		</div>
    </body>
</html>