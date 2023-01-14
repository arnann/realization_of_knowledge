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
                <li id="nav-question"><a href="#my-questions" data-toggle="pill"><span
                        class="glyphicon glyphicon-question-sign" style="margin-right: 8px;"></span>我的提问</a></li>
                <li class="active" id="nav-answer"><a href="#my-answers" data-toggle="pill"><span
                        class="glyphicon glyphicon-ok-sign" style="margin-right: 8px;"></span>我的回复</a></li>
            </ul>
        </div>
        <div class="col-sm-10" style="margin-top: 50px">
            <!-- 展示部分 -->
            <div class="tab-content">
                <!-- 我的回复 -->
                <div id="my-answers" class="tab-pane fade in active">
                    <table class="table table-striped" style="height: 100px;font-size:15px;">
                        <thead>
                        <tr>
                            <th width="10%" style="text-align: center">编号</th>
                            <th width="35%" style="text-align: center">回复的问题</th>
                            <th width="35%" style="text-align: center">回复内容</th>
                            <th width="20%" style="text-align: center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="answer" items="${requestScope.pageInfo.data}">
                            <tr>
                                <td style="text-align: center;vertical-align: middle">${answer.aid}</td>
                                <td style="vertical-align: middle">${answer.question}</td>
                                <td style="vertical-align: middle">${answer.answer}</td>
                                <td style="text-align: center;vertical-align: middle">
                                    <button class="btn btn-success" onclick="updateAnswer(${answer.aid})">修改</button>
                                    <button class="btn btn-danger" onclick="deleteAnswer(${answer.aid})">删除</button>
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
            <li><a href="${pageContext.request.contextPath}/answer?actionName=showMyAnswers&pageNum=1">首页</a></li>
            <li>
                <a href="${pageContext.request.contextPath}/answer?actionName=showMyAnswers&pageNum=${requestScope.pageInfo.pageNum-1}">上一下</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/answer?actionName=showMyAnswers&pageNum=${requestScope.pageInfo.pageNum+1}">下一页</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/answer?actionName=showMyAnswers&pageNum=${requestScope.pageInfo.pageCount}">尾页</a>
            </li>
        </ul>
    </nav>
</div>
<jsp:include page="answer.jsp"></jsp:include>
<!--script-->
<script>
    $("#nav-question").click(function () {
        window.location.href = "${pageContext.request.contextPath}/question?actionName=showMyQuestions";
    });

    // 修改模态框
    function updateAnswer(aid) {
        // 渲染模态框
        $.ajax({
            url: "${pageContext.request.contextPath}/answer",
            type: "GET",
            dataType: "JSON",
            data: {
                actionName: "selectAnswerByAid",
                aid: aid
            },
            success(result) {
                if (result.code == 0) {
                    alert(result.message);
                    return;
                } else if (result.code == 1) {
                    $("#myModalLabel").text("修改回复");
                    $("#question-content").text(result.data.question);
                    $("#changedQuestion").text("修改后的回复内容：");
                    // 讲aid传递给提交按钮
                    $("#postAnswer").attr("onclick", "postAnswer(" + aid + ")");
                    $("#answerModal").modal('show');
                } else {
                    alert("系统异常！请联系攻城狮修复！")
                    return;
                    ;
                }
            }
        });
    }

    // 提交修改
    function postAnswer(aid) {
        if ($("#my-answer").val() == null || $("#my-answer").val == "") {
            alert("请填写后提交~");
            return;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/answer",
            type: "GET",
            dataType: "JSON",
            data: {
                actionName: "update",
                aid: aid,
                answer: $("#my-answer").val()
            },
            success(result) {
                if (result.code == 0) {
                    alert(result.message);
                } else if (result.code == 1) {
                    alert("修改成功！");
                    window.location.reload();
                } else {
                    alert("系统异常！请联系攻城狮修复！")
                }
            }
        })
    }

    // 删除
    function deleteAnswer(aid) {
        $.ajax({
            url: "${pageContext.request.contextPath}/answer",
            type: "GET",
            dataType: "JSON",
            data: {
                actionName: "deleteAnswerByAid",
                aid: aid
            },
            success(result) {
                if (result.code == 0) {
                    alert(result.message)
                } else if (result.code == 1) {
                    alert("删除成功~");
                    window.location.reload();
                } else {
                    alert("系统异常！请联系攻城狮修复！")
                }
            }
        })
    }
</script>
</body>
</html>