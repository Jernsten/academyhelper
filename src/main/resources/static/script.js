$('.container div').click(function() {

    if ($(this).children("p").hasClass("hidden")) {
        $(this).children("p").slideDown();
        $(this).children("p").removeClass("hidden");
    } else {
        $(this).children("p").slideUp();
        $(this).children("p").addClass("hidden");
    }

});

