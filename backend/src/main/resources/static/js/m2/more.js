$(document).ready(function() {

  var lastPageNo = $("#more").attr("data-last");
  var currentPage = $("#more").attr("data-current");

  if(lastPageNo<=currentPage){
    $('#more').hide();
  }

  $('#more').bind('click', function() {
    currentPage = parseInt(currentPage)+1;

    if(lastPageNo>=currentPage){

      var sUrl = $(this).attr("data-url");
      $.ajax({
        url: sUrl+"?page="+currentPage,
        dataType : "html",
        success: function(data) {
          data = $.trim(data) ;
          if( data != "" ){
            $("#more").before(data);
          }
        }
      });
      if(lastPageNo==currentPage){
        $('#more').hide();
      }
    }

  });
});