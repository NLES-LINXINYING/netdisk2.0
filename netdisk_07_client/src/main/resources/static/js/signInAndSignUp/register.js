//注册
function register() {
    var name = document.getElementById("username2").value
    var password = document.getElementById("password21").value
    var password2 = document.getElementById("password22").value

    if(name==""||password==""||password2==""){
        layer.msg("以上字段不能为空",{time:500})
        document.getElementById("username2").value=""
        document.getElementById("password21").value=""
        document.getElementById("password22").value=""
    } else if (password != password2) {
        layer.msg("两次密码不一致，请重新输入", {time: 500})
        //清空密码
        document.getElementById("password21").value=""
        document.getElementById("password22").value=""
    } else {
        $.ajax({
            url: 'http://localhost:8763/user/user/register',
            headers:
                {
                    "Content-Type": "application/json"
                },
            type: 'post',
            data: JSON.stringify({name: name, password: password}),
            dataType: 'json',
            success: function (mydata) {
                if (mydata['code'] == 0) {
                    layer.msg("注册成功", {time: 500})
                    //返回登录页面
                    setTimeout(function () {
                        window.location.reload();
                    },500)

                } else {
                    layer.msg("该用户已存在", {time: 500})
                    //刷新当前页面
                    document.getElementById("username2").value=""
                    document.getElementById("password21").value=""
                    document.getElementById("password22").value=""
                }
            },
            error: function () {
                layer.msg("请求失败", {time: 1000})
            }
        })
    }
}