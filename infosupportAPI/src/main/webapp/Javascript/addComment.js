$( document ).ready(function() {


$('#addComment').on('click', function(){

        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/infosupportAPI/services/rest/profiles/2/posts/4/comments",
            contentType: "application/json",
            dataType: "json",
        data: commentFormToJSON(),
        success: function(data, textStatus, jqXHR){
          console.log(commentFormToJSON());
        },
        error: function(request, status, error){
            console.log(request.responseText);
          alert(request.responseText);
        }
        });
    });
    
    function commentFormToJSON() {
        return JSON.stringify({
            "id": 2,
                    "content": $('#addCommentTF').val()
            });
        }
    });