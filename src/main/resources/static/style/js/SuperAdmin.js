$(function () {
    $("#userSet,#blogSet,#fridSet").css("display", "none");
    $(".list-item").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
        var flag = $(this).attr('class').substring(10, 17);
        $("#websSet,#userSet,#blogSet,#fridSet").css("display", "none");
        $("#" + flag).css("display", "block");
    });
});

var noticeBox = $(".notice");
/**
 * 保存友链
 */
$(".savebtn").click(function () {
    var friendName = $("#friend-name").val().trim();
    var friendUrl = $("#friend-url").val().trim();
    if (friendName == "" || friendUrl == "") {
        $(".notice-friend").show();
        setTimeout(function () {
            noticeBox.hide();
        }, 3000);
        return;
    }
    var str = {
        "friendName": friendName,
        "friendUrl": friendUrl
    }
    $.ajax({
        type: "POST",
        url: "insFriendUrl",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(str),
        success: function (data) {
            //放入数据
            if (data.status == 200) {
                $(".notice-box-suc").show();
            } else if (data.status == 500) {
                $(".notice-fail").show();
            }
        },
        error: function () {
        }
    });
    // 定时关闭错误提示框
    var closeNoticeBox = setTimeout(function () {
        noticeBox.hide();
    }, 3000);

});


/**
 * 用户管理
 */
$(".userSet").click(function () {
    ajaxFirst(1);
});

/**
 * 博客信息
 */
$(".blogSet").click(function () {
    ajaxFirstBlog(1);
});


/**
 * 分页查询博客信息
 * @param data
 */
function putPageHelperBlog(data, curnum) {
    var count = data.data.records;
    //总页数大于页码总数
    layui.use('laypage', function () {
        var laypage = layui.laypage;
        //执行一个laypage实例
        laypage.render({
            elem: 'page-helper-two'
            , count: count//数据总数
            , limit: 10
            , curr: curnum
            , jump: function (obj, first) {
                if (!first) {
                    curnum = obj.curr;
                    ajaxFirstBlog(curnum);
                }
            }
        });
    });
}


/**
 * 分页查询用户
 * @param data
 */
function putPageHelperUser(data, curnum) {
    var count = data.data.records;
    //总页数大于页码总数
    layui.use('laypage', function () {
        var laypage = layui.laypage;
        //执行一个laypage实例
        laypage.render({
            elem: 'page-helper'
            , count: count//数据总数
            , limit: 10
            , curr: curnum
            , jump: function (obj, first) {
                if (!first) {
                    curnum = obj.curr;
                    ajaxFirst(curnum);
                }
            }
        });
    });
}

/**
 * 用户信息
 * @param currentPage
 */
function ajaxFirst(currentPage) {
    var jsonStr = {pageSize: 10, pageNum: currentPage};
    $.ajax({
        type: "GET",
        url: "/getUsers",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: jsonStr,
        success: function (data) {
            //放入数据
            if (data.status == 200) {
                putInUsers(data.data);
                scrollTo(0, 0);//回到顶部

                // 分页查询
                putPageHelperUser(data, currentPage);
            } else {
                alert("无权限");
                toLogin();
            }
        },
        error: function () {
        }
    })
}


/**
 * 博客信息
 * @param currentPage
 */
function ajaxFirstBlog(currentPage) {
    var jsonStr = {pageSize: 10, pageNum: currentPage};
    $.ajax({
        type: "GET",
        url: "/getBlogs",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: jsonStr,
        success: function (data) {
            //放入数据
            if (data.status == 200) {
                putInBlogs(data.data);
                scrollTo(0, 0);//回到顶部

                // 分页查询
                putPageHelperBlog(data, currentPage);
            } else {
                alert("无权限");
                toLogin();
            }
        },
        error: function () {
        }
    })
}

/**
 * 用户信息渲染
 */
function putInUsers(data) {
    $(".usersMessage").empty();
    var userMessage = $(".usersMessage");
    $.each(data.rows, function (index, obj) {
        var center = (
            '<tr>' +
            '<td>' + (index + 1) + '</td>' +
            '<td class="username">' + obj['username'] + '</td>' +
            '<td>' + obj['phone'] + '</td>' +
            '<td class="am-hide-sm-only">' + obj['realname'] + '</td>' +
            '<td class="am-hide-sm-only">' + obj['qq'] + '</td>' +
            '<td class="am-hide-sm-only">' + obj['email'] + '</td>' +
            '<td class="am-hide-sm-only">' + obj['lastTime'] + '</td>' +
            '<td>' +
            '<div class="am-btn-toolbar">' +
            '<div class="am-btn-group am-btn-group-xs">' +
            '<button class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only qz-delete"><span class="am-icon-trash-o"></span> 删除</button>' +
            '</div>' +
            '</div>' +
            '</td>' +
            '</tr>'
        );
        userMessage.append(center);
    });

    /**
     * 用户删除管理
     */
    $(".qz-delete").click(function () {
        var $this = $(this);
        var username = $this.parent().parent().parent().parent().find($(".username")).text();
        $.ajax({
            type: "GET",
            url: "/delUsers",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                username: username
            },
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    ajaxFirst(1);
                } else {
                    alert("无权限");
                    toLogin();
                }
            },
            error: function () {
            }
        })

    });

}

/**
 * 博客渲染
 * @param data
 */
function putInBlogs(data) {
    $(".usersMessage").empty();
    var userMessage = $(".usersMessage");
    $.each(data.rows, function (index, obj) {
        var center = (
            '<tr>' +
            '<td class="id" style="display: none;">' + obj['id'] + '</td>' +
            '<td>' + (index + 1) + '</td>' +
            '<td>' + obj['title'] + '</td>' +
            '<td>' + obj['createTime'] + '</td>' +
            '<td>' +
            '<div class="am-btn-toolbar">' +
            '<div class="am-btn-group am-btn-group-xs">' +
            '<button class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only qz-edit"><span class="am-icon-save"></span> 编辑</button>' +
            '<button class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only qz-delete"><span class="am-icon-trash-o"></span> 删除</button>' +
            '</div>' +
            '</div>' +
            '</td>' +
            '</tr>'
        );
        userMessage.append(center);
    });


    /**
     * 用户删除管理
     */
    $(".qz-delete").click(function () {
        var $this = $(this);
        var id = $this.parent().parent().parent().parent().find($(".id")).text();
        $.ajax({
            type: "GET",
            url: "/delBlog",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                id: id
            },
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    ajaxFirstBlog(1);
                } else {
                    alert("无权限");
                    toLogin();
                }
            },
            error: function () {
            }
        })

    });


    /**
     * 用户编辑管理
     */
    $(".qz-edit").click(function () {
        var $this = $(this);
        var id = $this.parent().parent().parent().parent().find($(".id")).text();
        window.location.replace("/editor?id=" + id);
    });
}


/**
 * 获取友链信息
 */
$(".fridSet").click(function () {
    ajaxFirstFriend(1);
})

function ajaxFirstFriend(currentPage) {
    var jsonStr = {pageSize: 10, pageNum: currentPage};
    $.ajax({
        type: "GET",
        url: "/getAllFriendsUrl",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: jsonStr,
        success: function (data) {
            //放入数据
            if (data.status == 200) {
                putInFriend(data.data);
                scrollTo(0, 0);//回到顶部

                // 分页查询
                putPageHelperFriend(data, currentPage);
            } else {
                alert("无权限");
                toLogin();
            }
        },
        error: function () {
        }
    })
}

function putInFriend(data) {
    $(".usersMessage").html('');
    $.each(data.rows, function (index, obj) {
        var center = (
            '<tr>' +
            '<td class="id" style="display: none;">' + obj['id'] + '</td>' +
            '<td>' + (index + 1) + '</td>' +
            '<td>' + obj['friendName'] + '</td>' +
            '<td>' + obj['friendUrl'] + '</td>' +
            '<td>' + obj['createTime'] + '</td>' +
            '<td>' +
            '<div class="am-btn-toolbar">' +
            '<div class="am-btn-group am-btn-group-xs">' +
            '<button class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only qz-delete"><span class="am-icon-trash-o"></span> 删除</button>' +
            '</div>' +
            '</div>' +
            '</td>'
        );
        $(".usersMessage").append(center);
    })

    /**
     * 友链删除管理
     */
    $(".qz-delete").click(function () {
        var $this = $(this);
        var id = $this.parent().parent().parent().parent().find($(".id")).text();
        $.ajax({
            type: "GET",
            url: "/delFriendUrl",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                id: id
            },
            success: function (data) {
                //放入数据
                if (data.status == 200) {
                    ajaxFirstFriend(1);
                } else {
                    alert("无权限");
                    toLogin();
                }
            },
            error: function () {
            }
        })

    });

}


/**
 * 分页查询友链信息
 * @param data
 */
function putPageHelperFriend(data, curnum) {
    var count = data.data.records;
    //总页数大于页码总数
    layui.use('laypage', function () {
        var laypage = layui.laypage;
        //执行一个laypage实例
        laypage.render({
            elem: 'page-helper-friend'
            , count: count//数据总数
            , limit: 10
            , curr: curnum
            , jump: function (obj, first) {
                if (!first) {
                    curnum = obj.curr;
                    ajaxFirstFriend(curnum);
                }
            }
        });
    });
}

/**
 * 登录
 */
function toLogin() {
    window.location.href = "/login";
}
