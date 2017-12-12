/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$('#signup').on('click', function(e){
        
      $.ajax({
        type: 'POST',
        url: "http://localhost:8080/infosupportAPI/services/rest/profiles",
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
    function formToJSON() {
    return JSON.stringify({
                  
		"firstname": $('#firstname').val(),
		"lastname": $('#lastname').val(),
                "username": $('#username').val(),
                "password": $('#password').val(),
        })};


