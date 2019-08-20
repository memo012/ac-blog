var articleId = ""; // 博客id
var newReports = ""; //  评论内容
var newComId = ""; // 评论id
var newComName = ""; // 被评论者的姓名
var noticeBox = $(".notice-box");

/**
 * 渲染文章详情
 * @param data
 */
function putInArticleDetail(data) {
    $(".qz-article-top").html('');
    var center = $(
        '<div class="qz-article-top">' +
        '<div class="article-top">' + data.title + '</div>' +
        '<div class="qz-tt article-info">' +
        '<span class="am-badge am-badge-success qz-mark">&nbsp;' + data.selectType + '</span>&nbsp;&nbsp;' +
        '<span class=""><span class="am-icon-calendar"></span>&nbsp;&nbsp;' + data.createTime + '</span>&nbsp;&nbsp;' +
        '<span style="font-size: 14px;"><i class="am-icon-user">&nbsp;' + data.name + '</i></span>&nbsp;&nbsp;' +
        '<span style="font-size: 14px; color: #610024;"><i class="am-icon-eye">&nbsp;&nbsp;' + data.look + '</i></span>' +
        '</div>' +
        '</div>'
    );
    $(".qz-article-top").append(center);
    $("#mdText").text(data.text);
    var wordsView;
    wordsView = editormd.markdownToHTML("wordsView", {
        htmlDecode: "true", // you can filter tags decode
        emoji: true,
        taskList: true,
        tex: true,
        flowChart: true,
        sequenceDiagram: true
    });

    //选中所有需放大的图片加上data-src属性
    $("#wordsView img").each(function(index){
        if(!$(this).hasClass("emoji")){
            var a=$(this).attr('src');
            $(this).attr("data-src",a);

            $(this).addClass("enlargePicture");
        }
    });
    //放大图片框架
    lightGallery(document.getElementById('wordsView'));
}


/**
 * 从响应头中获取文章id
 */
$.ajax({
    type: 'HEAD', // 获取头信息，type=HEAD即可
    url: window.location.href,
    async: false,
    success: function (data, status, xhr) {
        articleId = xhr.getResponseHeader("articleId");
    }
});
$.ajax({
    type: "GET",
    url: "/getArticleDetail",
    // contentType: "application/x-www-form-urlencoded",
    contentType: "application/json",
    dataType: "json",
    data: {
        articleId: articleId
    },
    success: function (data) {
        //放入数据
        if(data.status == 200){
            $("#article-like-span").html(data.data.likes);
            putInArticleDetail(data.data);
        }else if(data.status == 500){
            var cent = ('<div class="qz-article-top"><div class="article-top am-kai">客官，博客可能被博主一不小心删除了</div></div>');
            $(".qz-art").html(''+cent);
        }

    },
    error: function () {
    }
});

/**
 * 点赞
 */
$(function () {
    $("#article-like").click(function () {
        $.ajax({
            type: "GET",
            url: "/getArticleLike",
            // contentType: "application/x-www-form-urlencoded",
            contentType: "application/json",
            dataType: "json",
            data: {
                articleId: articleId
            },
            success: function (data) {
                //放入数据
                $("#article-like-span").html(data.data);
                $(".article-btn").css({
                    "background-color": "#EA6F5A",
                    "color": "white"
                });
            },
            error: function () {
            }
        });
    });

});


/**
 *  发表评论
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
                        message: message1,
                        blogId: articleId
                    };
                    $.ajax({
                        type: "POST",
                        url: "/insComment",
                        // contentType: "application/x-www-form-urlencoded",
                        contentType: "application/json",
                        dataType: "json",
                        data: JSON.stringify(data),
                        success: function (data) {
                            $this.parent().parent().find($("#desc")).val("");
                            putInComment(data.data[0]);
                        },
                        error: function () {
                        }
                    });

                    // 定时关闭错误提示框
                    var closeNoticeBox = setTimeout(function () {
                        noticeBox.hide();
                    }, 3000);

                }

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
    $(".comment").empty();
    var comment = $(".comment");
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
        var reLen = obj['reportComments'].length;// 回复的数量
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
            '<textarea class="replyWordTextarea textareas" placeholder="写下你的评论..."></textarea>' +
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
            var newMessage = obj['reportComments'];
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
                    '<a class="respondent">@' + obj['comName'] + '</a>&nbsp;&nbsp;' + obj['repMess'] +
                    '</div>' +
                    '<div class="visitorReplyTime">' +
                    '<span class="visitorReplyTimeTime">' + obj['rcreateTime'] + '</span>&nbsp;&nbsp;' +
                    '<a class="reply">' +
                    '<i class="am-icon-comment-o">&nbsp;&nbsp;回复</i>' +
                    '</a>' +
                    '<a class="commentId" style="display: none">' + obj['commentId'] + '</a>' +
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
     * 添加评论
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
                commentId: newComId,
                comName: newComName,
                blogId: articleId
            };
            $.ajax({
                type: "POST",
                url: "/InsRepComment",
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
                        putInComment(data.data[0]);
                    }
                },
                error: function () {
                }
            });
        }


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
                        blogId: articleId,
                        commentId: newComId
                    };
                    $.ajax({
                        type: "POST",
                        url: "/updLikes",
                        contentType: "application/json",
                        dataType: "json",
                        async: false,
                        data: JSON.stringify(data),
                        success: function (data) {
                            putInComment(data.data[0]);
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
 * 评论查询
 */
$.ajax({
    type: "GET",
    url: "/getComment",
    // contentType: "application/x-www-form-urlencoded",
    contentType: "application/json",
    dataType: "json",
    data: {
        blogId: articleId
    },
    success: function (data) {
        if (data.data[0] != "" && data.data.length != 0) {
            putInComment(data.data[0]);
        } else {
            putInNotComment();
        }
    },
    error: function () {
    }
});

/**
 * 填充不存在的评论
 */
function putInNotComment() {
    $(".comment").empty();
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
 * 登录
 */
function toLogin() {
    $.get("/toLogin", function (data,status,xhr) {
        window.location.replace("/login");
    })
}

