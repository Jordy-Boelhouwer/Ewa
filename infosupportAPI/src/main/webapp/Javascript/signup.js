/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$( document ).ready(function() {
    $('#signup').on('click', function(e){
        signup(); 
    });
});

    $('#signup').on('click', function(e){
        signup(); 
    });
function signup(){
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
};

function formToJSON() {
    return JSON.stringify({                 
		"first_name": $('#firstname').val(),
		"last_name": $('#lastname').val(),
                "username": $('#username').val(),
                "password": $('#password').val()
        });
    };



