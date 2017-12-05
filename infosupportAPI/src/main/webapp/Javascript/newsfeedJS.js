$( document ).ready(function() {
        $('#addPost').on('click', function (e) {

<<<<<<< HEAD
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/infosupportAPI/services/rest/profiles/1/posts",
            contentType: "application/json",
            data: JSON.stringify({
                "id": 16,
                "title": $('#newPostTitle').val(),
                "content": $('#newPost').val(),
                "username": 'Jordybo'
            }),
            dataType: "text",
            success: function (data, textStatus, jqXHR) {
                       console.log("The ajax request succeeded!");
                        console.log("The result is: ");  
                        getPosts();
            },
            error: function () {
                ("Posting ");
            }
        });
    });
    
getPosts(); 
});


function getPosts(){
    $('#posts').html("");
    let url="http://localhost:8080/infosupportAPI/services/rest/profiles";
=======


getPosts(); 
    });
$(function getPosts(){
    let url="http://localhost:8080/infosupportAPI/services/rest/profiles";
    var postsArr = { posts:[]};
>>>>>>> cef5c65fbaf1e7ff2e515b8d27077aac177519f1

    $.ajax({
      type: 'GET',
      url: url,
      dataType:'json',
      success: function(data){
<<<<<<< HEAD
        append(data);
=======
          
          profile = data;
           $.each(data, function(i, profile){
                    for (var i = 0; i < profile.posts.length; i++) {
                        postsArr.posts.push(profile.posts[i]); 
                    }
                postsArr.posts.sort(function(a, b){
                    return b.id - a.id;
                });   
            });   
            
            for (var i = 0; i < postsArr.posts.length; i++) {
                var commentArray = { comments:[]};
                 var comment = "";
                 $.each(postsArr.posts[i].comments, function(j){
                  commentArray.comments.push( postsArr.posts[i].comments[j].content); 
                  //console.log(commentArray.comments[j].content);
                  comment += '<p>'+postsArr.posts[i].comments[j].content+'</p>';
                });
                
                                
                $("#postRow").append(`<!-- Post Content Column -->
        <div class="col-lg-8 pull-right" >

          <!-- Title -->
          <h1 class="mt-4">`+postsArr.posts[i].title+`</h1>

          <!-- Author -->
          <p class="lead">
            by
            <a href="#">`+postsArr.posts[i].username+`</a>
          </p>

          <hr>

          <!-- Date/Time -->
          <p></p>

          <hr>

          <!-- Preview Image -->
          <img class="img-fluid rounded" src="images/sample_img.jpg" height="200px;" alt="">

          <hr>

          <!-- Post Content -->
          <p class="lead">`+postsArr.posts[i].content+`</p>
          <hr>

          <!-- Comments Form -->
          <div class="card my-4">
            <h5 class="card-header bg-dark text-white">Leave a Comment:</h5>
            <div class="card-body">
              <form>
                <div class="form-group">
                  <textarea class="form-control" rows="3"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
              </form>
            </div>
          </div>

          <!-- Single Comment -->
          <div  class="media mb-4">
            <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
            <div id="comments" class="media-body">
              <h5 class="mt-0">Commenter Name</h5>

            </div>
          </div>

          <!-- Comment with nested comments -->
          <div class="media mb-4">
            <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
            <div class="media-body">
              <h5 class="mt-0">Commenter Name</h5>
              Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.

              <div class="media mt-4">
                <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
                <div class="media-body">
                  <h5 class="mt-0">Commenter Name</h5>
                  
                </div>
              </div>

              <div class="media mt-4">
                <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
                <div class="media-body">
                  <h5 class="mt-0">Commenter Name</h5>
                  Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
                </div>
              </div>

            </div>
          </div>

        </div>
        <div class="col-md-4"></div>`);
               document.getElementById("comments").innerHTML+= comment;
            };
>>>>>>> cef5c65fbaf1e7ff2e515b8d27077aac177519f1
        },
        error: function(){
          alert('error loading messages');
        }
    });
<<<<<<< HEAD
    };



=======
    });

    $('#addPost').on('click', function(e){
        
      $.ajax({
        type: 'POST',
        url: "http://localhost:8080/infosupportAPI/services/rest/profiles/1/posts",
		contentType: "application/json",
		dataType: "json",
        data: formToJSON(),
        success: function(data, textStatus, jqXHR){
          console.log(formToJson());
          alert('succes');
        },
        error: function(request, status, error){
          alert(request.responseText);

        }
      });
    });
    
    
    
>>>>>>> cef5c65fbaf1e7ff2e515b8d27077aac177519f1
    $('#addLike').on('click', function(){
      $.ajax({
        type: 'POST',
        url: "http://localhost:8080/infosupportAPI/services/rest/profiles/1/posts/1/upvote",
		contentType: "application/json",
		dataType: "json",
        data: 1,
        success: function(data, textStatus, jqXHR){
          console.log("success");
        },
        error: function(request, status, error){
          alert(request.responseText);
        }
      });
    });
    
    $('#addFile').on('click', function(){
        var file = $('input[name="uploadfile"').get(0).files[0];
 
  var formData = new FormData();
  formData.append('uploadfile', file);
 
  $.ajax({
      url: "http://localhost:8080/infosupportAPI/services/rest/profiles/1/posts/file",
      type: 'POST',
      xhr: function() {  // Custom XMLHttpRequest
        var myXhr = $.ajaxSettings.xhr();
        return myXhr;
      },
      // beforeSend: beforeSendHandler,
      success: function(data) {
        alert('successfully uploaded file with '+data+' lines');
      },
      // Form data
      data: formData,
      //Options to tell jQuery not to process data or worry about content-type.
      cache: false,
      contentType: false,
      processData: false
    });
    });

<<<<<<< HEAD

function append(data) {
    var postsArr = { posts:[]};
    profile = data;
    $.each(data, function (i, profile) {
        for (var i = 0; i < profile.posts.length; i++) {
            postsArr.posts.push(profile.posts[i]);
=======
	function formToJSON() {
    return JSON.stringify({
        "id": 11,
		"title": $('#newPostTitle').val(),
		"content": $('#newPost').val(),
                "username": 'Jordybo'
        });
>>>>>>> cef5c65fbaf1e7ff2e515b8d27077aac177519f1
        }
        postsArr.posts.sort(function (a, b) {
            return b.id - a.id;
        });
    });

    for (var i = 0; i < postsArr.posts.length; i++) {
        var commentArray = {comments: []};
        var comment = "";
        $.each(postsArr.posts[i].comments, function (j) {
            commentArray.comments.push(postsArr.posts[i].comments[j].content);
            //console.log(commentArray.comments[j].content);
            comment += '<p>' + postsArr.posts[i].comments[j].content + '</p>';
        });


        $("#posts").append(`<!-- Post Content Column -->
        <div>

          <!-- Title -->
          <h1 class="mt-4">` + postsArr.posts[i].title + `</h1>

          <!-- Author -->
          <p class="lead">
            by
            <a href="#">` + postsArr.posts[i].username + `</a>
          </p>

          <hr>

          <!-- Date/Time -->
          <p></p>

          <hr>

          <!-- Preview Image -->
          <img class="img-fluid rounded" src="images/sample_img.jpg" height="200px;" alt="">

          <hr>

          <!-- Post Content -->
          <p class="lead">` + postsArr.posts[i].content + `</p>
          <hr>

          <!-- Comments Form -->
          <div class="card my-4">
            <h5 class="card-header bg-dark text-white">Leave a Comment:</h5>
            <div class="card-body">
              <form>
                <div class="form-group">
                  <textarea class="form-control" rows="3"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
              </form>
            </div>
          </div>

          <!-- Single Comment -->
          <div  class="media mb-4">
            <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
            <div id="comments" class="media-body">
              <h5 class="mt-0">Commenter Name</h5>

            </div>
          </div>

          <!-- Comment with nested comments -->
          <div class="media mb-4">
            <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
            <div class="media-body">
              <h5 class="mt-0">Commenter Name</h5>
              Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.

              <div class="media mt-4">
                <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
                <div class="media-body">
                  <h5 class="mt-0">Commenter Name</h5>
                  
                </div>
              </div>

              <div class="media mt-4">
                <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
                <div class="media-body">
                  <h5 class="mt-0">Commenter Name</h5>
                  Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
                </div>
              </div>

            </div>
          </div>

        </div>
        <div class="col-md-4 mt-4"></div>`);
        document.getElementById("comments").innerHTML += comment;
    };
};

<<<<<<< HEAD
=======

>>>>>>> cef5c65fbaf1e7ff2e515b8d27077aac177519f1
