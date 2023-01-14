<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>welcome</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/img/icon.png"/>
</head>
<body>
<!--导航条-->

<div class="row" style="margin-top: 100px;">

    <!--主体-->
    <div class="col-sm-12 text-center">
        <div class="jumbotron" style="height: 510px;">
            <h1 style="margin-top: 105px; height: 120px; padding-top: 20px;padding-left: 130px;">知识变现系统<small
                    style="font-size: 25px;">用知识创造价值</small></h1>
            <h3 style="margin-top: 30px">发布问题，解决问题，让知识变现更迅速、简单。</h3>
            <h2 style="margin-top: 45px">点击开始变现开启知识之旅吧！</h2>
            <p><a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/question?actionName=showTypeList&type=kaoyan"
                  role="button" style="margin-top: 20px;font-size: 25px;">开始变现</a></p>
        </div>
    </div>

</div>
</body>

</html>
