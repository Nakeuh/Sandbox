
/**
 * Creates the Bullet object drawn on the Player Canvas
 */
function Bullet(x, y, width, height,color) {
	Drawable.call(this,x, y, width, height,color);
	this.alive = false; // Is true if the bullet is currently in use

}
Bullet.prototype = Object.create(Drawable.prototype);
Bullet.prototype.constructor = Bullet;
	
/*
 * Sets the bullet values
 */
Bullet.prototype.spawn = function(x, y, speed, range) {

		this.x = this.startX = x;
		this.y = this.startY = y;
			
		this.mouseX = xMouse;
		this.mouseY = yMouse;

		this.speed = speed;
		this.range = range;
		this.alive = true;
	};
	
Bullet.prototype.draw = function() {
	this.context.fillStyle = this.color;
	 var limit = getPosInMouseDirection(this.startX ,this.startY,this.mouseX,this.mouseY,this.range);

	if (!isInRange(this.x,this.y,this.startX ,this.startY,limit.x,limit.y)) {
		return true;
	}
	else { // In the range
			
		// Clear old bullet image
		this.context.clearRect(this.x-this.width-1, this.y-this.height-1, this.width*2+2, this.height*2+2);
		
		var deltaX = limit.x - this.startX ;
		var deltaY = limit.y - this.startY ;
		
		var moveX = Math.abs(this.speed / Math.sqrt(1+Math.pow(deltaY/deltaX,2)));
		var moveY = Math.abs(moveX * (deltaY/deltaX));

		var coefX=1;
		var coefY=1;
		
		if(deltaX<0){
			coefX=-1;
		}
	
		if(deltaY<0){
			coefY=-1;
		}
			
		this.x += moveX*coefX; 
		this.y += moveY*coefY; 

		this.context.beginPath();
		this.context.arc(this.x,this.y,this.height,0,2*Math.PI);
		this.context.fill();
	}
};

/*
 * Resets the bullet values
 */
Bullet.prototype.clear = function() {
	this.context.clearRect(this.x-this.width-1, this.y-this.height-1, this.width*2+2, this.height*2+2);

	this.x = 0;
	this.y = 0;
	this.speed = 0;
	this.alive = false;
};
