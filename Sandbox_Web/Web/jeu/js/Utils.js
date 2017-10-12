

function percentToPx(percent){
	return percent*canvasMap.height/100;
}

function pxToPercent(px){
	return px*100/canvasMap.height;
}

function toPercentOfHeight(size){
	return size*canvasMap.height/100;
}

function isInRange(x,y,xOrigin,yOrigin,xLimit,yLimit){
	var coefX=1;
	var coefY=1;
	var inRange = true;

	if(xOrigin<xLimit){
		coefX=1;
	}else{
		coefX=-1;
	}
	
	if(yOrigin<yLimit){
		coefY=1;
	}else{
		coefY=-1;
	}
	
	if(xOrigin<xLimit){
		inRange &= (x>=xOrigin && x<=xLimit);
	}else{
		inRange &= (x<=xOrigin && x>=xLimit);
	} 
	
	if(yOrigin<yLimit){
		inRange &= (y>=yOrigin && y<=yLimit);
	}else{
		inRange &= (y<=yOrigin && y>=yLimit);
	} 
	
	return inRange;
}

function getPosInMouseDirection(xOrigin,yOrigin,mouseX,mouseY,distance){
	var coefX=1;
	var coefY=1;

	if(xOrigin<mouseX){
		coefX=1;
	}else{
		coefX=-1;
	}
	
	if(yOrigin<mouseY){
		coefY=1;
	}else{
		coefY=-1;
	}
	
	var tmp = Math.abs((mouseY - yOrigin) / (mouseX - xOrigin));

	var tmp2 = distance / Math.sqrt(1 + Math.pow(tmp,2));

	var x = xOrigin + coefX*tmp2;
	var y = yOrigin + coefY*tmp*tmp2;

	return {x, y};
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
				window.setTimeout(callback, 1000 / 60);
			};
})();