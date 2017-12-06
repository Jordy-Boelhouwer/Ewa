$( document ).ready(function() {


 $(document).on("click", "#addComment", function(){
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/infosupportAPI/services/rest/profiles/2/posts/4/comments",
            contentType: "application/json",
            dataType: "json",
        data: commentFormToJSON(),
        success: function(data, textStatus, jqXHR){
          console.log($('#addCommentTF').val());
          console.log(commentFormToJSON());
        },
        error: function(request, status, error){
            console.log($('#addCommentTF').val());
            console.log(request.responseText);
          alert(request.responseText);
        }
        });
    });
    
    function commentFormToJSON() {
        var content = $('#addCommentTF').val();
        return JSON.stringify({
            "id": 13,
                    "content": ''+content+''
                    
            });
        }
 });