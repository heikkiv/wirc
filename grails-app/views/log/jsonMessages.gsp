<ul data-role="listview">
	<g:each var="m" status="s" in="${messages}">
	 	<% if(m.channel == '#' + params.channel) { %>
			<li>				
				<h3>${m.sender}</h3>
				<p>${m.text}</p>
				<p class="ui-li-aside"><strong>${m.time}</strong></p>
			</li>
		<% } %>
	</g:each>
</ul>
