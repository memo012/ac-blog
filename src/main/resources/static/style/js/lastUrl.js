
$.ajax({
    type: 'HEAD', // 获取头信息，type=HEAD即可
    url : window.location.href,
    async:false,
    success:function (data, status, xhr) {
        var lastUrl = xhr.getResponseHeader("lastUrl");
        if(lastUrl != null){
            location.href = lastUrl;
        }
    }
});

// var forward = document.referrer;
// console.log(forward);
// if (forward == "" || forward == undefined || forward == null) {
//     location.href = "/";
// }
// else {
//     location.href = "" + forward + "";
// }