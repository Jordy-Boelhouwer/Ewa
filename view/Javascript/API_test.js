function init(){
    var posts;
    let url="http://localhost:8080/infosupportAPI/services/rest/profiles";
    let promise = fetch(url);

    promise.then(function(resp){
        return resp.json();
    }).then(function(json){
        posts=json;
        display();
    });

    promise.catch(function(err){console.log("jammer")});


    function display(){
      // var profileNameContainer = document.getElementById('profileNameContainer');
      // var profileName = '<h2 class="mdl-card__title-text">PROFILE_NAME</h2>';
      var mdlGrid = document.getElementById('mdl-grid');

      for (var i = 0; i < posts.length; i++) {
        var first_name = posts[i].first_name;
        var last_name = posts[i].last_name;
         $("#grid").append( `<div class="mdl-card mdl-cell mdl-cell--12-col mdl-shadow--2dp">
             <figure class="mdl-card__media">
               <img class="img" src="images/sky_img.jpg" alt="" width="100%" height="100%"/>
             </figure>
             <div class="mdl-card__title">
               <h2 class="mdl-card__title-text">`+ first_name + " " + last_name +`</h2>
             </div>
                 <div class="mdl-card__supporting-text">
                   <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam accusamus, consectetur.</p>
                 </div>
           </div>`);
        // var setProfileName = profileName.replace("PROFILE_NAME" ,posts[i].first_name + " " + posts[i].last_name);
        // profileNameContainer.innerHTML = setProfileName;
      }

    }
}
