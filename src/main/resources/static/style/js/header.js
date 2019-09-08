$(function () {
    $("#wx_picture").hide();
    $("#qq_picture").hide();
})
$(".wxchat").mouseenter(function () {
    $("#wx_picture").show();
});
$(".wxchat").mouseleave(function () {
    $("#wx_picture").hide();
});
$(".qq").mouseenter(function () {
    $("#qq_picture").show();
});
$(".qq").mouseleave(function () {
    $("#qq_picture").hide();
});

/**
 * 访客量
 */
var pageName = window.location.pathname + window.location.search;
console.log(pageName.substring(1));
$(function () {
    $.ajax({
        type: "GET",
        url: "/visitCount",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data:{
          pageName: pageName.substring(1)
        },
        success: function (data) {
            $(".visitCount").html(''+data.data);
        }
    })
});










var name = ""; // 用户名

    $(".logou").click(function () {
    $.ajax({
        type: "GET",
        url: "/logout",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            if(data.status == 200){
                window.location.href="/";
            }
        }
    })
});

var $word = "";
$(".es").keyup(function (event) {
    if (event.which == "13") {
        $word = $(".es").val().trim();
        if ($word != undefined && $word.length != 0 && $word != "") {
            window.location.href="/es/"+$word;
        }
    }
});


function isNotPermission() {
    $.ajax({
        type: "GET",
        url: "/isNotPermission",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            if(data.status == 503){
                alert("无此权限");
            }else{
                toEditor();
            }
        },
        error: function () {
        }
    });
}

/**
 * 写博客
 */
$(".writeBlog").click(function () {
    $.ajax({
        type: "GET",
        url: "/isLogin",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        async: false,
        data: {},
        success: function (data) {
            if (data.status == 500) {
                toLogin();
            }else{
                isNotPermission();
            }
        },
        error: function () {
        }
    });
});

/**
 * 登录
 */
function toEditor() {
    window.location.href = "/editor";
}

/**
 * 登录
 */
function toLogin() {
    window.location.href = "/login";
}

/**
 * 消息即时通信
 */
$(".personalSpace").click(function () {
    $.ajax({
        type: "GET",
        url: "/isLogin",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        async: false,
        data: {},
        success: function (data) {
            if (data.status == 500) {
                toLogin();
            }else{
                if(name == "强子"){
                    messageNotReadMana();
                }else{
                    messageNotReadUser();
                }

            }
        },
        error: function () {
        }
    });
});


/**
 * 反馈（管理员）
 */
function messageNotReadMana() {
    var name = $(".name").html();
    $.ajax({
        type: "GET",
        url: "/messageNotReadMana",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        async: false,
        data: {
            username: name
        },
        success: function (data) {
            if(data.data != 0){
                $(".feedbackClick").html('反馈&nbsp;&nbsp;'+'<span id="mess" class="am-badge am-radius am-badge-danger">'+data.data+'</span>');
            }
        },
        error: function () {
        }
    });
}

/**
 * 反馈
 */
$(".feedbackClick").click(function () {
    toUser();
});

/**
 * 消息（用户）
 */
function messageNotReadUser() {
    name = $(".name").html();
    $.ajax({
        type: "GET",
        url: "/messageNotReadUser",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        async: false,
        data: {
            username: name
        },
        success: function (data) {
            if(data.data != 0){
                $(".feedbackClick").html('消息&nbsp;&nbsp;'+'<span id="mess" class="am-badge am-radius am-badge-danger">'+data.data+'</span>');
            }
        },
        error: function () {
        }
    });
}

/**
 * 登录
 */
function toUser() {
    window.location.href = "/user";
}


/**
 * Web网站信息管理
 */
// 文章总数
$(function () {
    $.ajax({
        type: "GET",
        url: "/myArticlesCount",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            //放入数据
            $(".articleCount").html(''+data.data);
        },
        error: function () {
        }
    });
})

// 标签总数
$(function () {
    $.ajax({
        type: "GET",
        url: "/myLabelsCount",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            //放入数据
            $(".labelCount").html(''+data.data);
        },
        error: function () {
        }
    });
})


// 留言总数
$(function () {
    $.ajax({
        type: "GET",
        url: "/myGuestCount",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            //放入数据
            $(".guestCount").html(''+data.data);
        },
        error: function () {
        }
    });
})


// 评论总数
$(function () {
    $.ajax({
        type: "GET",
        url: "/myReportsCount",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            //放入数据
            $(".reportCount").html(''+data.data);
        },
        error: function () {
        }
    });
})

/**
 * 网站运行时间
 * @type {string}
 */
//网站开始时间
var siteBeginRunningTime = '2019-08-7 10:00:00';

//网站运行时间
//beginTime为建站时间的时间戳
function siteRunningTime(time) {
    var theTime;
    var strTime = "";
    if (time >= 86400){
        theTime = parseInt(time/86400);
        strTime += theTime + "天";
        time -= theTime*86400;
    }
    if (time >= 3600){
        theTime = parseInt(time/3600);
        strTime += theTime + "时";
        time -= theTime*3600;
    }
    if (time >= 60){
        theTime = parseInt(time/60);
        strTime += theTime + "分";
        time -= theTime*60;
    }
    strTime += time + "秒";

    $('.siteRunningTime').html(strTime);
}

var nowDate = new Date().getTime();
//网站开始运行日期
var oldDate = new Date(siteBeginRunningTime.replace(/-/g,'/'));
var time = oldDate.getTime();
var theTime = parseInt((nowDate-time)/1000);
setInterval(function () {
    siteRunningTime(theTime);
    theTime++;
},1000);





//网站最后更新时间（版本更新需更改）
var siteLastUpdateTime = '2019年09月2日12点';
$(".siteUpdateTime").html(''+siteLastUpdateTime);





