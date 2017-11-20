 $( document ).ready(function() {
     alert("hoi");
  $('#addComment').on('click', function(){
     alert("asdasd");
 
//      $.ajax({
//        type: 'POST',
//        url: 'http://localhost:8080/infosupportAPI/services/rest/profiles/1/posts/1/comments',
//		contentType: "application/json",
//		dataType: "json",
//        data: formToJSON(),
//        success: function(data, textStatus, jqXHR){
//          console.log(formToJson());
//        },
//        error: function(request, status, error){
//          alert(request.responseText);
//        }
//      });
//});
//
//	function formToJSON() {
//    return JSON.stringify({
//        "id": 1,
//	"content": $('#addComment').val()
//        });
});
});