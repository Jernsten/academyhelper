$('main#home > div').click(function () {
    $(this).children("h2").slideToggle();
    $(this).children('.info').slideToggle();
    $(this).children('.text').slideToggle();

});

$("window").ready(function () {
    $("#countdown").fadeOut(7000, "swing");
    $(".text").slideToggle();
});


$("div#loginbutton > button").click(function () {
    $('div#login').fadeToggle(300);
});