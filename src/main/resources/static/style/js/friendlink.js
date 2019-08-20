var newReports = ""; //  评论内容
var newComId = ""; // 留言id
var newComName = ""; // 被评论者的姓名
var noticeBox = $(".notice-box");






/**
 *  发表留言
 */
var commentBn = $("#commentBn");

commentBn.click(function () {
    var $this = $(this);
    $.ajax({
        type: 'get',
        url: '/isLogin',
        dataType: 'json',
        async: false,
        data: {},
        success: function (data) {
            if (data.status == 500) {
                toLogin();
            } else {
                var message1 = $("#desc").val();
                if (message1.trim() == "") {
                    $(".notice-box-text").show();
                } else {
                    var data = {
                        message: message1
                    };
                    $.ajax({
                        type: "POST",
                        url: "insFriend",
                        // contentType: "application/x-www-form-urlencoded",
                        contentType: "application/json",
                        dataType: "json",
                        data: JSON.stringify(data),
                        success: function (data) {
                            $this.parent().parent().find($("#desc")).val("");
                            putInComment(data.data);
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
        },
        error: function () {
        }
    })
});

/**
 * 填充评论列表
 */
function putInComment(data) {
    $(".visitorComment").empty();
    var comment = $(".visitorComment");
    var length = data.length;
    $.each(data, function (index, obj) {
        var amG = $(
            '<div class="am-g"></div>'
        );
        var visitorCommentImg = $(
            '<div class="visitorCommentImg am-u-sm-2 am-u-lg-1">' +
            '<img src="style/images/default.jpg">' +
            '</div>'
        );
        var cn = $('<div class="am-u-sm-10 am-u-lg-11 cn"></div>');
        var news = $(
            '<div class="visitorInfo">' +
            '<span class="visitorName">' + obj['authorName'] + '</span>&nbsp;&nbsp;' +
            '<span class="visitorFloor">#' + (length--) + '楼</span>' +
            '<span class="visitorPublishDate">' + obj['createTime'] + '</span>' +
            '</div>' +
            ' <div class="visitorSay">' + obj['message'] + '</div>' +
            '<div class="tool-group">' +
            '<a class="like">' +
            '<i class="am-icon-thumbs-o-up">&nbsp;&nbsp;<span>' + obj['likes'] + '</span>人赞</i>&nbsp;&nbsp;' +
            '</a>' +
            '<a class="reply1">' +
            '<i class="am-icon-comment-o">&nbsp;&nbsp;回复</i></a>' +
            '<a class="commentId" style="display:none;">' + obj['id'] + '</a>' +
            '</div>'
        );
        var reLen = obj['reportCommentSet'].length;// 回复的数量
        var subCommentList = $('<div class="sub-comment-list"></div>');
        var subComment = $('<div class="sub-comment"></div>');
        var newReport = $(
            '<div class="more-comment">' +
            '<a class="moreComment"><i class="am-icon-edit"> 添加新评论</i></a>' +
            '</div>'
        );
        var repor = $(
            '<div class="reply-sub-comment-list am-animation-slide-bottom">' +
            '<div class="replyWord">' +
            '<div class="replyWordBtn">' +
            '<textarea class="replyWordTextarea" placeholder="写下你的评论..."></textarea>' +
            '<button type="button" class="sendReplyWordBtn am-btn am-btn-success">发送' +
            '</button>' +
            ' <button type="button" class="quitReplyWordBtn am-btn">取消</button>' +
            '</div>' +
            '</div>' +
            '</div>'
        );
        comment.append(amG);
        amG.append(visitorCommentImg);
        amG.append(cn);
        amG.append('<hr/>');
        cn.append(news);
        cn.append(subComment);
        subComment.append(subCommentList);
        subComment.append(repor);
        if (reLen > 0) {
            var report1 = $('<div class="visitorReplies"></div>');
            subCommentList.append(report1);
            var newMessage = obj['reportCommentSet'];
            newMessage = newMessage.sort(
                function (a, b) {
                    return (a.rid - b.rid)
                }
            );
            $.each(newMessage, function (ind, obj) {
                var cent = $(
                    '<div class="visitorReply">' +
                    '<div class="visitorReplyWords">' +
                    '<a class="answerer">' + obj['repName'] + '</a>' +
                    '：' +
                    '<a class="respondent">@' + obj['friendName'] + '</a>&nbsp;&nbsp;' + obj['repMess'] +
                    '</div>' +
                    '<div class="visitorReplyTime">' +
                    '<span class="visitorReplyTimeTime">' + obj['rcreateTime'] + '</span>&nbsp;&nbsp;' +
                    '<a class="reply">' +
                    '<i class="am-icon-comment-o">&nbsp;&nbsp;回复</i>' +
                    '</a>' +
                    '<a class="commentId" style="display: none">' + obj['friendId'] + '</a>' +
                    '</div>' +
                    '<hr data-am-widget="divider" style="" class="am-divider am-divider-dashed">' +
                    '</div>'
                );
                report1.append(cent);
            })
        }
        subCommentList.append(newReport);
    })

    /**
     * 添加留言
     */
    $(".moreComment").click(function () {
        var $this = $(this);
        $.ajax({
            type: 'get',
            url: '/isLogin',
            dataType: 'json',
            async: false,
            data: {},
            success: function (data) {
                if (data.status == 500) {
                    toLogin();
                } else {
                    $this.parent().parent().parent().find($('.reply-sub-comment-list')).css("display", "block");
                    $this.parent().parent().parent().find($('.reply-sub-comment-list')).find($('.replyWordTextarea')).focus();
                    newComId = $this.parent().parent().parent().parent().find($('.tool-group')).find($('.commentId')).text();
                    newComName = $this.parent().parent().parent().parent().find($('.visitorInfo')).find($('.visitorName')).text();
                }
            },
            error: function () {
            }
        });
    });
    /**
     * 回复
     */
    $(".reply1").click(function () {
        var $this = $(this);
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
                    $this.parent().parent().find($('.sub-comment')).find($('.reply-sub-comment-list')).css("display", "block");
                    $this.parent().parent().find($('.reply-sub-comment-list')).find($('.replyWordTextarea')).focus();
                    newComName = $this.parent().parent().find($('.visitorInfo')).find($('.visitorName')).text();
                    newComId = $this.parent().parent().find($('.tool-group')).find($('.commentId')).text();
                }
            },
            error: function () {
            }
        });
    });

    /**
     * 取消发送
     */
    $(".quitReplyWordBtn").click(function () {
        var $this = $(this);
        $this.parent().parent().parent().css("display", "none");
    });

    /**
     * 发送评论
     */
    $(".sendReplyWordBtn").click(function () {
        var $this = $(this);
        newReports = $this.parent().find($('.replyWordTextarea')).val();

        if (newReports.trim() == "") {
            $(".notice-box-text").show();
        } else {
            var data = {
                repMess: newReports,
                friendId: newComId,
                friendName: newComName
            };
            $.ajax({
                type: "POST",
                url: "/insRepFriend",
                // contentType: "application/x-www-form-urlencoded",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify(data),
                success: function (data) {
                    $this.parent().parent().parent().css("display", "none");
                    if (data.status == 502) {
                        toLogin();
                    } else if (data.status == 500) {
                        $(".notice-box-comment").show();
                    } else if (data.status == 200) {
                        putInComment(data.data);
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

    });

    /**
     * 评论回复
     */
    $(".reply").click(function () {
        var $this = $(this);
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
                    $this.parent().parent().parent().parent().next().css("display", "block");
                    $this.parent().parent().parent().parent().next().find($('.replyWordTextarea')).focus();
                    newComName = $this.parent().parent().find($(".visitorReplyWords")).find($(".answerer")).text();
                    newComId = $this.parent().find($(".commentId")).text();
                }
            },
            error: function () {
            }
        });
    });

    /**
     * 点赞
     */
    $(".like").click(function () {
        var $this = $(this);
        newComId = $this.parent().find($('.commentId')).text();
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
                    var data = {
                        friendId: newComId
                    };
                    $.ajax({
                        type: "POST",
                        url: "/updFriendLikes",
                        contentType: "application/json",
                        dataType: "json",
                        async: false,
                        data: JSON.stringify(data),
                        success: function (data) {
                            if (data.status == 502) {
                                toLogin();
                            } else {
                                putInComment(data.data);
                            }
                        },
                        error: function () {
                        }
                    })
                }
            },
            error: function () {
            }
        });
    });
}


/**
 * 留言查询
 */
$.ajax({
    type: "GET",
    url: "getAllFriend",
    // contentType: "application/x-www-form-urlencoded",
    contentType: "application/json",
    dataType: "json",
    data:{},
    success: function (data) {
        if (data.data.length > 0) {
            putInComment(data.data);
        } else {
            putInNotComment();
        }
    },
    error: function () {
    }
});

/**
 * 友链查询
 */
$.ajax({
    type: "GET",
    url: "getAllFriendlink",
    // contentType: "application/x-www-form-urlencoded",
    contentType: "application/json",
    dataType: "json",
    data:{},
    success: function (data) {
        if (data.data.length > 0) {
            console.log(data.data);
            putInFriend(data.data);
        } else {
        }
    },
    error: function () {
    }
});

/**
 * 友链
 * @param data
 */
function putInFriend(data) {
    $(".qz-friend").html('');
    $.each(data, function (index, obj) {
        var center = $(
            '<li class="am-kai layui-col-xs6 layui-col-sm6 layui-col-md4"><span class="layui-icon layui-icon-tree" style="font-size: 23px;margin-right: 5px"></span><a href="'+obj['friendUrl']+'" target="_blank">'+
            obj['friendName']+'</a></li>'
        );
        $(".qz-friend").append(center);
    })
}



/**
 * 填充不存在的留言
 */
function putInNotComment() {
    $(".visitorComment").empty();
    var center = $(
        '<div class="am-g">' +
        '<div class="comment-com am-kai">' +
        '暂无留言，抢个沙发吧' +
        '</div>' +
        '</div>'
    );
    $(".visitorComment").append(center);
}

/**
 * 登录
 */
function toLogin() {
    $.get("/toLogin", function (data,status,xhr) {
        window.location.replace("/login");
    })
}