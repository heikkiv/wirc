<!DOCTYPE html> 
<html> 
	<head> 
	<title>${channel}</title> 
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1"> 
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.css" />
	<script src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.js"></script>
	<script type="text/JavaScript">
		$(document).ready(function() {
				
			$('#refresh').click(function() {
		        window.location.reload(true);
		    });
			
		});
		
	</script>
</head> 
<body> 

<div data-role="page" id="one">

	<div data-role="header" data-position="inline">
		<a href="#popup" data-role="button" data-rel="dialog" data-transition="pop">Post</a>
		<h1>${channel}</h1>
		<a id="refresh" href="mobile" data-role="button" data-icon="refresh" class="ui-btn-right">Refresh</a>
	</div><!-- /header -->

	<div data-role="content">	
		<ul data-role="listview">
			<g:each var="m" status="s" in="${messages}">
			 	<% if(m.channel == channel) { %>
					<li>				
						<h4>${m.sender}</h4>
						<p>${m.text}</p>
						<p class="ui-li-aside"><strong>${m.time.format("k:mm:ss")}</strong></p>
					</li>
				<% } %>
			</g:each>
		</ul>		
	</div><!-- /content -->
	
	<div data-role="footer">
		<h4>${channel}</h4>
	</div><!-- /footer -->

</div><!-- /page -->

<div data-role="page" id="popup">

	<div data-role="header" data-theme="e">
		<h1>Send message</h1>
	</div><!-- /header -->

	<div data-role="content" data-theme="d">	
		<form action="sendMessage" method="post" data-ajax="false">
			<textarea name="message"></textarea>
			<input type="hidden" name="channel" value="#ep-dev">
			<input type="hidden" name="mobile" value="true">
			<button type="submit" name="submit" value="submit-value">Post</button>
		</form>
	</div><!-- /content -->
	
</div><!-- /page popup -->

</body>
</html>
