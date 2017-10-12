
var canvasMap;
var canvasPlayer;
var canvasBullet;

var playerSize;

var xMouse;
var yMouse;

var playerSize;
var playerSpeed;
var bulletRange;
var visionRange;

var bulletSize;
var bulletSpeed;


var bulletColor;
var playerFireRate;

function Game(){
	this.init = function() {	
		resizeGame();
		window.addEventListener('resize', resizeGame, false);
		// Test to see if canvas is supported
		if (canvasMap.getContext('2d')) {

			// Initialize objects to contain their context and canvas
			// information
			Background.prototype.context = canvasMap.getContext('2d');
			Background.prototype.canvasWidth = canvasMap.width;
			Background.prototype.canvasHeight = canvasMap.height;
			
			Player.prototype.context = canvasPlayer.getContext('2d');
			Player.prototype.canvasWidth = canvasPlayer.width;
			Player.prototype.canvasHeight = canvasPlayer.height;
			
			Bullet.prototype.context = canvasBullet.getContext('2d');
			Bullet.prototype.canvasWidth = canvasBullet.width;
			Bullet.prototype.canvasHeight = canvasBullet.height;
			
			// Initialize the background object
			this.background = new Background(0,0,0,0,DEFAULT_BACKGROUND_COLOR);
			
			this.player = new Player(playerStartX, playerStartY, playerSize,playerSize,DEFAULT_PLAYER_COLOR);
			
			// TODO : Center with the player width/height
			var playerStartX = canvasPlayer.width/2 ; 
			var playerStartY = canvasPlayer.height/2;
						this.player = new Player(playerStartX, playerStartY, playerSize,playerSize,DEFAULT_PLAYER_COLOR);

			return true;
		} else {
			return false;
		}
	};

	// Start the animation loop
	this.start = function() {
		this.player.draw();
		animate();
	};

}

/**
 * The animation loop. Calls the requestAnimationFrame shim to
 * optimize the game loop and draws all game objects. This
 * function must be a gobal function and cannot be within an
 * object.
 */
function animate() {
	requestAnimFrame( animate );
	game.background.draw();
	game.player.move();
	game.player.bulletPool.animate();
}

// TODO : redraw elements at the right place
function resizeGame() {		
	var windowWidth = window.innerWidth;
	var windowHeight = window.innerHeight;
		
	var windowRatioWidthToHeight = windowWidth / windowHeight;
			
	if (windowRatioWidthToHeight > RATIO_WINDOW) {
	  canvasPlayer.height = canvasBullet.height = canvasMap.height = windowHeight;
	  
	  canvasPlayer.width = canvasBullet.width = canvasMap.width = windowHeight * RATIO_WINDOW;
	} else { 
	  canvasPlayer.width = canvasBullet.width = canvasMap.width = windowWidth;
	  canvasPlayer.height = canvasBullet.height = canvasMap.height = windowWidth / RATIO_WINDOW;
	}	

	canvasPlayer.style.marginLeft = canvasBullet.style.marginLeft = canvasMap.style.marginLeft = (-canvasMap.width / 2) + 'px';
	canvasPlayer.style.marginTop = canvasBullet.style.marginTop = canvasMap.style.marginTop = (-canvasMap.height / 2) + 'px';

	playerSize = percentToPx(DEFAULT_PLAYER_SIZE);
	playerSpeed = percentToPx(DEFAULT_PLAYER_SPEED);
	bulletRange = percentToPx(DEFAULT_BULLET_RANGE);

	bulletSize = percentToPx(DEFAULT_BULLET_SIZE);
	bulletSpeed = percentToPx(DEFAULT_BULLET_SPEED);	
	visionRange = percentToPx(DEFAULT_VISION_RANGE);
}

/**
 * Used to get dynamicaly the position in the mouse in the canvas (and not in the window)
 *
 */
document.onmousemove = function(event) {
	var e = event || window.event; 
	
	var rect = canvasPlayer.getBoundingClientRect();
    xMouse = e.clientX - rect.left;
    yMouse = e.clientY - rect.top;
}

/**
 * requestAnim shim layer by Paul Irish
 * Finds the first API that works to optimize the animation loop,
 * otherwise defaults to setTimeout().
 */
window.requestAnimFrame = (function(){
	return  window.requestAnimationFrame   ||
			window.webkitRequestAnimationFrame ||
			window.mozRequestAnimationFrame    ||
			window.oRequestAnimationFrame      ||
			window.msRequestAnimationFrame     ||
			function(callback, element){
				window.setTimeout(callback, 1000 / FPS);
			};
})();

var game = new Game();

/**
 * Initialize the Game and starts it.
 */
window.onload = function () {
	canvasMap = document.getElementById('map');
	canvasPlayer = document.getElementById('player');
	canvasBullet = document.getElementById('otherPlayers');
		
	if(game.init())
		game.start();
}
