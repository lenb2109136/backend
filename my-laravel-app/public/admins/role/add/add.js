$(".checkbox_wrapper").on("click", function () {
    $(this)
        .closest(".card")
        .find(".checkbox_childrent")
        .prop("checked", $(this).prop("checked"));
});
