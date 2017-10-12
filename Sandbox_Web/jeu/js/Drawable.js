
/**
 * Creates an abstract object which will be the base class for
 * all drawable objects in the game. Sets up default variables
 * that all child objects will inherit, as well as the default
 * functions.
 */
function Drawable(x, y, width, height,color) {
	console.log("new drawable");
		this.x = x;
		this.y = y;
		this.width=width;
		this.height=height;
		this.color=color;
	

	this.speed = 0;

}

Drawable.prototype.draw = function(){};
