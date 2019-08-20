

/**
 * 博客渲染
 * @param data
 */
function putInArticle(data) {
    var length = data.length;
    $(".qz-session-center").empty();
    var artiles = $(".qz-session-center");
    $.each(data, function (index, obj) {
        var center = $(
            '<div class="qz-center">' +
            '<header class="qz-article">' +
            '<h1 itemprop="name">' +
            '<a class="qz-article-title" href="' + obj['articleUrl'] + '">' + obj['title'] + '</a>' +
            '</h1>' +
            '<div class="qz-tt">' +
            '<span class="am-badge am-badge-success qz-mark">' + obj['selectType'] + '</span>&nbsp;&nbsp;' +
            '<span class=""><span class="am-icon-calendar"></span>&nbsp;&nbsp;' + obj['createTime'] + '</span>&nbsp;&nbsp;' +
            '<span style="font-size: 14px;"><i class="am-icon-user">&nbsp;' + obj['name'] + '</i></span>&nbsp;&nbsp;' +
            '</div>' +
            '</header>' +
            '<div class="article-entry" style="height: 130px;overflow:hidden;">' +
            ' <p>' + obj['articleTabled'] + '</p>' +
            '</div>' +
            '<div class="read-all">' +
            '<a href="' + obj['articleUrl'] + '">阅读全文 <i class="am-icon-angle-double-right"></i></a>' +
            '</div>' +
            '<hr>' +
            '<div class="article-tags">' +

            '</div>' +
            '</div>');
        artiles.append(center);
        var articleTags = $('.article-tags');
        for (var i = 0; i < obj['tagValue'].length; i++) {
            var articleTag = $('<i class="am-icon-tag"><a class="tag aa" href="/tags?tag=' + obj['tagValue'][i] + '">&nbsp;' + obj['tagValue'][i] + '&nbsp;</a></i>');
            articleTags.eq(index).append(articleTag);
        }
    })
}

/**
 * 分页查询文章
 * @param data
 */
function putPageHelper(data, curnum) {
    var count = data.data.records;
    //总页数大于页码总数
    layui.use('laypage', function () {
        var laypage = layui.laypage;
        //执行一个laypage实例
        laypage.render({
            elem: 'page-helper'
            , count:  count//数据总数
            , limit: 5
            , curr: curnum
            , jump: function (obj, first) {
                if (!first) {
                    curnum = obj.curr;
                    ajaxFirst(curnum, 0);
                }
            }
        });
    });
}


/**
 * 分页查询博客文章
 * @param currentPage
 */
function ajaxFirst(currentPage) {
    var jsonStr = {pageSize: 5, pageNum: currentPage};
    $.ajax({
        type: "GET",
        url: "/myArticles",
        // contentType: "application/x-www-form-urlencoded",
        contentType: "application/json",
        dataType: "json",
        data: jsonStr,
        success: function (data) {
            //放入数据
            putInArticle(data.data.rows);
            scrollTo(0, 0);//回到顶部

            // 分页查询
            putPageHelper(data, currentPage);

        },
        error: function () {
        }
    });
}

ajaxFirst(1);


/**
 * 广告上下滚动
 */
function getStyle(obj,name){
    if(obj.currentStyle)
    {
        return obj.currentStyle[name];
    }
    else
    {
        return getComputedStyle(obj,false)[name];
    }
}
function startMove(obj,json,doEnd){
    clearInterval(obj.timer);
    obj.timer=setInterval(function(){
        var oStop=true;
        for(var attr in json)
        {
            var cur=0;
            if(attr=='opacity')
            {
                cur=Math.round(parseFloat(getStyle(obj,attr))*100);
            }
            else
            {
                cur=parseInt(parseInt(getStyle(obj,attr)));
            }
            var speed=(json[attr]-cur)/6;
            speed=speed>0?Math.ceil(speed):Math.floor(speed);
            if(cur!=json[attr])
            {
                oStop=false;
            }
            if(attr=='opacity')
            {
                obj.style.filter='alpha(opacity:'+(speed+cur)+')';
                obj.style.opacity=(speed+cur)/100;
            }
            else
            {
                obj.style[attr]=speed+cur+'px';
            }
        }
        if(oStop)
        {
            clearInterval(obj.timer);
            if(doEnd) doEnd();
        }
    },30);
}
window.onload=function(){
    var oDiv=document.getElementsByClassName('roll')[0];
    var oUl=oDiv.getElementsByTagName('ul')[0];
    var aLi=oUl.getElementsByTagName('li');

    var now=0;
    for(var i=0;i<aLi.length;i++)
    {
        aLi[i].index=i;
    }

    function next(){
        now++;
        if(now==aLi.length)
        {
            now=0;
        }
        startMove(oUl,{top:-26*now})
    }
    //设置广播滚动时间
    var timer=setInterval(next,3000);
    oDiv.onmouseover=function(){
        clearInterval(timer);
    };
    oDiv.onmouseout=function(){
        timer=setInterval(next,3000);
    }
};

