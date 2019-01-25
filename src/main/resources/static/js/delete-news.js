$(document).ready(function () {

    $(document).on("click", '[name=delete]', function () {
        var ok = confirm("Are you sure you want to delete it?");
        if (ok == false) return;
        var workingObject = $(this).parent();
        var newsId = workingObject.find('input').val();
        var newsTitle = workingObject.find('h2').text();
        $.ajax({
            type: "DELETE",
            url: window.location + "/delete-news/" + newsId,
            success: function (resultMsg) {
                workingObject.empty();
                workingObject.html("<b>" + newsTitle + " has been Deleted</b>");
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    });
})