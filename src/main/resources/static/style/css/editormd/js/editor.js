var testEditor;
$(function () {
    testEditor = editormd("my-editormd", {
        width: "98%",
        height: 600,
        syncScrolling: "single",
        path: "style/css/editormd/lib/", //依赖lib文件夹路径
        previewTheme: "dark", //代码块使用dark主题
        /**设置主题颜色*/
        // editorTheme: "#c3c3c3",
        // theme: "gray",
        codeFold: true,
        emoji: true,
        tocm: true, // Using [TOCM]
        tex: true, // 开启科学公式TeX语言支持，默认关闭
        flowChart: true, // 开启流程图支持，默认关闭
        sequenceDiagram: true, // 开启时序/序列图支持，默认关闭,
        htmlDecode: true, //不过滤标签
        imageUpload: true,
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL: "/uploadImage",		//上传图片控制器Mapping
        onload: function () {
            // console.log('onload', this);
        },
        saveHTMLToTextarea: true, //注意3：这个配置，方便post提交表单
        toolbarIcons: function () {
            return ["bold", "del", "italic", "quote", "|", "h1", "h2", "h3", "h4", "h5", "h6", "|", "list-ul", "list-ol", "hr", "|", "link", "image", "code", "code-block", "table", "datetime", "html-entities", "emoji", "|", "watch", "preview", "fullscreen", "clear", "search", "|", "help", "info"]
        }
    });
});

var publishBtn = $(".publishBtn");
var qzEditorTitle = $("#qz-editor-title");
var myEditormdHtmlCode = $("#my-editormd-html-code");
var noticeBoxTitle = $(".notice-box-title");
var noticeBoxContent = $(".notice-box-content");
var noticeBox = $('.notice-box');
/**
 * 发布博客
 */
publishBtn.click(function (data) {
    var qzEditorTitleValues = qzEditorTitle.val();
    var myEditormdHtmlCodeValues = myEditormdHtmlCode.val();
    if (qzEditorTitleValues.length == 0) {
        noticeBoxTitle.show();
    } else if (myEditormdHtmlCodeValues.length == 0) {
        noticeBoxContent.show();
    } else {
        $('#my-alert').modal();
    }
    // 定时关闭错误提示框
    var closeNoticeBox = setTimeout(function () {
        noticeBox.hide();
    }, 3000);
});

// 插入标签
var addTagsBtn = $('.addTagsBtn');
$(function () {
    var i = 0;
    var $tag = $(".tag");
    var appendParam = function () {
        var param = $('<div style="display: inline-block;"><p class="tag-name" contenteditable="true"></p>' +
            '<i class="am-icon-times removeTag" style="color: #CCCCCC"></i></div>');
        $tag.append(param);
        $('.tag-name').click(function () {
            $(this).focus();
        });
    };
    addTagsBtn.on("click", function () {
        if (i >= 4) {
            addTagsBtn.attr('disabled', 'disabled');
        }
        var value = $(".tag-name").eq(i - 1).html();
        if (value != "") {
            appendParam();
            i++;
        }
    });

    $tag.on("click", ".removeTag", function () {
        $(this).parent().remove();
        i--;
        if (i <= 4) {
            addTagsBtn.removeAttr("disabled");
        }
    });

})


/*
显示文章原作者
 */
var selectType = $("#select-type");
selectType.blur(function () {
    var slectValue = selectType.val();
    if (slectValue == "原创") {
        $("#originalAuthorHide").hide();
    } else if (slectValue == "转载") {
        $("#originalAuthorHide").show();
    }
});


function publishSuccessPutIn(data) {
    $("#removeDiv").html('');
}


//获得草稿文章
$(function () {
    $.ajax({
        type: "GET",
        url: "/getDraftArticle",
        async: false,
        data: {},
        dataType: "json",
        success: function (data) {
            if(data.status == 201){
                $("#qz-editor-title").val(''+data.data.title);
                $("#content").val(''+data.data.text);
                selectType.val(''+data.data.selectType);
                selectCategories.val(''+data.data.selectCategories);
                selectGrade.val(''+data.data.selectGrade);
                if(data.data.selectType == "转载"){
                    originalAuthor.val(''+data.data.originalAuthor);
                    $("#originalAuthorHide").show();
                }else{
                    $("#originalAuthorHide").hide();
                }

                var tags = data.data.tagValue;
                var $tag = $(".tag");
                for(var i in tags){
                    $tag.append('<div style="display: inline-block;"><p class="tag-name" contenteditable="true">'+tags[i]+'</p>' +
                    '<i class="am-icon-times removeTag" style="color: #CCCCCC"></i></div>');
                }
                var articleId = data.data.id;
                if(articleId != null){
                    $(".surePublishBtn").attr("id", articleId);
                }
            }
        },
        error: function () {
        }
    });
})


/*
发表博客
 */
var selectCategories = $("#select-categories");
var selectGrade = $("#select-grade");
var originalAuthor = $("#originalAuthor");
var message = $(".url");
var surePublishBtn = $(".surePublishBtn");
surePublishBtn.click(function () {
    /**
     * 标签
     * @type {jQuery}
     */
    var length = $(".tag").find(".tag-name").length;
    var tagValues = [];
    for (var i = 0; i < length; i++) {
        tagValues[i] = $(".tag-name").eq(i).html();
    }

    /**
     *  文章类型
     */
    var selectTypes = selectType.val();

    /**
     * 博客分类
     */
    var selectCategorie = selectCategories.val();

    /**
     * 文章等级
     */
    var selectGrades = selectGrade.val();

    /**
     * 原作者
     */
    var originalAuthors = originalAuthor.val();

    /**
     * 文章（0-公开  1-私密）
     */
    var messages = $("input[type='radio']:checked").val();

    /**
     * 标题
     */
    var title = qzEditorTitle.val();


    if (length <= 0 || tagValues[length - 1] == "") {
        $(".notice-box-tags").show();
    } else if (selectType.val() == "choose") {
        $(".notice-box-type").show();
    } else if (selectCategories.val() == "choose") {
        $(".notice-box-categories").show();
    } else if (selectGrade.val() == "choose") {
        $(".notice-box-grade").show();
    } else if (selectType.val() == "转载" && originalAuthor.val() == "") {
        $(".notice-box-originalAuthor").show();
    } else {
        var data = {
            id: $(".surePublishBtn").attr("id"),
            tagValue: tagValues,
            selectType: selectTypes,
            selectCategories: selectCategorie,
            selectGrade: selectGrades,
            originalAuthor: originalAuthors,
            message: messages,
            title: title,
            text: myEditormdHtmlCode.val(),
            articleHtmlContent: testEditor.getHTML()
        };
        $.ajax({
            type: "POST",
            url: "/publishEditor",
            traditional: true,// 传数组
            data: JSON.stringify(data),
            contentType: "application/json",
            success: function (data) {
                if (data.status == 200) {
                    $("#my-alert").modal('close');
                    $("#publish-success").modal();
                } else if (data.status == 500) {
                    $(".notice-box-publish").show();
                    setTimeout(function () {
                        noticeBox.hide();
                    }, 3000);
                } else if (data.status == 502) {
                    alert("重新登录");
                    toLogin();
                }else if(data.status == 201){
                    $("#my-alert").modal('close');
                    $("#publish-upd").modal();
                }
            }
        })
    }

    // 定时关闭错误提示框
    var closeNoticeBox = setTimeout(function () {
        noticeBox.hide();
    }, 3000);

})

/**
 * 发布成功跳转页面
 * @type {*|jQuery|HTMLElement}
 */
var issueSuccess = $(".issue-success");
issueSuccess.click(function () {
    window.location.href = "/";
});


/**
 * 登录
 */
function toLogin() {
    window.location.href = "/login";
}