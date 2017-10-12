// https://openclassrooms.com/courses/des-applications-ultra-rapides-avec-node-js/node-js-mais-a-quoi-ca-sert

// require the 'http' library of NodeJS
// 'http' library is used to create a web server
var http = require('http');

//create the httpServer
//callback function executed each time a  guest connect to the server
//req : guest's request
//res : server's response
var server = http.createServer(function(req, res) {

	// 200 : http success code : wrote in the header of the response
  res.writeHead(200, {"Content-Type": "text/html"});
	
  res.write('<html>'+
'    <head>'+
'        <meta charset="utf-8" />'+
'        <title>Ma page Node.js !</title>'+
'    </head>'+ 
'    <body>'+
'       <p>Voici un paragraphe <strong>HTML</strong> !</p>'+
'    </body>'+
'</html>');
	
  res.end();

});

server.on('close',function(){
	console.log('Fermeture du serveur.');
})

// don't use port 80 for tests (could be already used), but do in production
server.listen(8080);