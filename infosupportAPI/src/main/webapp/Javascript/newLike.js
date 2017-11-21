 $( document ).ready(function() {
     
 $(document).on("click", "#addLike", function(){
     let url = 'http://localhost:8080/infosupportAPI/services/rest/profiles/1/posts/1/upvote';
     
       $.ajax({
        type: 'POST',
        url: url,
		contentType: "application/json",
		dataType: "json",
        data: "2",
        success: function(data, textStatus, jqXHR){
          console.log("succes");
          alert(data);
        },
        error: function(request, status, error){
            console.log("error");
          alert(request.responseText);
        }
      });
    });
//        function likeFormToJSON() {
//        return JSON.stringify(
//            {"2"}
//            );
//        }
    
 });