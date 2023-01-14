<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-inverse" style="margin-bottom: 1px;"></nav>
<nav class="navbar navbar-inverse navbar-fixed-top" style="margin-bottom: 1px;"></nav>
<nav class="nav nav-tabs-justified navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="row">
            <div class="col-sm-10">
                <ul class="nav nav-pills nav-justified">
                    <li><a href="${pageContext.request.contextPath}/question?actionName=showTypeList&type=kaoyan" style="margin-top: 3px;font-size: 17px;"><span
                            class=" glyphicon glyphicon-home"
                            style="margin-right: 5px;"></span>主页</a></li>
                    <li><a href="${pageContext.request.contextPath}/static/web/question.jsp" style="margin-top: 2px;font-size: 17px;"><span class=" glyphicon glyphicon-send"
                                                                                   style="margin-right: 5px;"></span>发布问题</a></li>
                    <li><a href="${pageContext.request.contextPath}/question?actionName=showMyQuestions" style="margin-top: 2px;font-size: 17px;"><span class=" glyphicon glyphicon-refresh"
                                                                                   style="margin-right: 5px;"></span>我的申请</a></li>
                    <li><a href="${pageContext.request.contextPath}/payment?actionName=showMyPayments" style="margin-top: 2px;font-size: 17px;"><span class=" glyphicon glyphicon-barcode"
                                                                                   style="margin-right: 5px;"></span>我的订单</a></li>
                    <li><a href="${pageContext.request.contextPath}/static/web/userinfo.jsp" style="margin-top: 2px;font-size: 17px;"><span
                            class=" glyphicon glyphicon-user"
                            style="margin-right: 5px;"></span>个人空间</a></li>
                </ul>
            </div>
            <div class="col-sm-2">
                <li class="dropdown" style="margin-top:14px;font-size: 17px">
                    <span class="text-info">欢迎：</span><span class="text-success">${sessionScope.user.username}</span>
                    <span href="#" class="dropdown-toggle text-info"
                          style="text-decoration-line: none;margin-left: 25px" data-toggle="dropdown" role="button"
                          aria-haspopup="true"
                          aria-expanded="false">我的<span class="caret"></span></span>
                    <ul class="dropdown-menu">
                        <li><a href="#"><span class=" glyphicon glyphicon-envelope"
                                              style="margin-right: 5px; vertical-align: middle;"></span>我的消息</a></li>
                        <li onclick="changePassword()"><a href="#"><span class="glyphicon glyphicon-lock"
                                                                style="margin-right: 5px; vertical-align: middle;"></span>修改密码</a>
                        </li>
                        <li onclick="integrationManage()"><a href="#"><span class="glyphicon glyphicon-shopping-cart"
                                                                style="margin-right: 5px; vertical-align: middle;"></span>积分管理</a>
                        </li>
                        <li role="separator" class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/user?actionName=logout"><span class="glyphicon glyphicon-off"
                                                                    style="margin-right: 5px; vertical-align: middle;"></span>退出登录</a>
                        </li>
                    </ul>
                </li>

            </div>
        </div>
    </div><!-- /.container-fluid -->
</nav>

<jsp:include page="update_pwd.jsp"></jsp:include>
<jsp:include page="integration_manage.jsp"></jsp:include>