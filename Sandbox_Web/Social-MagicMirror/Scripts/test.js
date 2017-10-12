hideModule: function (modulename, delay){
  MM.getModules().withClass('clock').enumerate(function(module){
  				module.hide(delay, function(){
  				});
  });
}

hideAllModule: function (delay){
  MM.getModules().enumerate(function(module){
  				module.hide(delay, function(){
  				});
  });
}

changeColorClass: function (color, classname) {
    elements = document.getElementsByClassName(classname);
    for (var i = 0; i < elements.length; i++) {
        elements[i].style.color=color;
        elements[i].style.borderbottom=color; // for module-header

    }
}

changeColorTheme: function(color){
  switch(color){

    case "pink":
    changeColorClass(color,"normal");
    changeColorClass(color,"bright");
    changeColorClass(color,"dimmed");
    changeColorClass(color,"module-header");

    document.body.style.color=color;
    break;

    case "red":
    break;

    case "blue":
    break;

    case "green":
    break;

    case "black":
    break;

    default:
    break;

  }

}
