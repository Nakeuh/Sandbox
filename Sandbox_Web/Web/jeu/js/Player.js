
var fireRate;
var counter;

/**
 * Create the Player object that the player controls.
 */
function Player(x, y, width, height,color) {
	Drawable.call(this,x, y, width, height,color);
	
	this.bulletPool = new BulletPool(NB_BULLET_MAX);
	this.bulletPool.init();
	
	fireRate = 5;
	counter = 0;
}

Player.prototype = Object.create(Drawable.prototype);
Player.prototype.constructor = Player;

Player.prototype.draw = function() {
	this.context.fillStyle = this.color;

	this.context.beginPath();
	this.context.arc(this.x,this.y,this.height,0,2*Math.PI);
	this.context.fill();
};
	
	// TODO : yerk -> optimize
Player.prototype.drawMist = function(){	
	this.context.strokeStyle = "rgba(50, 50, 50, 1)";
		
	this.context.lineWidth=canvasPlayer.width;
	this.context.beginPath();
	this.context.arc(this.x, this.y, visionRange+this.context.lineWidth/2, 0, 2 * Math.PI, false);

	this.context.stroke();
		
	this.context.lineWidth=1;					
}
	
Player.prototype.move = function() {
	counter++;
						
	if (KEY_STATUS.space && counter >= fireRate) {
		this.fire();
		counter = 0;
	}
		
	// Determine if the action is move action
	if (KEY_STATUS.left || KEY_STATUS.right ||
		KEY_STATUS.down || KEY_STATUS.up) {
			// The player moved, so erase it's current shape so it can be redrawn in it's new location
			//this.context.clearRect(this.x-this.width-1, this.y-this.height-1, this.width*2+2, this.height*2+2);
			// Update x and y according to the direction to move and redraw the player. 
			
		if (KEY_STATUS.left) {
			this.x -= playerSpeed
			if (this.x <= 0 + this.width) 
				this.x = 0 + this.width;
		} 
			
		if (KEY_STATUS.right) {
			this.x += playerSpeed
			if (this.x >= this.canvasWidth - this.width){
				this.x = this.canvasWidth - this.width;
			}
		} 
			
		if (KEY_STATUS.up) {
			this.y -= playerSpeed
			if (this.y <= 0 + this.height)
				this.y = 0 + this.height;
		} 
			
		if (KEY_STATUS.down) {
			this.y += playerSpeed
			if (this.y >= this.canvasHeight - this.height){
				this.y = this.canvasHeight - this.height;
			}
		}
	}

	// Clear and redraw
		
	this.context.clearRect(this.x-visionRange, this.y-visionRange, visionRange*2,visionRange*2);

	this.drawMist();
	this.draw();

};

Player.prototype.fire = function() {
	this.bulletPool.get(this.x,this.y, bulletSpeed, bulletRange,DEFAULT_BACKGROUND_COLOR);
};
