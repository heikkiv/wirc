<div id="messages">
	<g:each var="m" status="s" in="${messages}"> 
		<div class="${m.channel.replace('#', '')}">[${m.time.format("k:mm:ss")} &lt;${m.channel}:${m.sender}&gt;] ${m.text}</div>
	</g:each>
</div>