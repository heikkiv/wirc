<!DOCTYPE html> 
<html> 
	<head> 
	<title>${channel}</title> 
	<meta name="viewport" content="width=device-width, initial-scale=1"> 
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.css" />
	<script src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.js"></script>
	<script type="text/JavaScript">
		
		window.heikkiv = {};
		window.heikkiv.channel = '#ep-dev';
		
		function updateMessages(channel) {
			$.get('jsonMessages?channel=' + channel.substring(1), function(html, textStatus) {
				$('ul').replaceWith(html);
	        });
		}
			
		function changeChannel(ch) {
			console.log('Changing channel to: ' + ch);
			window.heikkiv.channel = ch;
			updateMessages(ch);
			if(ch === '#ep-dev') {
				$('#yougamers-form').hide();
				$('#ep-dev-form').show();
			} else {
				$('#yougamers-form').show();
				$('#ep-dev-form').hide();
			}
		}
		
		$(document).ready(function() {
			
			$('a').click(function() {
				console.log('refresh');
				window.location.reload(true);
		        //updateMessages('#ep-dev');
		    });
			
			$('#ep-dev-form a').click(function(e) {
				$('#ep-dev-form form').submit();
			});
				
			$('#yougamers-form a').click(function(e) {
				$('#yougamers-form form').submit();
			});
							
		});
			
		//updateMessages(window.heikkiv.channel);
		//setInterval( "updateMessages(window.heikkiv.channel)", 5000 );
	</script>
</head> 
<body> 

<div data-role="page">

	<div data-role="header" data-position="inline">
		<h1>${channel}</h1>
		<a href="mobile" data-role="button" data-icon="refresh" class="ui-btn-right">Refresh</a>
	</div><!-- /header -->

	<div data-role="content">	
		<ul data-role="listview">
		<!--
					<li>				
						<h3>Stephen Weber</h3>
						<p><strong>You've been invited to a meeting at Filament Group in Boston, MA</strong></p>
						<p>Hey Stephen, if you're available at 10am tomorrow, we've got a meeting with the jQuery team.</p>
						<p class="ui-li-aside"><strong>6:24</strong>PM</p>
					</li>
					<li><a href="index.html">
				
						<h3>jQuery Team</h3>
						<p><strong>Boston Conference Planning</strong></p>
						<p>In preparation for the upcoming conference in Boston, we need to start gathering a list of sponsors and speakers.</p>
						<p class="ui-li-aside"><strong>9:18</strong>AM</p>
				
					</a></li>
					<li><a href="index.html">
						<h3>Avery Walker</h3>
						<p><strong>Re: Dinner Tonight</strong></p>
						<p>Sure, let's plan on meeting at Highland Kitchen at 8:00 tonight. Can't wait! </p>
						<p class="ui-li-aside"><strong>4:48</strong>PM</p>
					</a></li>
			-->
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

</div><!-- /page -->

</body>
</html>
