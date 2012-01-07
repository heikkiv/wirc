<html>
    <head>
        <title>#yougamers2</title>
		<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
		<script type="text/JavaScript" src="${resource(dir:'js',file:'bootstrap-tabs.js')}"></script>
		<script type="text/JavaScript">
		
			function updateMessages(channel) {
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
			
			function changeChannel(channel) {
				console.log('Changing channel to: ' + channel);
				updateMessages(channel);
				if(channel === '#ep-dev') {
					$('#yougamers-form').hide();
					$('#ep-dev-form').show();
				} else {
					$('#yougamers-form').show();
					$('#ep-dev-form').hide();
				}
			}
		
			$(document).ready(function() {
				
    			$('a').click(function() {
			        //updateMessages('#ep-dev');
			    });
			
				$('#ep-dev-form a').click(function(e) {
					$('#ep-dev-form form').submit();
				});
				
				$('#yougamers-form a').click(function(e) {
					$('#yougamers-form form').submit();
				});
			
				$('.tabs').tabs();
				$('.tabs').bind('change', function (e) {
				    var channel = $(e.target).attr('href');
					changeChannel(channel);
				});
				
				//console.log(${channel});
				changeChannel('${channel}');
			});
			
			//setInterval( "updateMessages()", 5000 );
		</script>
		<link rel="StyleSheet" href="../css/wirc.css"/>
		<link rel="stylesheet" href="${resource(dir:'css',file:'bootstrap.css')}" />
    </head>
    <body>
		<div class="container">
		<ul class="tabs" data-tabs="tabs">
		  <li <% if(channel == '#ep-dev') { %>class="active"<% } %>><a href="#ep-dev">#ep-dev</a></li>
		  <li <% if(channel == '#yougamers2') { %>class="active"<% } %>><a href="#yougamers2">#yougamers2</a></li>
		</ul>
		<div id="yougamers-form">
			<g:form name="updateForm" action="sendMessage">
				<input class="span6" name="message" type="text">
				<input type="hidden" name="channel" value="#yougamers2">
				<a href="#" class="btn small">yougamers2</a>
			</g:form>
		</div>
		<div id="ep-dev-form">
			<g:form name="updateForm" action="sendMessage">
				<input class="span6" name="message" type="text">
				<input type="hidden" name="channel" value="#ep-dev">
				<a href="#" class="btn small">ep-dev</a>
			</g:form>
		</div>
		<a href="#" id="update">Update now ${channel}</a>
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