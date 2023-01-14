<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page buffer="8192kb" autoFlush="true" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>知识变现</title>
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.js"></script>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/img/icon.png"/>
</head>

<body style="background-color: #f7f7f7">
<!--导航条-->
<jsp:include page="navbar.jsp"></jsp:include>

<div class="row">
    <div class="col-sm-2">
        <jsp:include page="list.jsp"></jsp:include>
    </div>
    <div class="col-sm-10">
        <div role="tabpanel" class="tab-pane" id="tab7">
            <ul class="list-unstyled" style="height: 520px; margin-left: 25px;">
                <c:forEach items="${requestScope.pageInfo.data}" var="item">
                    <li>
                        <div class="row" style=" padding-top: 20px;margin-top: 22px;">
                            <div class="col-sm-8">
                                <div>
                                <span class="glyphicon glyphicon-fire"
                                      style="color: #ff0000; font-size: 20px;margin-right: 5px;"
                                      aria-hidden="true"></span>
                                    <a href="#" style="font-size: 20px;color: #0f0f0f">${item.title}</a>
                                </div>
                                <div style="height: 50px;padding-top:18px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;display: block;">
                                    <a href="#" style="font-size: 15px;color: #0f0f0f">${item.content}</a>
                                </div>
                            </div>
                            <div class="col-sm-4" style="padding-top: 20px;">
                                <button type="button" class="btn btn-primary" style="margin: auto; width: 90px;"
                                        onclick="answer(${item.qid})">回复
                                </button>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <nav style="text-align: center;">
        <ul class="sticky-bottom pagination pagination-lg">
            <li><a href="/question?actionName=showTypeList&type=${requestScope.type}&pageNum=1">首页</a></li>
            <li>
                <a href="/question?actionName=showTypeList&type=${requestScope.type}&pageNum=${requestScope.pageInfo.pageNum-1}">上一下</a>
            </li>
            <li>
                <a href="/question?actionName=showTypeList&type=${requestScope.type}&pageNum=${requestScope.pageInfo.pageNum+1}">下一页</a>
            </li>
            <li>
                <a href="/question?actionName=showTypeList&type=${requestScope.type}&pageNum=${requestScope.pageInfo.pageCount}">尾页</a>
            </li>
        </ul>
    </nav>
</div>
<jsp:include page="answer.jsp"></jsp:include>
<!--script-->
<script>
    function answer(qid){
        $("#answerModal").modal("show");
        $.ajax({
            url: "${pageContext.request.contextPath}/question",
            dataType: "JSON",
            data: {
              actionName: "selectQuestionByQid",
              qid: qid
            },
            type: "GET",
            success(result){
                if(result.code == 0){
                    alert(result.message)
                }else if(result.code == 1){
                    $("#question-content").text(result.data.content);
                    // 给提交按钮绑定问题的id
                    $("#postAnswer").attr("onclick",'postAnswer(' + qid + ')');
                }else {
                    alert("系统异常！请联系攻城狮修复！")
                }
            }
        });
    }
</script>
</body>
</html>