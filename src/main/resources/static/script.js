$('main#home > div#news').click(function () {
    $(this).children().slideToggle();
});

$('main#home > div#food > h2').click(function () {
    $(this).slideToggle();
    $(this).next().slideToggle();
});



$('main#home > div#sick > h2').click(function(){
    $(this).slideToggle();
    $(this).next().slideToggle();
    $(this).next().next().slideToggle();
})

$("document").ready(function () {
    $("#countdown").fadeToggle(3500, "swing", function() {
        $("#countdown").fadeToggle(3500, "swing");
    });

    $("main#home div.text").slideToggle();
});


$("div#loginbutton > button").click(function () {
    $('div#startlogga>img');
});

$('main#faq > div').click(function () {
    $(this).next().slideToggle();
});

$('main#faq > div > h3').click(function () {
    $(this).next().slideToggle();
});

$('main#home > form#makenews > h2').click(function(){
    $(this).slideToggle();
    $(this).next().slideToggle();
});

$('main#home > box > h2').click(function () {
    $(this).slideToggle();
    $(this).next().slideToggle();
    $(this).next().next().slideToggle();
});
