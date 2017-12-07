$('.container div').click(function() {

    if ($(this).children(".text").hasClass("hidden")) {
        $(this).children(".text").slideDown();
        $(this).children(".text").removeClass("hidden");
        $(this).children(".info").slideUp();
    } else {
        $(this).children(".text").slideUp();
        $(this).children(".text").addClass("hidden");
        $(this).children(".info").slideDown();
    }

});