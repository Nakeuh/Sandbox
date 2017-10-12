
/**
 * Creates the Background object which will become a child of
 * the Drawable object. The background is drawn on the "background"
 * canvas. It's possible to make it move later. 
 */
function Background(x, y, width, height,color) {
	Drawable.call(this,x, y, width, height,color);
	this.speed = 0; 
}

Background.prototype = Object.create(Drawable.prototype);
Background.prototype.constructor = Background;

Background.prototype.draw = function() {
	this.context.fillStyle = this.color;
	//this.context.drawImage(imageRepository.background, this.x, this.y,canvasMap.width,canvasMap.height);
	this.context.fillRect(this.x, this.y,canvasMap.width,canvasMap.height);
};
	