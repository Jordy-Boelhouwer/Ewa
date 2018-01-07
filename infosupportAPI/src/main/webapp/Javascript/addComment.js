

function addComment(id, content) {
    

    url = "/infosupportAPI/services/rest/profiles/"  + sessionStorage.id + "/posts/"+id+"/comments";
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