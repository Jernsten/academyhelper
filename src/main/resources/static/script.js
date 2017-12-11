$('main#home > div').click(function () {
    $(this).children("h2").slideToggle();
    $(this).children('.info').slideToggle();
    $(this).children('.text').slideToggle();

});

$("window").ready(function () {
    $("#countdown").fadeToggle(3500, "swing", function() {
        $("#countdown").fadeToggle(3500, "swing");
    });

    //$(".text").slideToggle();
});


$("div#loginbutton > button").click(function () {
    $('div#login').fadeToggle(300);
});

$('main#faq > div').click(function () {
    $(this).next().slideToggle();
});

$('main#faq > div > h3').click(function () {
    $(this).next().slideToggle();
});
