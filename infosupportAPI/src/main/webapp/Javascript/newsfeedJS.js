$( document ).ready(function() {
        
    let url="http://localhost:8080/infosupportAPI/services/rest/profiles";

    var postsArr = { posts:[]};
    var namesArr = { names:[]};

    $.ajax({
      type: 'GET',
      url: url,
      dataType:'json',
      success: function(data){
          profile = data;
           $.each(data, function(i, profile){
               namesArr.names.push(profile.first_name);
                    for (var i = 0; i < profile.posts.length; i++) {
                        postsArr.posts.push(profile.posts[i]);
                        
                    }
                postsArr.posts.sort(function(a, b){
                    return b.id - a.id;
                });   
            });   
            
            for (var i = 0; i < postsArr.posts.length; i++) {
              console.log(namesArr.names);
              $("#grid").append( `<div class="mdl-card mdl-cell mdl-cell--12-col mdl-shadow--2dp">
                      <div class="mdl-card__title">
                        <h2 class="mdl-card__title-text">`+ postsArr.posts[i].title +`</h2>
                      </div>
                          <div class="mdl-card__supporting-text">
                            <p>`+postsArr.posts[i].content+`</p>
                          </div>
                           <img src=`+postsArr.posts[i].imagePath+` alt="Smiley face" height="42" width="42"> 
                          <div class="mdl-card__actions mdl-card--border">
                            <h6>Written by: `+ + " " + +`</h6>
                          </div>
                    </div>`);
            };
        },
        error: function(){
          alert('error loading messages');
        }
    });

    $('#addPost').on('click', function(){

      var newtitle = $('#newPostTitle');
      var newpost =  $('#newPost');

      $.ajax({
        type: 'POST',
        url: "http://localhost:8080/infosupportAPI/services/rest/profiles/1/posts",
		contentType: "application/json",
		dataType: "json",
        data: formToJSON(),
        success: function(data, textStatus, jqXHR){
          console.log(formToJson());
        },
        error: function(request, status, error){
          alert(request.responseText);
        }
      });
    });

	function formToJSON() {
    return JSON.stringify({
        "id": 7,
		"title": $('#newPostTitle').val(),
		"content": $('#newPost').val(),
        });
}



});
