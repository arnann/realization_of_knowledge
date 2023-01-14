<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
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

<!--主体-->
<div class="row" style="height: 600px;width: 100%; margin-top: 40px">
    <div class="col-sm-3"></div>
    <div class="col-sm-6 col-center-block" style="margin: auto; background-color:white;">
        <form class="form-horizontal" method="post" id="questionForm">
            <input type="hidden" name="actionName" value="save">
            <div>
                <label class=" control-label" style="font-size: 25px">问题类别</label>
                <div class="radio" id="radio">
                    <label>
                        <input type="radio" name="type" id="optionsRadios1" value="kaoyan">考研
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="type" id="optionsRadios2" value="waiyu">外语
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="type" id="optionsRadios3" value="jisuanji">计算机
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="type" id="optionsRadios4" value="jingsai">竞赛
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="type" id="optionsRadios5" value="jjgl">经济管理
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="type" id="optionsRadios6" value="wszf">文史哲法
                    </label>
                </div>
            </div>
            <div class="form-group" style="padding-left: 10px">
                <label for="title" class=" control-label" style="font-size: 20px">概要描述问题</label>
                <div>
                    <input type="text" class="form-control" id="title" name="title" placeholder="请在这里概述您的问题"
                           autocomplete="off" required>
                </div>
            </div>
            <div class="form-group" style="padding-left: 10px">
                <label for="content" class=" control-label" style="font-size: 20px">问题内容</label>
                <div>
                    <textarea class="form-control" id="content" name="content" rows="6"
                              placeholder="请在这里详细描述您的问题"></textarea>
                </div>
            </div>
            <div class="modal-footer" style="margin: auto;">
                <button type="button" class="btn btn-primary" id="postQuestion" style="margin: auto">提交</button>
            </div>
        </form>
    </div>
    <div class="col-sm-3"></div>
</div>

<!--script-->
<script>
    $("#postQuestion").click(function () {
        if ($("input[name='type']:checked").val() == null || $("#title").val() == null || $("#content").val() == null
            || $("input[name='type']:checked").val() == "" || $("#title").val() == "" || $("#content").val() == "") {
            alert("请完整填写表单内容~");
            alert($("input[name='type']:checked").val());
            return;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/question",
            type: "POST",
            dataType: "JSON",
            data: $("#questionForm").serialize(),
            success(result) {
                if (result.code == 0) {
                    alert(result.data.message);
                } else if (result.code == 1) {
                    alert("提交成功~");
                    window.location.reload();
                } else {
                    alert("系统出错！请联系攻城狮修复！");
                }
            }
        });
    });
</script>
</body>
</html>