var qzLogin = $(".qz-login");
var noticeBox = $(".notice-box");
qzLogin.click(function () {
    log();
});


function log(){
    var phone = $("#username").val().trim();
    var pass = $("#password").val();
    var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if(phone.length == 0 || phone.length != 11 || !myreg.test(phone) || pass.length < 6 || pass.length > 18){
        $(".notice-box-res").show();
    } else if(pass.length < 6 || pass.length > 18){
        $(".notice-box-password-num").show();
    }else{
        var str = {phone: phone, password: pass};
        $.ajax({
            type: "GET",
            url: "loginUser",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: str,
            success: function (data) {
                //放入数据
                if(data.status == 200){
                    window.location.href="/lasturl";
                }else if(data.status == 500){
                    $(".notice-box-res").show();
                    setTimeout(function () {
                        noticeBox.hide();
                    }, 3000);
                }
            },
            error: function () {
            }
        });
    }


    // 定时关闭错误提示框
    var closeNoticeBox = setTimeout(function () {
        noticeBox.hide();
    }, 3000);
}

/**
 * enter 登录
 */
$(".pass").keyup(function (event) {
    if (event.which == "13") {
        log();
    }
});