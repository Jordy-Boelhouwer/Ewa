$( document ).ready(function() {
getCurrentUser();
getPosts();
$('#addPost').click(submitForm);
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
          console.log(request.responseText + ' door getposts');
        }
    });
};

//$('#addPost').on('click', function (e) {
//        $.ajax({
//            type: 'POST',
//            url: "/infosupportAPI/services/rest/profiles/" + sessionStorage.id + "/posts",
//            contentType: "application/json",
//            data: JSON.stringify({
//                "title": $('#newPostTitle').val(),
//                "content": $('#newPost').val(),
//            }),
//            dataType: "text",
//            success: function () {
//                      getPosts();
//                      sendNotification();
//            },
//            error: function (request) {
//                alert(request.responseText);
//            }
//        });
//});

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
    var name = "";
    var commenternName = "";
    var postsArr = {posts:[]};
    profile = data;
    $.each(data, function (i, profile) {
        name = profile.name;
        
        for (var i = 0; i < profile.posts.length; i++) {
            postsArr.posts.push(profile.posts[i]);
        }
        postsArr.posts.sort(function (a, b) {
            return b.id - a.id;
        });
    });



    for (var i = 0; i < postsArr.posts.length; i++) {
        console.log(postsArr.posts[i].imagePath);
        $("#posts").append(`<!-- Post Content Column -->
        <div>
          <!-- Title -->
          <h1 class="mt-4">` + postsArr.posts[i].title + `</h1>
          <!-- Author -->
          <p class="lead">
            by
            <a href="#">` + name + `</a>
          </p>
        
          <hr>

          <!-- Date/Time -->
          <p></p>

          <hr>

          <!-- Preview Image -->
        
          <img src="`+ postsArr.posts[i].imagePath +`" height="50%" width="50%">

          <hr>

          <!-- Post Content -->
          <p class="lead">` + postsArr.posts[i].content + `</p>
          <hr>
   <!--       <div class="mb-4" id="like_div" style="min-height: 50px;">
            <p class="float-right">0x geliked</p>
            <input class="float-right" type="image" src="images/like_button.png" width="50px" height="50px" border="0" alt="Submit" />
        <span></span>
        </div>
 Comments Form -->
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
        </div>
        <div class="col-md-4 mt-4"></div>`);
        
     
        $.each(postsArr.posts[i].comments, function (j) {

            commentID = "#comments"+i
            $(commentID).append(`<div class="media mb-4">
               <img class="d-flex mr-3 rounded-circle" src="`+sessionStorage.smallImage+`" alt="">
               <div id="comments" class="media-body">
                 <h5 class="mt-0">`+sessionStorage.name+`</h5>
                 <p>`+ postsArr.posts[i].comments[j].content +`</p>
               </div>
             </div>`

            );
         });
 
    };
    
    return;
};

function addProfile(){
    console.log("aan het opslaan");
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();

    if(dd<10) {
        dd = '0'+dd;
    } 

    if(mm<10) {
        mm = '0'+mm;
    } 

    today = mm + '/' + dd + '/' + yyyy;
    $.ajax({
            type: 'POST',
            url: "/infosupportAPI/services/rest/profiles",
            contentType: "application/json",
            data: JSON.stringify({
                "id": sessionStorage.id,
                "name": sessionStorage.name,
                "date_joined": today,
                "access_token": sessionStorage.access_token,
                "small_image": sessionStorage.smallImage,
                "big_image": sessionStorage.bigImage
            }),
            success: function () {
                console.log("opgeslagen!");
            },
            error: function (request) {
                alert(request.responseText);
            }
        });
}

function checkIfUserExists(){
    console.log("begin met checken");
    console.log(sessionStorage.id);
    console.log(sessionStorage.name);
    console.log(sessionStorage.access_token);
    let url="/infosupportAPI/services/rest/profiles";
    $.ajax({
      type: 'GET',
      url: url,
      dataType:'json',
      success: function(data){
          if(data && data.length){
            for (i = 0; i < data.length; i++) { 
                if(data[i].access_token === sessionStorage.access_token){
                    console.log("gevonden!");
                } else {
                    console.log("niet gevonden");
                    addProfile();
                }
            }
          } else {
              console.log("geen profielen opgeslagen");
              addProfile();
          }
        },
        error: function(request){
          console.log(request.responseText);
        }
    });
}

function getCurrentUser() {
    let url="https://slack.com/api/users.identity?token=" + sessionStorage.access_token;
    $.ajax({
      type: 'GET',
      url: url,
      dataType:'json',
      success: function(data){
        console.log(data.user.name);
        sessionStorage.name = data.user.name;
        console.log(sessionStorage.name);
        sessionStorage.id = data.user.id;
        sessionStorage.smallImage = data.user.image_48;
        sessionStorage.bigImage = data.user.image_192;
        var test = {data};
        console.log(test);
        checkIfUserExists();
        },
        error: function(request){
          alert(request.responseText + 'door requestToken');
          alert('error loading messages');
        }
    });
}

function sendNotification() {
    let url="https://hooks.slack.com/services/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";
    $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify({
                "text": sessionStorage.name +" has uploaded a new article! Check it <http://localhost:8080/infosupportAPI/login.html|here>"
            }),
            success: function () {
                console.log("slack heeft het gehoord!");
            },
            error: function (request) {
               console.log("error");
            }
        });
}

function submitForm() {
    
  var file = $('#image')[0].files[0];
  
  var formData = new FormData();
  formData.append('title', $('#newPostTitle').val());
  formData.append('content', $('#newPost').val());
  formData.append('image', file);
  $.ajax({
      url: "/infosupportAPI/services/rest/profiles/" + sessionStorage.id + "/posts",
      type: 'POST',
      xhr: function() {  // Custom XMLHttpRequest
        var myXhr = $.ajaxSettings.xhr();
        return myXhr;
      },
      // beforeSend: beforeSendHandler,
      success: function(data) {
        getPosts();
        sendNotification();
      },
      error: function(request){
          console.log(request.responseText + 'door getposts');
        },
      // Form data
      data: formData,
      //Options to tell jQuery not to process data or worry about content-type.
      cache: false,
      contentType: false,
      processData: false
    });
}



