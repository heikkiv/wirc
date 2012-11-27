<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Wirc</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="../bootstrap/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }
    </style>
    <link href="../bootstrap/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="../assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
    
	<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
	<script type="text/JavaScript">
		$(document).ready(function() {
				
            function sendMessage() {
		        $.post('sendMessage', {channel: '<%= (from) ? from : channel %>', message: $('#messageText').val()}, function(resp) {
		            window.location.reload(true);
		        });
            }
            
			$('#postForm').submit(function(event) {
                event.preventDefault();
                sendMessage();
		    });
            
			$('#postButton').click(function(event) {
                event.preventDefault();
                sendMessage();
		    });
            
            function checkNewMessages() {
		        $.get('messageCount', function(resp) {
		            if(messageCount > 0 && messageCount < resp.count) {
		                console.log('New messages');
                        $('#newMessagesNotice').show();
		            }
                    messageCount = resp.count;
		        });
            }
            
            var messageCount = -1;
            setInterval(checkNewMessages, 5000);
			
		});
		
	</script>
    
  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">wirc</a>
          <div class="nav-collapse collapse">
            <p class="navbar-text pull-right">
              Logged in as <a href="#" class="navbar-link">Username</a>
            </p>
            <ul class="nav">
              <li class="active"><a href="#">Home</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span2">
            
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              <li class="nav-header">Channels</li>
              <li class="<%= (!from && channel == '#ep-dev') ? 'active' : '' %>"><a href="?channel=%23ep-dev">#ep-dev</a></li>
              <li class="<%= (!from && channel == '#yougamers2') ? 'active' : '' %>"><a href="?channel=%23yougamers2">#yougamers2</a></li>
            </ul>
          </div><!--/.well -->
          
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              <li class="nav-header">Private messages</li>
              <g:each var="sender" status="s" in="${privateMessageSenders}">
                  <li class="<%= (from == sender) ? 'active' : '' %>"><a href="?from=${sender}">${sender}</a></li>
              </g:each>
            </ul>
          </div><!--/.well -->
        
        </div><!--/span-->
        <div class="span10">
            
            <div id='newMessagesNotice' class="row-fluid" style="display: none">
                <div class="alert">
                  <button type="button" class="close" data-dismiss="alert">×</button>
                  New messages
                </div>
            </div>
            
            <div class="row-fluid input-append">
                <form id="postForm">
                  <input id="messageText" type="text" class="input-xxlarge">
                  <button id="postButton" class="btn" type="button">Post</button>
                </form>
            </div>
          
			<g:each var="m" status="s" in="${messages}">
		 	    <div class="row-fluid">
                    <div class="span12"><small>${m.time.format("k:mm")}</small> <strong>${m.sender}</strong> ${m.textWithLinks}</div>
                </div>
			</g:each>
          
        </div><!--/span-->
      </div><!--/row-->

      <hr>

      <footer>
        <p>&copy; Heikki Verta 2012</p>
      </footer>

    </div><!--/.fluid-container-->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="../bootstrap/js/bootstrap.js"></script>

  </body>
</html>