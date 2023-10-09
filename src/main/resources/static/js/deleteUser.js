    $(document).ready(function(){
    $(".link-delete").on("click", function(e){
    e.preventDefault();
    link = $(this);
    userId = link.attr("userId");
    $("#yesButton").attr("href", link.attr("href"));
    $("#confirmText").text("Are you sure you want to delete this user ID: " + userId + "?");
    $("#confirmModal").modal("show");
    });
  });

