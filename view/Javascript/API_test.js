$( document ).ready(function() {
console.log("ads");

    var posts;
    let url="http://localhost:8084/infosupportAPI/services/rest/profiles";

    //let promise = fetch(url);

    // promise.then(function(resp){
    //     return resp.json();
    // }).then(function(json){
    //     posts=json;
    //     display();
    // });
    //
    // promise.catch(function(err){console.log("jammer")});

    $.ajax({
      type: 'GET',
      url: url,
      dataType:'json',
      success: function(data){
          $.each(data, function(i, post){
            for (var i = 0; i < post.posts.length; i++) {
              $("#grid").append( `<div class="mdl-card mdl-cell mdl-cell--12-col mdl-shadow--2dp">
                      <div class="mdl-card__title">
                        <h2 class="mdl-card__title-text">`+ post.posts[i].title +`</h2>
                      </div>
                          <div class="mdl-card__supporting-text">
                            <p>`+post.posts[i].content+`</p>
                          </div>
                          <div class="mdl-card__actions mdl-card--border">
                            <h6>Written by: `+ post.first_name + " " + post.last_name +`</h6>
                          </div>
                    </div>`);
            };
          });
        },
        error: function(){
          alert('error loading messages');
        }
    });

    $('#addPost').on('click', function(){

      var newtitle = $('#newPostTitle');
      var newpost =  $('#newPost');

      var newPost = {
        id: 5,
        "title": newtitle.val(),
        "content": newpost.val()
      };

      $.ajax({
        type: 'POST',
        url: "http://localhost:8084/infosupportAPI/services/rest/profiles/1/posts",
		contentType: "application/json",
		dataType: "json",
        data: formToJSON(),
        success: function(data, textStatus, jqXHR){
          console.log(newPost.content);
        },
        error: function(request, status, error){
          alert(request.responseText);
        }
      });
    });
	
	function formToJSON() {
    return JSON.stringify({
        "id": 6,
		"title": $('#newPostTitle').val(),
		"content": $('#newPost').val()
        });
}

});
