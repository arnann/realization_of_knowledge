<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ul class="list-group" id="list-group" style="font-size: 15px;">
    <a href="${pageContext.request.contextPath}/question?actionName=showTypeList&type=kaoyan" class="list-group-item"><span
            class=" glyphicon glyphicon-education"
            style="margin-right: 10px;margin-left: 8px"></span>考研</a>
    <a href="${pageContext.request.contextPath}/question?actionName=showTypeList&type=waiyu" class="list-group-item"><span
            class=" glyphicon glyphicon-globe"
            style="margin-right: 10px;margin-left: 8px"></span>外语</a>
    <a href="${pageContext.request.contextPath}/question?actionName=showTypeList&type=jisuanji" class="list-group-item"><span
            class=" glyphicon glyphicon-list"
            style="margin-right: 10px;margin-left: 8px"></span>计算机</a>
    <a href="${pageContext.request.contextPath}/question?actionName=showTypeList&type=jingsai" class="list-group-item"><span
            class=" glyphicon glyphicon-knight"
            style="margin-right: 10px;margin-left: 8px"></span>竞赛</a>
    <a href="${pageContext.request.contextPath}/question?actionName=showTypeList&type=jjgl" class="list-group-item"><span
            class=" glyphicon glyphicon-usd"
            style="margin-right: 10px;margin-left: 8px"></span>经济管理</a>
    <a href="${pageContext.request.contextPath}/question?actionName=showTypeList&type=wszf" class="list-group-item"><span
            class=" glyphicon glyphicon-align-left"
            style="margin-right: 10px;margin-left: 8px"></span>文史哲法</a>
</ul>

<!--高亮-->
<script>
    var curUrl = window.location.href;
    var nav_group = $("#list-group > *");
    $.each(nav_group, function () {
        if (curUrl.indexOf("kaoyan") != -1) {
            $("#list-group > a:eq(0)").addClass("active");
        }
        if (curUrl.indexOf("waiyu") != -1) {
            $("#list-group > a:eq(1)").addClass("active");
        }
        if (curUrl.indexOf("jisuanji") != -1) {
            $("#list-group > a:eq(2)").addClass("active");
        }
        if (curUrl.indexOf("jingsai") != -1) {
            $("#list-group > a:eq(3)").addClass("active");
        }
        if (curUrl.indexOf("jjgl") != -1) {
            $("#list-group > a:eq(4)").addClass("active");
        }
        if (curUrl.indexOf("wszf") != -1) {
            $("#list-group > a:eq(5)").addClass("active");
        }
    })
</script>