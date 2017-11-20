 $('#addLike').on('click', function(){
     let url = 'http://localhost:8080/infosupportAPI/services/rest/profiles/1/posts/1/upvote';
     
       $.ajax({
        type: 'POST',
        url: url,
		contentType: "application/json",
		dataType: "json",
        data: {"username":"Jordybo"},
        success: function(data, textStatus, jqXHR){
          alert(data);
        },
        error: function(request, status, error){
          alert(request.responseText);
        }
      });
    });
