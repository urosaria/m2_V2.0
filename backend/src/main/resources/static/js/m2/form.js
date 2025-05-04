function blankCheck(val) {
  var check=true;
  if( val == '' || val == 'undefined') {
    check=false;
  }
  return check;
}

function blankChecktho(val) {
  var check=true;
  if( val == '' || val == 'undefined' || val<100){
    check=false;
  }
  return check;
}

function maxLengthCheck(object){
  if(object.value.length > 6) {
    object.value = object.value.slice(0, 6);
  }
}

function maxLengthCheckAmount(object){
  if(object.value.length > 3) {
    object.value = object.value.slice(0, 3);
  }
}