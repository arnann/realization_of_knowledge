<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<div class="container-fluid" style="margin-top: 50px">
    <div class="tab-content">
        <div id="my-questions" class="tab-pane fade in active">
            <table class="table table-hover table-striped" style="height: 100px;font-size:15px;">
                <thead>
                <tr>
                    <th width="20%" style="text-align: center">订单编号</th>
                    <th width="10%" style="text-align: center">回复信息编号</th>
                    <th width="10%" style="text-align: center">回复者</th>
                    <th width="10%" style="text-align: center">订单状态</th>
                    <th width="15%" style="text-align: center">创建时间</th>
                    <th width="15%" style="text-align: center">支付时间</th>
                    <th width="20%" style="text-align: center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="payment" items="${requestScope.pageInfo.data}">
                    <tr>
                        <td style="text-align: center;vertical-align: middle">${payment.pid}</td>
                        <td style="text-align: center;vertical-align: middle">${payment.aid}</td>
                        <td style="text-align: center;vertical-align: middle">${payment.responder}</td>
                        <td style="text-align: center;vertical-align: middle">
                            <c:choose>
                                <c:when test="${payment.state == 1}">未支付</c:when>
                                <c:when test="${payment.state == 2}">已支付</c:when>
                            </c:choose>
                        </td>
                        <td style="text-align: center;vertical-align: middle">${payment.create_time}</td>
                        <td style="text-align: center;vertical-align: middle">${payment.pay_time}</td>
                        <td style="text-align: center;vertical-align: middle">
                            <button class="btn btn-success" onclick="pay(${payment.pid})">支付</button>
                            <button class="btn btn-danger" onclick="deletePayment(${payment.pid})">删除</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <nav style="text-align: center;">
        <ul class="sticky-bottom pagination pagination-lg">
            <li><a href="${pageContext.request.contextPath}/payment?actionName=showMyPayments&pageNum=1">首页</a></li>
            <li>
                <a href="${pageContext.request.contextPath}/payment?actionName=showMyPayments&pageNum=${requestScope.pageInfo.pageNum-1}">上一页</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/payment?actionName=showMyPayments&pageNum=${requestScope.pageInfo.pageNum+1}">下一页</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/payment?actionName=showMyPayments&pageNum=${requestScope.pageInfo.pageCount}">尾页</a>
            </li>
        </ul>
    </nav>
</div>

<!--支付模态框-->
<div class="modal fade" id="payModal" tabindex="-1" role="dialog" aria-labelledby="payModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="payModalLabel">支付积分</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- 选择框 -->
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="payOption" id="payOption1" value="1" checked>
                    <label class="form-check-label" for="payOption1">
                        1 积分
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="payOption" id="payOption2" value="2">
                    <label class="form-check-label" for="payOption2">
                        2 积分
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="payOption" id="payOption3" value="3">
                    <label class="form-check-label" for="payOption3">
                        3 积分
                    </label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="payBtn">确认支付</button>
            </div>
        </div>
    </div>
</div>
<!--script-->
<script>
    // 打开支付
    function pay(pid){
        $("#payModal").modal("show");
        $("#payBtn").attr("onclick","confirmPay(" + pid + ")");
    }
    // 确认支付
    function confirmPay(pid){
        if($("input[name='payOption']:checked").val() == null || $("input[name='payOption']:checked").val() == ""){
            alert("请选择后支付~");
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/payment",
            type: "GET",
            dataType: "JSON",
            data: {
                actionName: "pay",
                pid: pid,
                payOption: $("input[name='payOption']:checked").val()
            },
            success(result){
                if(result.code == 0){
                    alert(result.message)
                }else if(result.code == 1){
                    alert("支付成功！");
                    window.location.reload();
                }else {
                    alert("系统异常！请联系攻城狮修复！");
                }
            }
        })
    }
    // 删除订单
    function deletePayment(pid){
        $.ajax({
            url: "${pageContext.request.contextPath}/payment",
            type: "GET",
            dataType: "JSON",
            data: {
                actionName: "delete",
                pid: pid,
            },
            success(result){
                if(result.code == 0){
                    alert(result.message)
                }else if(result.code == 1){
                    alert("删除成功！");
                    window.location.reload();
                }else {
                    alert("系统异常！请联系攻城狮修复！");
                }
            }
        })
    }
</script>
</body>
</html>
