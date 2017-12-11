

function addComment(id, content) {
    

    url = "http://localhost:8080/infosupportAPI/services/rest/profiles/1/posts/"+id+"/comments";
    $.ajax({
        type: 'POST',
        url: url,
        contentType: "application/json",
        async: false,
        data: JSON.stringify({
            "content": content
        }),
        dataType: "text",
        success: function (data, textStatus, jqXHR) {
            
             getPosts();
             
         
        },
        error: function (request, status, error) {
            console.log(request.responseText);
        }
    });
};