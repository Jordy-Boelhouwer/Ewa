$( document ).ready(function() {
getPosts(); 
getSlackUser();
});

function getPosts(){
     var scroll = $(window).scrollTop();

    $('#posts').html("");
    let url="/infosupportAPI/services/rest/profiles";

    $.ajax({
      type: 'GET',
      url: url,
      dataType:'json',
      success: function(data){
        append(data);
        $(window).scrollTop(scroll);
        },
        error: function(request){
          alert(request.responseText + 'door getposts');
          alert('error loading messages');
        }
    });
};

$('#addPost').on('click', function (e) {
        $.ajax({
            type: 'POST',
            url: "/infosupportAPI/services/rest/profiles/1/posts",
            contentType: "application/json",
            data: JSON.stringify({
                "title": $('#newPostTitle').val(),
                "content": $('#newPost').val(),
            }),
            dataType: "text",
            success: function (data, textStatus, jqXHR) {
                      getPosts();
            },
            error: function (request) {
                alert(request.responseText);
            }
        });
});

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


function append(data) {
    var postsArr = { posts:[]};
    profile = data;
    $.each(data, function (i, profile) {
        for (var i = 0; i < profile.posts.length; i++) {
            postsArr.posts.push(profile.posts[i]);
        }
        postsArr.posts.sort(function (a, b) {
            return b.id - a.id;
        });
    });



    for (var i = 0; i < postsArr.posts.length; i++) {
        
            
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
                  <textarea id="addComment`+i+`" type="text" class="form-control" rows="3" placeholder="Write here..."></textarea>
                </div>
                <button id="addCommentBTN`+i+`" type="submit" class="btn btn-primary" onClick="addComment(`+postsArr.posts[i].id+`, addComment`+i+`.value)">Submit</button>
              </form>
            </div>
          </div>

          <!-- Single Comment -->
        <div id="comments`+i+`">
          
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
                  Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
                </div>
              </div>

            </div>
          </div>

        </div>
        <div class="col-md-4 mt-4"></div>`);
        
     
        $.each(postsArr.posts[i].comments, function (j) {

            //var commentArray = {comments: []};
            //commentArray.comments.push(postsArr.posts[i].comments[j].content);
            //console.log(commentArray.comments[j].content);
            commentID = "#comments"+i
            $(commentID).append(`<div  class="media mb-4">
               <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
               <div id="comments" class="media-body">
                 <h5 class="mt-0">Commenter Name</h5>
                 <p>`+ postsArr.posts[i].comments[j].content +`</p>
               </div>
             </div>`

            );
         });
 
    };
    
    return;
};

function getSlackUser() {
    let url="https://slack.com/api/users.identity?token=xoxp-288788460883-289005432293-290997151330-4930cfd22d5c074565f5efd33030bb8b";
    $.ajax({
      type: 'GET',
      url: url,
      dataType:'json',
      success: function(data){
        var test = {data};
        console.log(data);
        },
        error: function(request){
          alert(request.responseText + 'door requestToken');
          alert('error loading messages');
        }
    });
}

