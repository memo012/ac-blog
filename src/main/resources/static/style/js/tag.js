var tag = "";

/**
 * 标签归档渲染
 * @param data
 */
function putInTags(data) {
    $(".container-before-icon").html('');
    $(".qz-time").html('');
    var articleTime = $('.qz-time');
    var cent = $(
        '<span class="am-icon-star am-icon-lg"></span>' +
        '目前总计 ' + data.records + ' 篇日志。 (゜-゜)つロ 干杯' +
        '</div>'
    );
    $(".container-before-icon").append(cent);
    $.each(data.rows, function (index, obj) {
        var center;
            if(index == 0){
            center = $(
                '<dl>' +
                '<dt>' + obj['specificTag'] + '</dt>' +
                '<dd class="qz-right qz-dt-div">' +
                '<div class="circ"></div>' +
                ' <div class="time">' + obj['createTime']+ '</div>' +
                '<div class="qz-event">' +
                '<div class="events-header"><a href="/article/' + obj['id'] + '">' + obj['title'] + '</a></div>' +
                '<div class="events-header-span">' +
                '<span class="am-icon-calendar">' +
                '<a href="/time/'+obj['createTime']+'">'+obj['createTime']+'</a>'+
                '</span>' +
                '<span class="am-icon-folder qz-flie">' +
                '<a href="/categories?categorie='+obj['selectCategories']+'">' + obj['selectCategories'] + '</a>' +
                '</span>' +
                '<br>' +
                '<span class="am-icon-tags qz-flie qz-file">' +

                '</span>' +
                '</div>' +
                '</div>' +
                '</dd>' +
                '</dl>'
            );
        }else{
            if(index % 2 == 0){
                center = $(
                    '<dl>' +
                    '<dt>' + obj['specificTag'] + '</dt>' +
                    '<dd class="qz-right qz-dt-div">' +
                    '<div class="circ"></div>' +
                    ' <div class="time">' + obj['createTime']+ '</div>' +
                    '<div class="qz-event">' +
                    '<div class="events-header"><a href="/article/' + obj['id'] + '">' + obj['title'] + '</a></div>' +
                    '<div class="events-header-span">' +
                    '<span class="am-icon-calendar">' +
                    '<a href="/time/'+obj['createTime']+'">'+obj['createTime']+'</a>'+
                    '</span>' +
                    '<span class="am-icon-folder qz-flie">' +
                    '<a href="/categories?categorie='+obj['selectCategories']+'">' + obj['selectCategories'] + '</a>' +
                    '</span>' +
                    '<br>' +
                    '<span class="am-icon-tags qz-flie qz-file">' +

                    '</span>' +
                    '</div>' +
                    '</div>' +
                    '</dd>' +
                    '</dl>'
                );
            }else {
                center = $(
                    '<dl>' +
                    '<dd class="qz-left qz-dt-div">' +
                    '<div class="circ"></div>' +
                    ' <div class="time">' + obj['createTime']+ '</div>' +
                    '<div class="qz-event">' +
                    '<div class="events-header"><a href="/article/' + obj['id'] + '">' + obj['title'] + '</a></div>' +
                    '<div class="events-header-span">' +
                    '<span class="am-icon-calendar">' +
                    '<a href="/time/'+obj['createTime']+'">'+obj['createTime']+'</a>'+
                    '</span>' +
                    '<span class="am-icon-folder qz-flie">' +
                    '<a href="/categories?categorie='+obj['selectCategories']+'">' + obj['selectCategories'] + '</a>' +
                    '</span>' +
                    '<br>' +
                    '<span class="am-icon-tags qz-flie qz-file">' +

                    '</span>' +
                    '</div>' +
                    '</div>' +
                    '</dd>' +
                    '</dl>'
                );
            }
        }

        articleTime.append(center);
        var qzFile = $('.qz-file');
        for (var i = 0; i < obj['tagValue'].length; i++) {
            var tt;
            if (i != obj['tagValue'].length - 1) {
                tt = '<a href="/tags?tag=' + obj['tagValue'][i] + '">' + obj['tagValue'][i] + '</a>,';
            } else {
                tt = '<a href="/tags?tag=' + obj['tagValue'][i] + '">' + obj['tagValue'][i] + '</a>';
            }
            qzFile.eq(index).append(tt);
        }
    })
}

$.ajax({
    type: 'HEAD', // 获取头信息，type=HEAD即可
    url: window.location.href,
    async: false,
    success: function (data, status, xhr) {
        tag = xhr.getResponseHeader("tag");
    }
});


/**
 * 分页查询标签
 * @param data
 */
function putPageHelper(data, curnum) {
    //总页数大于页码总数
    layui.use('laypage', function () {
        var laypage = layui.laypage;
        //执行一个laypage实例
        laypage.render({
            elem: 'page-helper'
            , count: data.data.records //数据总数
            , limit: 5
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
 * 分页标签
 * @param currentPage
 */
function ajaxFirst(currentPage) {
    var jsonStr = {pageSize: 5, pageNum: currentPage, tags: tag};
    $.ajax({
        type: "GET",
        url: "/getTagsDetail",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: jsonStr,
        success: function (data) {
            // //放入数据
            putInTags(data.data);
            scrollTo(0, 0);//回到顶部
            //
            // 分页查询
            putPageHelper(data, currentPage);
        },
        error: function () {
            alert("出错啦...");
        }
    });
}

ajaxFirst(1);