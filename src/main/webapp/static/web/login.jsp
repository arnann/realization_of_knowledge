<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login and Sign up</title>
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/reset.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/img/icon.png"/>
</head>
<body>

<section class="user">
    <div class="user_options-container">
        <div class="user_options-text">
            <div class="user_options-unregistered">
                <h2 class="user_unregistered-title">没有账号？</h2>
                <p class="user_unregistered-text">点击按钮注册成为会员.</p>
                <button class="user_unregistered-signup" id="signup-button">注册</button>
            </div>
            <div class="user_options-registered">
                <h2 class="user_registered-title">已有账号?</h2>
                <p class="user_registered-text">点击按钮会员登录.</p>
                <button class="user_registered-login" id="login-button">登录</button>
            </div>
        </div>

        <div class="user_options-forms" id="user_options-forms">
            <div class="user_forms-login">
                <h2 class="forms_title">登录</h2>
                <form class="forms_form" action="${pageContext.request.contextPath}/user" method="post">
                    <input type="hidden" name="actionName" value="login">
                    <fieldset class="forms_fieldset">
                        <div class="forms_field">
                            <input type="text" placeholder="用户名" name="username" class="forms_field-input"
                                   value="${requestScope.resultInfo.data.username}" required/>
                        </div>
                        <div class="forms_field">
                            <input type="password" placeholder="密码" name="password" class="forms_field-input"
                                   value="${requestScope.resultInfo.data.password}" autocomplete="off" required/>
                        </div>
                        <div class="forms_field">
                            <input type="checkbox" name="rem" value="1" style="vertical-align: middle"/><span
                                class="user_unregistered-text">记住我</span>
                            <span class="user_unregistered-text"
                                  style="color: #e14641">${requestScope.resultInfo.message}</span>
                        </div>
                    </fieldset>
                    <div class="forms_buttons">
                        <%--<button type="button" class="forms_buttons-forgot"><a href="#" style="color: gray;">忘记密码?</a>--%>
                        <%--</button>--%>
                        <input type="submit" value="登录" class="forms_buttons-action">
                    </div>
                </form>
            </div>
            <div class="user_forms-signup">
                <h2 class="forms_title">注册</h2>
                <form class="forms_form" action="${pageContext.request.contextPath}/user" method="post">
                    <input type="hidden" name="actionName" value="register">
                    <fieldset class="forms_fieldset">
                        <div class="forms_field">
                            <input type="text" placeholder="用户名" name="username" class="forms_field-input" required/>
                        </div>
                        <div class="forms_field">
                            <input type="password" placeholder="密码" name="password" class="forms_field-input"
                                   autocomplete="off" required/>
                        </div>
                    </fieldset>
                    <div class="forms_buttons">
                        <input type="submit" value="注册" class="forms_buttons-action">
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<script src="${pageContext.request.contextPath}/static/js/script.js" type="javascript"></script>
<script>
    $("#signup-button").click(function () {
        window.location.href = "${pageContext.request.contextPath}/static/web/register.jsp";
    });
</script>


</body>
</html>
