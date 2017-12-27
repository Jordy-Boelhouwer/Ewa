/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function requestToken(){
    let url="https://slack.com/api/oauth.access?client_id=288788460883.288345855217&client_secret=2bb324796aabf0d0c0885eeed7ccb828&code=" + getAllUrlParams().code;
    $.ajax({
      type: 'GET',
      url: url,
      dataType:'json',
      success: function(data){
        var test = {data};
        if(test.data.ok) {
            addProfile(test);
        } else {
            window.location.href = "login.html";
        }
        },
        error: function(request){
          alert(request.responseText);
          window.location.href = "login.html";
        }
    });
}

function getAllUrlParams(url) {

  // get query string from url (optional) or window
  var queryString = url ? url.split('?')[1] : window.location.search.slice(1);

  // we'll store the parameters here
  var obj = {};

  // if query string exists
  if (queryString) {

    // stuff after # is not part of query string, so get rid of it
    queryString = queryString.split('#')[0];

    // split our query string into its component parts
    var arr = queryString.split('&');

    for (var i=0; i<arr.length; i++) {
      // separate the keys and the values
      var a = arr[i].split('=');

      // in case params look like: list[]=thing1&list[]=thing2
      var paramNum = undefined;
      var paramName = a[0].replace(/\[\d*\]/, function(v) {
        paramNum = v.slice(1,-1);
        return '';
      });

      // set parameter value (use 'true' if empty)
      var paramValue = typeof(a[1])==='undefined' ? true : a[1];

      // (optional) keep case consistent
      paramName = paramName.toLowerCase();
      paramValue = paramValue.toLowerCase();

      // if parameter name already exists
      if (obj[paramName]) {
        // convert value to array (if still string)
        if (typeof obj[paramName] === 'string') {
          obj[paramName] = [obj[paramName]];
        }
        // if no array index number specified...
        if (typeof paramNum === 'undefined') {
          // put the value on the end of the array
          obj[paramName].push(paramValue);
        }
        // if array index number specified...
        else {
          // put the value at that index number
          obj[paramName][paramNum] = paramValue;
        }
      }
      // if param name doesn't exist yet, set it
      else {
        obj[paramName] = paramValue;
      }
    }
  }

  return obj;
}

function addProfile(profiledata){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();

    if(dd<10) {
        dd = '0'+dd
    } 

    if(mm<10) {
        mm = '0'+mm
    } 

    today = mm + '/' + dd + '/' + yyyy;
    $.ajax({
            type: 'POST',
            url: "/infosupportAPI/services/rest/profiles",
            contentType: "application/json",
            data: JSON.stringify({
                "name": profiledata.data.user.name,
                "date_joined": today,
                "access_token": profiledata.data.access_token
            }),
            success: function () {
                window.location.href = "index.html";
            },
            error: function (request) {
                alert(request.responseText);
            }
        });
}







