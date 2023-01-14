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

<body style="">
<!--导航条-->
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-2">
            <!-- 导航列表 -->
            <ul class="nav nav-pills nav-stacked">
                <li class="active" id="nav-question"><a href="#my-questions" data-toggle="pill"><span
                        class="glyphicon glyphicon-question-sign" style="margin-right: 8px;"></span>我的提问</a></li>
                <li id="nav-answer"><a href="#my-answers" data-toggle="pill"><span class="glyphicon glyphicon-ok-sign"
                                                                                   style="margin-right: 8px;"></span>我的回复</a>
                </li>
            </ul>
        </div>
        <div class="col-sm-10" style="margin-top: 50px">
            <!-- 展示部分 -->
            <div class="tab-content">
                <!-- 我的提问 -->
                <div id="my-questions" class="tab-pane fade in active">
                    <table class="table table-hover table-striped" style="height: 100px;font-size:15px;">
                        <thead>
                        <tr>
                            <th width="35%" style="text-align: center">问题</th>
                            <th width="35%" style="text-align: center">回复内容</th>
                            <th width="10%" style="text-align: center">问题状态</th>
                            <th width="20%" style="text-align: center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="questionVo" items="${requestScope.pageInfo.data}">
                            <tr>
                                <td style="vertical-align: middle">${questionVo.question}</td>
                                <td style="vertical-align: middle">${questionVo.answer}</td>
                                <td style="text-align: center;vertical-align: middle">
                                    <c:choose>
                                        <c:when test="${questionVo.state == 0}">待解决</c:when>
                                        <c:when test="${questionVo.state == 1}">未支付</c:when>
                                        <c:when test="${questionVo.state == 2}">已支付</c:when>
                                    </c:choose>
                                </td>
                                <td style="text-align: center;vertical-align: middle">
                                    <button class="btn btn-success" onclick="updateQuestion(${questionVo.qid})">修改
                                    </button>
                                    <button class="btn btn-danger" onclick="deleteQuestion(${questionVo.qid})">删除</button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <nav style="text-align: center;">
        <ul class="sticky-bottom pagination pagination-lg">
            <li><a href="${pageContext.request.contextPath}/question?actionName=showMyQuestions&pageNum=1">首页</a></li>
            <li>
                <a href="${pageContext.request.contextPath}/question?actionName=showMyQuestions&pageNum=${requestScope.pageInfo.pageNum-1}">上一页</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/question?actionName=showMyQuestions&pageNum=${requestScope.pageInfo.pageNum+1}">下一页</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/question?actionName=showMyQuestions&pageNum=${requestScope.pageInfo.pageCount}">尾页</a>
            </li>
        </ul>
    </nav>
</div>
<jsp:include page="answer.jsp"></jsp:include>
<!--script-->
<script>
    $("#nav-answer").click(function () {
        window.location.href = "${pageContext.request.contextPath}/answer?actionName=showMyAnswers";
    });

    // 修改模态框展示查询到的问题
    function updateQuestion(qid) {
        $.ajax({
            url: "${pageContext.request.contextPath}/question",
            dataType: "JSON",
            data: {
                actionName: "selectQuestionByQid",
                qid: qid
            },
            type: "GET",
            success(result) {
                if (result.code == 0) {
                    alert("查询问题时出现了异常！")
                } else if (result.code == 1) {
                    if (result.data.state == 1 || result.data.state == 2) {
                        alert('非"待解决"状态不能修改！');
                        return;
                    }
                    $("#question-content").text(result.data.content);
                    $("#postAnswer").attr("onclick", "postAnswer(" + qid + ")");
                    $("#changedQuestion").text("请输入修改后的问题：");
                    $("#myModalLabel").text("修改问题");
                    $("#answerModal").modal('show');
                }
            }
        })
    }

    // 提交修改
    function postAnswer(qid) {
        if ($("#my-answer").val() == null || $("#my-answer").val() == "") {
            alert("请输入修改后的问题~");
            return;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/question",
            type: "GET",
            dataType: "JSON",
            data: {
                actionName: "updateQuestionByQid",
                qid: qid,
                content: $("#my-answer").val()
            },
            success(result) {
                if (result.code == 0) {
                    alert("修改失败");
                } else if (result.code == 1) {
                    alert("修改成功");
                    window.location.reload();
                } else {
                    alert("系统异常！请联系管理员修复！");
                }
            }
        });
    }

    // 删除
    function deleteQuestion(qid) {
        $.ajax({
            url: "${pageContext.request.contextPath}/question",
            type: "GET",
            dataType: "JSON",
            data: {
                actionName: "delete",
                qid: qid
            },
            success(result) {
                if (result.code == 1) {
                    alert("删除成功！")
                    window.location.reload();
                } else if (result.code == 0) {
                    alert(result.message)
                } else {
                    alert("系统异常！请联系攻城狮修复！")
                }
            }
        });
    }
</script>
</body>
</html>