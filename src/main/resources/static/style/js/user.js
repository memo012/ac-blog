var username = "";

$(function () {
    $(".list-item").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
        var flag = $(this).attr('class').substring(10, 18);
        $("#persoSet,#securSet,#guestSet,#commeSet,#commmSet,#likesSet,#guesuSet").css("display", "none");
        $("#" + flag).css("display", "block");
    });
});

// 用户名称
username = $(".username").text();

var phoneNum = ""; // 手机号

$(function () {
    $.ajax({
        type: "GET",
        url: "/getUserMess",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: {
            username: username
        },
        success: function (data) {
            //放入数据
            phoneNum = data.data['phone'];
            putInMessage(data.data);
        },
        error: function () {
        }
    })
})

var userName = $("#user-name");
var userQQ = $("#user-QQ");
var userEmail = $("#user-email");
var userIntro = $("#user-intro");
var noticeBox = $(".notice");

/**
 * 个人信息渲染
 * @param data
 */
function putInMessage(data) {
    userName.attr("value", data['realname']);
    $("#user-phone").attr("value", data['phone']);
    userQQ.attr("value", data['qq']);
    userEmail.attr("value", data['email']);
    userIntro.val(data['intro']);
}

/**
 * 保存修改
 */
var username1 = "";
var userqq = "";
var useremail = "";
var userintro = "";
$(".savebtn").click(function () {
    username1 = userName.val().trim();
    userqq = userQQ.val().trim();
    useremail = userEmail.val().trim();
    userintro = userIntro.val();
    var qqnum = /^[1-9][0-9]{4,14}$/;
    var emailnum = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    if (username1.length > 4) {
        $(".notice-name-len").show();
        close();
        return;
    }
    if (userqq.length != 0) {
        if (userqq.search(/^[1-9]\d{4,11}$/) == -1) {
            $(".notice-qq").show();
            close();
            return;
        }
    }
    if (useremail.length != 0) {
        if (!emailnum.test(useremail)) {
            $(".notice-email").show();
            close();
            return;
        }
    }
    updMessage();
});

/**
 * 修改信息
 */
function updMessage() {

    var data = {
        realname: username1,
        qq: userqq,
        email: useremail,
        intro: userintro,
        username: username
    };
    $.ajax({
        type: "POST",
        url: "insUserMess",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(data),
        success: function (data) {
            //放入数据
            if (data.status == 200) {
                $(".notice-box-suc").show();
                putInMessage(data.data);
            } else if (data.status == 500) {
                $(".notice-box1").show();
            }
        },
        error: function () {
        }
    })
    close();
}

function close() {
    // 定时关闭错误提示框
    var closeNoticeBox = setTimeout(function () {
        noticeBox.hide();
    }, 3000);
}

/**
 * 我的评论
 */
$(".commeSet").click(function () {


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
            } else {
                MyComment();
            }
        },
        error: function () {
        }
    });
});

/**
 * 我的评论
 * @constructor
 */
function MyComment() {
    $.ajax({
        type: "GET",
        url: "/getUserReport",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: {
            username: username
        },
        success: function (data) {
            //放入数据
            if (data.status == 200) {
                if(data.data.length > 0){
                    putNotReadRepMess(data.data);
                }else{
                    putInNotComment();
                }
            }
        },
        error: function () {
        }
    })
}

/**
 * 我的评论(博客)
 */
$(".commmSet").click(function () {


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
            } else {
                MyCommentBlog();
            }
        },
        error: function () {
        }
    });
});

/**
 * 我的评论（博客）
 * @constructor
 */
function MyCommentBlog() {
    $.ajax({
        type: "GET",
        url: "/getBlogUserReport",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data:null,
        success: function (data) {
            //放入数据
            if (data.status == 200) {
                if(data.data.length > 0){
                    putNotReadBlogMess(data.data);
                }else{
                    putInBlogNotComment();
                }
            }
        },
        error: function () {
        }
    })
}

/**
 * 我的点赞
 */
$(".likesSet").click(function () {

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
            } else {
                MyLikes();
            }
        },
        error: function () {
        }
    });

});

/**
 * 我的点赞
 * @constructor
 */
function MyLikes() {
    $.ajax({
        type: "GET",
        url: "/getUserLikes",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: {
            username: username
        },
        success: function (data) {
            //放入数据
            if (data.status == 200) {
                if (data.data.length > 0) {
                    putNotReadLikes(data.data);
                } else {
                    putInNotLikes();
                }
            }
        },
        error: function () {
        }
    })
}


/**
 * 我的留言(管理员)
 */
$(".guestSet").click(function () {
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
            } else {
                MyGuest();
            }
        },
        error: function () {
        }
    });

});

/**
 * 我的留言（管理员）
 * @constructor
 */
function MyGuest() {
    $.ajax({
        type: "GET",
        url: "/getNotAllGuest",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            //放入数据
            if (data.status == 200) {
                if (data.data.length > 0) {
                    putNotReadGuest(data.data);
                } else {
                    putInNotGuest();
                }
            }
        },
        error: function () {
        }
    })
}

/**
 * 我的留言（用户）
 * @constructor
 */
function MyGuestUser() {
    $.ajax({
        type: "GET",
        url: "/getUserGuest",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data:{
            username: username
        },
        success: function (data) {
            //放入数据
            if (data.status == 200) {
                if (data.data.length > 0) {
                    putNotReadUser(data.data);
                } else {
                    putInNotGuestUser();
                }
            }
        },
        error: function () {
        }
    })
}


/**
 * 我的留言(用户)
 */
$(".guesuSet").click(function () {
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
            } else {
                MyGuestUser();
            }
        },
        error: function () {
        }
    });

});



/**
 * 填充不存在的评论
 */
function putInNotComment() {
    $("#commeSet > .comment").empty();
    var center = $(
        '<div class="am-g">' +
        '<div class="comment-com am-kai">' +
        '暂无评论，抢个沙发吧' +
        '</div>' +
        '</div>'
    );
    $(".comment").append(center);
}

/**
 * 填充不存在的评论
 */
function putInBlogNotComment() {
    $("#commmSet > .comment").empty();
    var center = $(
        '<div class="am-g">' +
        '<div class="comment-com am-kai">' +
        '暂无博客评论' +
        '</div>' +
        '</div>'
    );
    $(".comment").append(center);
}

/**
 * 填充不存在的点赞
 */
function putInNotLikes() {
    $("#likesSet > .comment").empty();
    var center = $(
        '<div class="am-g">' +
        '<div class="comment-com am-kai">' +
        '暂无点赞信息' +
        '</div>' +
        '</div>'
    );
    $(".comment").append(center);
}

/**
 * 填充不存在的留言(管理员)
 */
function putInNotGuest() {
    $("#guestSet > .comment").empty();
    var center = $(
        '<div class="am-g">' +
        '<div class="comment-com am-kai">' +
        '暂无留言信息' +
        '</div>' +
        '</div>'
    );
    $(".comment").append(center);
}

/**
 * 填充不存在的留言(用户)
 */
function putInNotGuestUser() {
    $("#guesuSet > .comment").empty();
    var center = $(
        '<div class="am-g">' +
        '<div class="comment-com am-kai">' +
        '暂无留言信息' +
        '</div>' +
        '</div>'
    );
    $(".comment").append(center);
}

/**
 * 我的评论渲染
 */
function putNotReadRepMess(data) {
    $("#commeSet > .comment").empty();
    var ReadReport = $("#commeSet > .comment");
    var length = data.length;
    $.each(data, function (index, obj) {
        var he = $(
            '<div class="am-u-sm-12 am-u-md-12">' +
            '</div>'
        );
        var notRead = $(
            '<div class="am-cf" >' +
            '<span>未读消息：</span>' +
            '<a class="fr data-me">清空所有消息</a>' +
            '<span class="line fr"></span>' +
            '<a class="fr data-me bankRead">全部标记为已读</a>' +
            '</div>'
        );
        var list = $(
            '<div class="data-list">' +
            '</div>'
        );
        var ul = $(
            '<ul>' +
            '</ul>'
        );
        var li = $(
            '<li>' +
            '</li>'
        );
        var dataCircle = $('<i class="data-cricle"></i>');
        var pubMess = $(
            '<span class="repId" style="display: none;">'+obj['rid']+'</span>' +
            '<span class="msg-type">评论</span>' +
            '<span class="msg-title">' +
            '<a target="_blank">&nbsp;&nbsp;' + obj['repName'] + '&nbsp;</a>' +
            '评论了你的评论' +
            '</span>' +
            '<p class="msg-text">' +
            '<span data-v-0947769e="" class="bb-span-wrap FirstbankRead">' +
            '<a href="/article/' + obj['blogId'] + '">' + obj['title'] + '</a>' +
            '</span>' +
            '<em class="fr" style="color: #cccccc;">' + obj['rcreateTime'] + '</em>' +
            '</p>'
        );

        if (index == 0) {
            he.append(notRead);
        }
        he.append(list);
        list.append(ul);
        ul.append(li);
        if (obj['risRead'] == 1) {
            li.append(dataCircle);
        }
        li.append(pubMess);
        ReadReport.append(he);
    })

    /**
     * 全部标记已读
     */
    $(".bankRead").click(function () {
        $.ajax({
            type: "GET",
            url: "/clearComNotRead",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                username: username
            },
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    putNotReadRepMess(data.data);
                } else {
                }

            },
            error: function () {
            }
        })
    });

    /**
     * 部分评论已读
     */
    $(".FirstbankRead").click(function () {
        var $this = $(this);
        var Id = $this.parent().parent().parent().find(".repId").text();
        $.ajax({
            type: "GET",
            url: "/clearOneNotComm",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                id: Id
            },
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    // putNotReadUser(data.data);
                } else {
                }

            },
            error: function () {
            }
        })
    });


}

/**
 * 我的博客评论渲染
 */
function putNotReadBlogMess(data) {
    $("#commmSet > .comment").empty();
    var ReadReport = $("#commmSet > .comment");
    var length = data.length;
    $.each(data, function (index, obj) {
        var he = $(
            '<div class="am-u-sm-12 am-u-md-12">' +
            '</div>'
        );
        var notRead = $(
            '<div class="am-cf" >' +
            '<span>未读消息：</span>' +
            '<a class="fr data-me">清空所有消息</a>' +
            '<span class="line fr"></span>' +
            '<a class="fr data-me bankRead">全部标记为已读</a>' +
            '</div>'
        );
        var list = $(
            '<div class="data-list">' +
            '</div>'
        );
        var ul = $(
            '<ul>' +
            '</ul>'
        );
        var li = $(
            '<li>' +
            '</li>'
        );
        var dataCircle = $('<i class="data-cricle"></i>');
        var pubMess = $(
            '<span class="repId" style="display: none;">'+obj['id']+'</span>' +
            '<span class="msg-type">博客</span>' +
            '<span class="msg-title">' +
            '<a target="_blank">&nbsp;&nbsp;' + obj['authorName'] + '&nbsp;</a>' +
            '评论了你的博客' +
            '</span>' +
            '<p class="msg-text">' +
            '<span data-v-0947769e="" class="bb-span-wrap FirstbankRead">' +
            '<a href="/article/' + obj['blogId'] + '">' + obj['message'] + '</a>' +
            '</span>' +
            '<em class="fr" style="color: #cccccc;">' + obj['createTime'] + '</em>' +
            '</p>'
        );

        if (index == 0) {
            he.append(notRead);
        }
        he.append(list);
        list.append(ul);
        ul.append(li);
        if (obj['isRead'] == 1) {
            li.append(dataCircle);
        }
        li.append(pubMess);
        ReadReport.append(he);
    })

    /**
     * 部分评论已读
     */

    $(".FirstbankRead").click(function () {
        var $this = $(this);
        var Id = $this.parent().parent().parent().find(".repId").text();
        $.ajax({
            type: "GET",
            url: "/clearOneBlogNotComm",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                id: Id
            },
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    putNotReadBlogMess(data.data);
                } else {
                }

            },
            error: function () {
            }
        })
    });


}

/**
 * 我的点赞渲染
 */
function putNotReadLikes(data) {

    $("#likesSet > .comment").empty();
    var ReadReport = $("#likesSet > .comment");
    var length = data.length;
    $.each(data, function (index, obj) {
        var he = $(
            '<div class="am-u-sm-12 am-u-md-12">' +
            '</div>'
        );
        var notRead = $(
            '<div class="am-cf" >' +
            '<span>未读消息：</span>' +
            '<a class="fr data-me">清空所有消息</a>' +
            '<span class="line fr"></span>' +
            '<a class="fr data-me bankRead">全部标记为已读</a>' +
            '</div>'
        );
        var list = $(
            '<div class="data-list">' +
            '</div>'
        );
        var ul = $(
            '<ul>' +
            '</ul>'
        );
        var li = $(
            '<li>' +
            '</li>'
        );
        var dataCircle = $('<i class="data-cricle"></i>');
        var pubMess = $(
            '<span class="repId" style="display: none;">'+obj['id']+'</span>' +
            '<span class="msg-type">点赞</span>' +
            '<span class="msg-title">' +
            '<a target="_blank">&nbsp;&nbsp;' + obj['likeName'] + '&nbsp;</a>' +
            '点赞了你的评论' +
            '</span>' +
            '<p class="msg-text">' +
            '<span data-v-0947769e="" class="bb-span-wrap FirstbankRead">' +
            '<a href="/article/' + obj['blogId'] + '">' + obj['title'] + '</a>' +
            '</span>' +
            '<em class="fr" style="color: #cccccc;">' + obj['likeTime'] + '</em>' +
            '</p>'
        );

        if (index == 0) {
            he.append(notRead);
        }
        he.append(list);
        list.append(ul);
        ul.append(li);
        if (obj['isRead'] == 1) {
            li.append(dataCircle);
        }
        li.append(pubMess);
        ReadReport.append(he);
    })

    /**
     * 全部标记已读
     */
    $(".bankRead").click(function () {
        $.ajax({
            type: "GET",
            url: "/clearLikeNotRead",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                username: username
            },
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    putNotReadLikes(data.data);
                } else {
                }

            },
            error: function () {
            }
        })
    });

    /**
     * 部分评论已读
     */

    $(".FirstbankRead").click(function () {
        var $this = $(this);
        var Id = $this.parent().parent().parent().find(".repId").text();
        $.ajax({
            type: "GET",
            url: "/clearOneBlogNotLikes",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                id: Id
            },
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    putNotReadLikes(data.data);
                } else {
                }

            },
            error: function () {
            }
        })
    });

}

/**
 * 我的留言渲染(G管理员)
 */
function putNotReadGuest(data) {
    $("#guestSet > .comment").empty();
    var ReadReport = $("#guestSet > .comment");
    var length = data.length;
    $.each(data, function (index, obj) {
        var he = $(
            '<div class="am-u-sm-12 am-u-md-12">' +
            '</div>'
        );
        var notRead = $(
            '<div class="am-cf" >' +
            '<span>未读消息：</span>' +
            '<a class="fr data-me">清空所有消息</a>' +
            '<span class="line fr"></span>' +
            '<a class="fr data-me bankRead">全部标记为已读</a>' +
            '</div>'
        );
        var list = $(
            '<div class="data-list">' +
            '</div>'
        );
        var ul = $(
            '<ul>' +
            '</ul>'
        );
        var li = $(
            '<li>' +
            '</li>'
        );
        var dataCircle = $('<i class="data-cricle"></i>');
        var pubMess = $(
            '<span class="repId" style="display: none;">'+obj['id']+'</span>'+
            '<span class="msg-type">留言</span>' +
            '<span class="msg-title">' +
            '<a target="_blank">&nbsp;&nbsp;' + obj['authorName'] + '&nbsp;</a>' +
            '给你留了言' +
            '</span>' +
            '<p class="msg-text">' +
            '<span data-v-0947769e="" class="bb-span-wrap FirstbankRead">' +
            '<a href="/guest">' + obj['message'] + '</a>' +
            '</span>' +
            '<em class="fr" style="color: #cccccc;">' + obj['createTime'] + '</em>' +
            '</p>'
        );

        if (index == 0) {
            he.append(notRead);
        }
        he.append(list);
        list.append(ul);
        ul.append(li);
        if (obj['isRead'] == 1) {
            li.append(dataCircle);
        }
        li.append(pubMess);
        ReadReport.append(he);
    })

    /**
     * 部分留言已读
     */
    $(".FirstbankRead").click(function () {
        var $this = $(this);
        var Id = $this.parent().parent().parent().find(".repId").text();
        $.ajax({
            type: "GET",
            url: "/clearFirstNotGuestMana",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                id: Id
            },
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    putNotReadUser(data.data);
                } else {
                }

            },
            error: function () {
            }
        })
    });
}

/**
 * 我的留言渲染(G普通用户)
 */
function putNotReadUser(data) {
    $("#guesuSet > .comment").empty();
    var ReadReport = $("#guesuSet > .comment");
    var length = data.length;
    $.each(data, function (index, obj) {
        var he = $(
            '<div class="am-u-sm-12 am-u-md-12">' +
            '</div>'
        );
        var notRead = $(
            '<div class="am-cf" >' +
            '<span>未读消息：</span>' +
            '<a class="fr data-me">清空所有消息</a>' +
            '<span class="line fr"></span>' +
            '<a class="fr data-me bankRead">全部标记为已读</a>' +
            '</div>'
        );
        var list = $(
            '<div class="data-list">' +
            '</div>'
        );
        var ul = $(
            '<ul>' +
            '</ul>'
        );
        var li = $(
            '<li>' +
            '</li>'
        );
        var dataCircle = $('<i class="data-cricle"></i>');
        var pubMess = $(
            '<span class="repId" style="display: none;">'+obj['rid']+'</span>' +
            '<span class="msg-type">留言</span>' +
            '<span class="msg-title">' +
            '<a target="_blank">&nbsp;&nbsp;' + obj['repName'] + '&nbsp;</a>' +
            '评论了你的留言' +
            '</span>' +
            '<p class="msg-text">' +
            '<span data-v-0947769e="" class="bb-span-wrap FirstbankRead">' +
            '<a href="/guest">' + obj['repMess'] + '</a>' +
            '</span>' +
            '<em class="fr" style="color: #cccccc;">' + obj['rcreateTime'] + '</em>' +
            '</p>'
        );

        if (index == 0) {
            he.append(notRead);
        }
        he.append(list);
        list.append(ul);
        ul.append(li);
        if (obj['risRead'] == 1) {
            li.append(dataCircle);
        }
        li.append(pubMess);
        ReadReport.append(he);
    })


    /**
    * 全部标记已读
    */
    $(".bankRead").click(function () {
        $.ajax({
            type: "GET",
            url: "/clearNotGuest",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                username: username
            },
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    putNotReadUser(data.data);
                } else {
                }

            },
            error: function () {
            }
        })
    });

    /**
     * 部分留言已读
     */
    $(".FirstbankRead").click(function () {
        var $this = $(this);
        var Id = $this.parent().parent().parent().find(".repId").text();
        $.ajax({
            type: "GET",
            url: "/clearFirstNotGuestUser",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                id: Id
            },
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    putNotReadUser(data.data);
                } else {
                }

            },
            error: function () {
            }
        })
    });
}


/**
 * 登录
 */
function toLogin() {
    window.location.href = "/login";
}


/**
 *
 * 安全配置
 */
$(".securSet").click(function () {
    $("#phone").attr("value", phoneNum);
});