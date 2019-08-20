var registerBtn = $("#register-btn");
var phone = $("#phone");
var yan = $("#yan");
var username = $("#username");
var password = $("#password");
var passwordSure = $("#passwordSure");
var sex = $("input[type='radio']:checked");
var noticeBox = $(".notice-box");

/**
 * 手机号
 * @type {string}
 */

var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;
/**
 * 注册事件
 */
registerBtn.click(function () {
    var phone1 = phone.val().trim();
    var yan1 = yan.val();
    var username1 = username.val();
    var password1 = password.val();
    var passwordSure1 = passwordSure.val();
    if (phone1.length == 0) {
        $(".notice-box-phone").show();
    } else if (phone1.length != 11) {
        $(".notice-box-phone-num").show();
    } else if (!myreg.test(phone1)) {
        $(".notice-box-phone-num").show();
    } else if (checkPhone(phone1)) {
        $(".notice-box-phone-exit").show();
    } else if (yan1.length != 4) {
        $(".notice-box-code").show();
    } else if (!checkCode(yan1)) {
        $(".notice-box-code").show();
    } else if (username1.length == 0) {
        $(".notice-box-user").show();
    } else if (username1.toLowerCase().indexOf("admin") == 0 || username1.toLowerCase().indexOf("user") == 0) {
        $(".notice-box-name").show();
    } else if (username1.length > 16) {
        $(".notice-box-user-length").show();
    } else if (nameLen(username1)) {
        $(".notice-box-user-exit").show();
    } else if (password1.length == 0 || passwordSure1.length == 0) {
        $(".notice-box-password").show();
    } else if (password1.length < 6 || password1.length > 18) {
        $(".notice-box-password-num").show();
    } else if (password1 != passwordSure1) {
        $(".notice-box-password-ps").show();
    } else {
        var jsonStr = {
            username: username1,
            password: password1,
            phone: phone1,
            sex: sex.val()
        };
        $.ajax({
            type: "POST",
            url: "register",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(jsonStr),
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    $("#publish-success").modal();
                } else if (data.status == 500) {
                    $(".notice-box-reg").show();
                    setTimeout(function () {
                        noticeBox.hide();
                    }, 3000);
                }
            },
            error: function () {
                alert("出错啦...");
            }
        });
    }
    // 定时关闭错误提示框
    var closeNoticeBox = setTimeout(function () {
        noticeBox.hide();
    }, 3000);
});

/**
 * 发布成功跳转页面
 * @type {*|jQuery|HTMLElement}
 */
var issueSuccess = $("#issue-success");
issueSuccess.click(function () {
    window.location.href = "login";
});

/**
 * 手机号校验
 * @param data
 */
function checkPhone(data) {
    var str = {phone: data};
    var mes = true;
    $.ajax({
        type: "GET",
        async: false,
        url: "phoneCheck",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: str,
        success: function (data) {
            //放入数据
            if (data.status == 200) {
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
 * 该用户是否存在
 * @param data
 */
function nameLen(data) {
    var str = {username: data};
    var mes = true;
    $.ajax({
        type: "GET",
        async: false,
        url: "usernameCheck",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: str,
        success: function (data) {
            //放入数据
            if (data.status == 200) {
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
 * 点击获取验证码
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
    var phone1 = phone.val().trim();
    if (phone1.length == 0) {
        $(".notice-box-phone").show();
        codeBtn.attr("disabled", false);
    } else if (phone1.length != 11) {
        $(".notice-box-phone-num").show();
        codeBtn.attr("disabled", false);
    } else if (!myreg.test(phone1)) {
        $(".notice-box-phone-num").show();
        codeBtn.attr("disabled", false);
    } else if (checkPhone(phone1)) {
        $(".notice-box-phone-exit").show();
        codeBtn.attr("disabled", false);
    } else {
        $.ajax({
            type: "GET",
            url: "getCode",
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
/**
 * 校验验证码
 * @param data
 * @returns {boolean}
 */
function checkCode(data) {
    var phone1 = phone.val().trim();
    var code = "";
    $.ajax({
        type: "GET",
        async: false,
        url: "getCodeReflush",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: {
            phone: phone1
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

