/**
 * 标签加载
 */
function putInLabel(data){
    var length = data.length;
    $(".qz-div-div").empty();
    var tagVaues = $(".qz-div-div");
    var center = $(
        '<div class="am-g">' +
        ' <h1 class="blog-title"><i>标签云</i></h1>' +
        '<hr>' +
        '<div class="am-u-sm-12 blog-clear-padding">' +

        '</div>' +
        '</div>');
    tagVaues.append(center);
    $.each(data, function (index, obj) {
        var cent = $('<a href="/tags?tag=' + obj['labelName'] + '" class="blog-tag am-kai">' + obj['labelName'] + '&nbsp;&nbsp;</a>');
        $(".blog-clear-padding").append(cent);
    })
}
$.ajax({
    type: "GET",
    url: "/tag/getTags",
    contentType: "application/json",
    dataType: "json",
    success: function (data) {
        //放入数据
        if(data.status == 200){
            putInLabel(data.data);
        }
    },
    error: function () {
        alert("出错啦...");
    }
})