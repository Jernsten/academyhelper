$('.container div').click(function() {

    if ($(this).children("p").hasClass("hidden")) {
        $(this).children("p").slideDown();
        $(this).children("p").removeClass("hidden");
        $(this).children("span.arrow").text("ᐃ");
    } else {
        $(this).children("p").slideUp();
        $(this).children("p").addClass("hidden");
        $(this).children("span.arrow").text("ᐁ");
    }

});