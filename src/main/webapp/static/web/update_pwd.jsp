<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="gridSystemModalLabel">修改密码</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="changePasswordForm">
                    <div class="form-group">
                        <label for="oldPassword" class="col-sm-3 control-label">旧密码</label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" id="oldPassword" placeholder="OldPassword"
                                   autocomplete="off" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newPassword" class="col-sm-3 control-label">新密码</label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" id="newPassword" placeholder="NewPassword"
                                   autocomplete="off" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newPassword2" class="col-sm-3 control-label">确认密码</label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" id="newPassword2" placeholder="ConfirmPassword"
                                   autocomplete="off" required>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="confirmChangePassword">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
    // 显示修改密码模态框
    function changePassword() {
        $('#changePasswordModal').modal('show');
    }
    // 修改密码ajax请求
    $('#confirmChangePassword').click(function () {
        if ($('#oldPassword').val() == null || $('#newPassword').val() == null || $('#newPassword2').val() == null
            || $('#oldPassword').val() == "" || $('#newPassword').val() == "" || $('#newPassword2').val() == "") {
            alert("请完整填写表单内容~");
            return;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/user",
            type: 'POST',
            data: {
                actionName: 'changePassword',
                oldPassword: $('#oldPassword').val(),
                newPassword: $('#newPassword').val(),
                newPassword2: $('#newPassword2').val(),
            },
            dataType: 'JSON',
            success(result) {
                if (result.code == 1) {
                    alert("修改成功！");
                } else if (result.code == 0) {
                    alert("修改失败，" + result.message);
                } else {
                    alert("系统故障！请联系攻城狮修复！");
                }
            }
        });
    });
</script>
