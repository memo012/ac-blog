var noticeBox = $(".notice-box");
var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;

/**
 * 修改密码（按钮）
 */
$("#register-btn").click(function () {
    updPassword();
});

/**
 * 修改密码（enter）
 */
$("#passwordSure").keyup(function (event) {
    if (event.which == "13") {
        updPassword();
    }
});

/**
 * 密码修改
 */
function updPassword() {
    var phone = $("#phone").val().trim();
    var yan1 = $("#yan").val();
    var password = $("#password").val();
    var SurePwd = $("#passwordSure").val();
    if (phone.length == 0) {
        $(".notice-box-phone").show();
    } else if (phone.length != 11) {
        $(".notice-box-phone-num").show();
    } else if (!myreg.test(phone)) {
        $(".notice-box-phone-num").show();
    }else if(!checkPhone1(phone)){
        $(".notice-box-phone-exit").show();
    } else if (yan1.length != 4) {
        $(".notice-box-yan").show();
    }else if(!checkCode(yan1)){
        $(".notice-box-yan").show();
    }else if (password.length == 0 || SurePwd.length == 0) {
        $(".notice-box-password").show();
    } else if (password.length < 6 || SurePwd.length > 18) {
        $(".notice-box-password-num").show();
    } else if (password != SurePwd) {
        $(".notice-box-password-ps").show();
    } else {
        $.ajax({
            type: "GET",
            url: "findUsersPwd",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                phone: phone,
                password: password
            },
            success: function (data) {
                //放入数据
                if(data.status == 200){
                    $("#publish-success").modal();
                }else {
                    $(".notice-box-reg").show();
                    setTimeout(function () {
                        noticeBox.hide();
                    }, 3000);
                }
            },
            error: function (res) {
                alert("客官，慢点按(⊙o⊙)？");
            }
        });
    }

    // 定时关闭错误提示框
    var closeNoticeBox = setTimeout(function () {
        noticeBox.hide();
    }, 3000);
}


/**
 * 修改成功跳转页面
 * @type {*|jQuery|HTMLElement}
 */
var issueSuccess = $("#issue-success");
issueSuccess.click(function () {
    window.location.href="login";
});

/**
 * 手机号校验
 * @param data
 */
function checkPhone1(data) {
    var str = {phone: data};
    var mes = true;
    $.ajax({
        type: "GET",
        async:false,
        url: "phoneCheck",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: str,
        success: function (data) {
            //放入数据
            if(data.status == 200){
                mes = false;
            }
        },
        error: function (res) {
            alert("客官，慢点按(⊙o⊙)？");
        }
    });
    return mes;
}


/**
 * 校验验证码
 * @param data
 * @returns {boolean}
 */
function checkCode(data) {
    var phone = $("#phone").val().trim();
    var code = "";
    $.ajax({
        type: "GET",
        async: false,
        url: "getCodeReflush",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: {
            phone: phone
        },
        success: function (data) {
            //放入数据
            code = data.data;
        },
        error: function (res) {
            alert("客官，慢点按(⊙o⊙)？");
        }
    });
    if (data == code) {
        return true;
    } else {
        return false;
    }
}


/**
 * 验证码
 * @type {*|jQuery|HTMLElement}
 */
var codeBtn = $("#authCodeBtn");
var my_interval;
my_interval = 60;
var timeLeft = my_interval;
//重新发送计时函数
var timeCount = function () {
    window.setTimeout(function () {
        if (timeLeft > 0) {
            timeLeft -= 1;
            codeBtn.html(timeLeft + "秒重新发送");
            timeCount();
        } else {
            codeBtn.html("重新发送");
            timeLeft = 60;
            codeBtn.attr('disabled', false);
        }
    }, 1000);
};

/**
 * 验证码获取
 */

codeBtn.click(function () {
    codeBtn.attr("disabled", true);
    var phone1 = $("#phone").val().trim();
    if (phone1.length == 0) {
        $(".notice-box-phone").show();
        codeBtn.attr("disabled", false);
    } else if (phone1.length != 11) {
        $(".notice-box-phone-num").show();
        codeBtn.attr("disabled", false);
    } else if (!myreg.test(phone1)) {
        $(".notice-box-phone-num").show();
        codeBtn.attr("disabled", false);
    } else if (!checkPhone1(phone1)) {
        $(".notice-box-phone-exit").show();
        codeBtn.attr("disabled", false);
    } else {
        $.ajax({
            type: "GET",
            url: "getUpdPwdCode",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                phone: phone1
            },
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    alert("手机验证码发送成功！");
                    timeCount();
                } else {
                    alert("手机验证码发送失败，请重新发送！");
                    codeBtn.attr("disabled", false);
                }
            },
            error: function (res) {
                alert("客官，慢点按(⊙o⊙)？");
                codeBtn.attr("disabled", false);
            }
        });
    }

    // 定时关闭错误提示框
    var closeNoticeBox = setTimeout(function () {
        noticeBox.hide();
    }, 3000);

});


