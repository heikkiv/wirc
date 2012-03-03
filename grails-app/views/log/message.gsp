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
				
			$('#back').click(function() {
		        $.mobile.changePage('mobile', {reverse: true});
		    });
			
		});
		
	</script>
</head> 
<body> 

<div data-role="page">

	<div data-role="header">
		<a id="back" href="mobile" data-role="button" data-icon="back" data-transition="left">Back</a>
		<h1>[${message.time.format("k:mm:ss")} &lt;${message.channel}:${message.sender}&gt;]</h1>
	</div><!-- /header -->

	<div data-role="content">	
		<p>${text}
	</div><!-- /content -->
	
	<div data-role="footer">
		<h4>${channel}</h4>
	</div><!-- /footer -->
	
</div><!-- /page popup -->

</body>
</html>
