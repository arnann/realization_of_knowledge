<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Modal -->
<div class="modal fade" id="answerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">回复问题</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>问题内容：</p>
                <p id="question-content">后台获取的问题内容</p>
                <p id="changedQuestion">我的回复：</p>
                <textarea id="my-answer" class="form-control"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" id="postAnswer" class="btn btn-primary">提交</button>
            </div>
        </div>
    </div>
</div>

<script>
    // 提交
    function postAnswer(qid){
        if($("#my-answer").val() == null || $("#my-answer").val() == ""){
             alert($("#my-answer").val())
             alert("请完整提交内容~");
             return;
         }
        $.ajax({
            url: "${pageContext.request.contextPath}/answer",
            type: "GET",
            dataType: "JSON",
            data:{
                actionName: "save",
                qid: qid,
                question: $("#question-content").text(),
                answer: $("#my-answer").val()
            },
            success(result){
              if(result.code == 0){
                  alert("回复失败！");
              }else if(result.code == 1){
                  alert("提交成功~");
                  location.reload();
              }else {
                  alert("系统异常！请联系攻城狮修复！")
              }
            }
        });
    }
</script>
