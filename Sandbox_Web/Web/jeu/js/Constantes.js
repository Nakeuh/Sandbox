var DEFAULT_PLAYER_SIZE = 5;
var DEFAULT_PLAYER_SPEED = 1;
var DEFAULT_PLAYER_COLOR = "rgba(0, 0, 255, 1)";

var DEFAULT_BULLET_RANGE = 40;
var DEFAULT_BULLET_SIZE = 2;
var DEFAULT_BULLET_SPEED = 3;
var DEFAULT_BULLET_COLOR = "rgba(255, 0, 0, 1)";

var DEFAULT_VISION_RANGE = 60;
var RATIO_WINDOW = 1.8;
var FPS = 60;

var NB_BULLET_MAX = 10;
var DEFAULT_FIRE_RATE = 5;

var DEFAULT_BACKGROUND_COLOR = "rgba(200, 200, 200, 1)"
/**
 * Define a singleton to hold all the images for the game so images
 * are only ever created once.
 */
var imageRepository  = new function () {
	
	this.background = new Image();
	
	// Ensure all images have loaded before starting the game
	var numImages = 1;
	var numLoaded = 0;
	function imageLoaded() {
		numLoaded++;
		if (numLoaded === numImages) {
			window.init();
		}
	}
	
	this.background.src = "images/background.png";
}

// The keycodes that will be mapped when a user presses a button.
KEY_CODES = {
  32: 'space',
  
  37: 'left',
  81: 'left',
  65: 'left',

  39: 'right',
  68: 'right',
  
  38: 'up',
  87: 'up',
  90: 'up',
  
  40: 'down',
  83: 'down',
}

/** Creates the array to hold the KEY_CODES and sets all their values
 *  to false. Checking true/flase is the quickest way to check status
 * of a key press and which one was pressed when determining
 *  when to move and which direction.
 */
KEY_STATUS = {};
for (code in KEY_CODES) {
  KEY_STATUS[ KEY_CODES[ code ]] = false;
}

/**
 * Sets up the document to listen to onkeydown events (fired when
 * any key on the keyboard is pressed down). When a key is pressed,
 * it sets the appropriate direction to true to let us know which
 * key it was.
 */
document.onkeydown = function(e) {
  // Firefox and opera use charCode instead of keyCode to
  // return which key was pressed.
  var keyCode = (e.keyCode) ? e.keyCode : e.charCode;
  if (KEY_CODES[keyCode]) {
    e.preventDefault();
    KEY_STATUS[KEY_CODES[keyCode]] = true;
  }
}

/**
 * Sets up the document to listen to ownkeyup events (fired when
 * any key on the keyboard is released). When a key is released,
 * it sets teh appropriate direction to false to let us know which
 * key it was.
 */
document.onkeyup = function(e) {
  var keyCode = (e.keyCode) ? e.keyCode : e.charCode;
  if (KEY_CODES[keyCode]) {
    e.preventDefault();
    KEY_STATUS[KEY_CODES[keyCode]] = false;
  }
}