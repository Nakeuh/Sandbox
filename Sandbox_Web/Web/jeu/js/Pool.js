var maxSize;
var pool;
/**
 * Pool of Bullet object. 
 */
function BulletPool(max) {
	pool = [];
	maxSize = maxSize;
}
BulletPool.prototype.constructor = BulletPool;

/*
 * Populates the pool array with Bullet objects
 */
BulletPool.prototype.init = function() {
	for (var i = 0; i < maxSize; i++) {
		// Initalize the bullet object
		var bullet = new Bullet(0,0, bulletSize,bulletSize,DEFAULT_BULLET_COLOR);
		pool[i] = bullet;
	}
};
	
/*
 * Grabs the last item in the list and initializes it and
 * pushes it to the front of the array.
 */
BulletPool.prototype.get = function(x, y, speed, range) {
	if(!pool[maxSize - 1].alive) {
		pool[maxSize - 1].spawn(x, y, speed, range);
		pool.unshift(pool.pop());
	}
};
	
/*
 * Draws any in use Bullets. If a bullet goes off the screen,
 * clears it and pushes it to the front of the array.
 */
BulletPool.prototype.animate = function() {
	for (var i = 0; i < maxSize; i++) {
		// Only draw until we find a bullet that is not alive
		if (pool[i].alive) {
			if (pool[i].draw()) {
				pool[i].clear();
				pool.push((pool.splice(i,1))[0]);
			}
		}
		else{
			break;
		}
	}
};
