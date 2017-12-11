/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$( document ).ready(function() {
$(function(){
  $(".increment").click(function(){
    var count = parseInt($("~ .count", this).text());
    
    if($(this).hasClass("up")) {
      var count = count + 1;
      
       $("~ .count", this).text(count);
    } else {
      var count = count - 1;
       $("~ .count", this).text(count);     
    }
    
    $(this).parent().addClass("bump");
    
    setTimeout(function(){
      $(this).parent().removeClass("bump");    
    }, 400);
  });
});

});