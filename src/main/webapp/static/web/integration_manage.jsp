<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">积分管理</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                您当前的积分是：<span id="current-points">1</span>
                <hr>
                充值积分：<input type="number" name="rechargePoints" id="recharge-points" value="0">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="recharge-button">充值</button>
            </div>
        </div>
    </div>
</div>

<script>
    // 展示模态框
    function integrationManage(){
        // 加载积分
        $.ajax({
            url: "${pageContext.request.contextPath}/user",
            data:{
                actionName:"selectUserInfo"
            },
            type: "GET",
            dataType: "JSON",
            success(result){
                if (result.code == 0){
                    alert(result.message)
                }else {
                    $('#current-points').text(result.data.integration) ;
                }
            }
        })
        $('#exampleModal').modal('show');
    }
    // 充值按钮的点击事件
    $('#recharge-button').click(function() {
        // 获取当前用户的积分
        var currentPoints = parseInt($('#current-points').text());
        // 获取用户输入的充值积分
        var rechargePoints = parseInt($('#recharge-points').val());
        // 将用户输入的充值积分加到当前积分上
        currentPoints += rechargePoints;
        // 更新当前用户的积分
        $.ajax({
            url: "${pageContext.request.contextPath}/user",
            data: {
                actionName: "updateIntegration",
                rechargePoints: currentPoints
            },
            type: "GET",
            dataType: "JSON",
            success(result) {
                if(result.code == 0){
                    alert("充值失败！")
                }else if(result.code == 1){
                    alert("充值成功！")
                    $("#current-points").text(currentPoints);
                }else {
                    alert("系统异常！请联系攻城狮修复！")
                }
            }
        });
    })
</script>