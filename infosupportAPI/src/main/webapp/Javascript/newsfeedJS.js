$( document ).ready(function() {
        alert("hoi");
    let url="http://localhost:8080/infosupportAPI/services/rest/profiles";

    var postsArr = { posts:[]};

    $.ajax({
      type: 'GET',
      url: url,
      dataType:'json',
      success: function(data){
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
              $("#grid").append( `<div class="mdl-card mdl-cell mdl-cell--12-col mdl-shadow--2dp">
                      <div class="mdl-card__title">
                        <h2 class="mdl-card__title-text">`+ postsArr.posts[i].title +`</h2>
                      </div>
                          <div class="mdl-card__supporting-text">
                            <p>`+postsArr.posts[i].content+`</p>
                          </div>
                           
                          <div class="mdl-card__actions mdl-card--border">
                            <h6>Written by: `+postsArr.posts[i].username+`</h6>
                             <button type="submit" value="addLike" id="addLike"class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored" style="float: right;">
                                <b>Like!</b>
                            </button>
                            <input class="mdl-textfield__input" type="text" id="addCommentTF" placeholder="Write a comment" style="float-left">
                            <button type="submit" value="addComment" id="addComment" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored" style="float: right;">
                                <b>Place Comment</b>
                            </button>
                          </div>
                    </div>`);
            };
        },
        error: function(){
          alert('error loading messages');
        }
    });

    $('#addPost').on('click', function(){

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

	function formToJSON() {
    return JSON.stringify({
        "id": 7,
		"title": $('#newPostTitle').val(),
		"content": $('#newPost').val(),
                "username": 'Jordybo'
        });
}



});
