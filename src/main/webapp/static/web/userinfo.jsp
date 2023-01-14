<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息</title>
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" charset="UTF-8"
          href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.js"></script>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/img/icon.png"/>
</head>
<body style="background-color: #f7f7f7">

<!--导航条-->
<jsp:include page="navbar.jsp"></jsp:include>

<!--主题内容-->
<form class="form-horizontal" style="margin-top: 38px">
    <div class="form-group">
        <label class="col-sm-4 control-label">我的头像</label>
        <div class="col-sm-3">
            <img src="" height="180px" alt="未设置头像" id="tx">
        </div>
    </div>
    <div class="form-group">
        <label for="uname" class="col-sm-4 control-label">我的昵称</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" id="uname" readonly required>
        </div>
    </div>
    <div class="form-group">
        <label for="xb" class="col-sm-4 control-label">我的性别</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" id="xb" readonly required>
        </div>
    </div>
    <div class="form-group">
        <label for="nl" class="col-sm-4 control-label">我的年龄</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" id="nl" readonly required>
        </div>
    </div>
    <div class="form-group">
        <label for="bj" class="col-sm-4 control-label">我的班级</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" id="bj" readonly required>
        </div>
    </div>
    <div class="form-group">
        <label for="integration" class="col-sm-4 control-label">我的积分</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" id="integration" readonly required>
        </div>
    </div>
    <div class="form-group">
        <label for="jieshao" class="col-sm-4 control-label">我的简介</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" id="jieshao" readonly required>
        </div>
    </div>
</form>
<div class="row">
    <div class="col-sm-4">
        <button type="button" id="updateUserInfo" class="col-sm-offset-11 btn btn-danger">我要修改</button>
    </div>
    <div class="col-sm-4"></div>
</div>

<!--模态框-->
<!--修改个人信息模态框-->
<div class="modal fade" id="userUploadModal" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h5 class="modal-title" id="gridSystemModalLabel">修改个人信息</h5>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/userUpload" method="post" enctype="multipart/form-data" class="form-horizontal"
                      id="userUpload">
                    <div class="form-group">
                        <label for="username" class="col-sm-3 control-label">用户名</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" name="username" id="username"
                                   placeholder="Username">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="age" class="col-sm-3 control-label">年龄</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" name="age" id="age" placeholder="Age">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">性别</label>
                        <div class="col-sm-7">
                            <div class="radio">
                                <label for="man">男</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="radio" name="gender" id="man" value="1">
                                <label for="woman">女</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="radio" name="gender" id="woman" value="0">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="classnum" class="col-sm-3 control-label">班级</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" name="classnum" id="classnum"
                                   placeholder="Class">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="introduction" class="col-sm-3 control-label">介绍</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" name="introduction" id="introduction"
                                   placeholder="Introduction">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="avatar" class="col-sm-3 control-label">头像</label>
                        <div class="col-sm-7">
                            <input type="file" id="avatar" name="avatar">
                            <p class="help-block" id="tips">请选择图片.</p>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="confirmUpload">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
    // 加载信息
    $(function () {
        $.ajax({
            url: "${pageContext.request.contextPath}/user",
            data: {
                actionName: "selectUserInfo"
            },
            dataType: "JSON",
            type: "GET",
            success(result) {
                if (result.code == 1) {
                    $("#uname").attr('value', result.data.username);
                    if (result.data.gender == 1) {
                        $("#xb").attr('value', "男");
                    } else {
                        $("#xb").attr('value', "女");
                    }
                    $("#nl").attr('value', result.data.age);
                    $("#bj").attr('value', result.data.classnum);
                    $('#jieshao').attr('value', result.data.introduction);
                    $('#integration').attr('value', result.data.integration);
                    $('#tx').attr("src", "${pageContext.request.contextPath}/static/upload/" + result.data.avatar);
                } else if (result.code == 0) {
                    console.log(result)
                    alert("页面出错");
                } else {
                    alert("系统错误，请联系攻城狮修复！");
                }
            }
        });
    });

    // 打开模态框
    $("#updateUserInfo").click(function () {
        $('#userUploadModal').modal('show');
        $("#username").attr('value', $("#uname").val())
        $("#age").attr('value', $("#nl").val())
        $("#classnum").attr('value', $("#bj").val())
        $("#introduction").attr('value', $("#jieshao").val())
        $("#username").attr('value', $("#uname").val())
        $("#xb").val() == "男" ? $("#man").attr("checked", 'checked') : $("#woman").attr("checked", 'checked');
    });
    // 提交模态框
    $("#confirmUpload").click(function () {
        if ($("#username").val() == null || $("#username").val() == "" || $("#age").val() == null || $("#age") == ""
            || $("#classnum").val() == null || $("#classnum").val() == "" ||
            $("#introduction").val() == null || $("#introduction").val() == "") {
            alert("请完整填写内容！");
            return;
        }
        var formData = new FormData($("#userUpload")[0]);
        $.ajax({
            url: "${pageContext.request.contextPath}/userUpload",
            type: "POST",
            processData: false,
            contentType: false,
            data: formData,
            dataType: "JSON",
            success(result) {
                if (result.code == 1) {
                    location.reload();
                    alert("修改成功！")
                    $("#userUploadModal").modal('hide');
                } else if (result.code == 0) {
                    alert(result.message)
                } else {
                    alert("系统错误，请联系攻城狮修复！")
                }
            }
        });
    });
</script>
</body>
</html>
