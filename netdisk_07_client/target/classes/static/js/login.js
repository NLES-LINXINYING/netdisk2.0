//登录
function login() {
    var name = document.getElementById("username1").value
    var password = document.getElementById("password1").value

    if(name==""||password==""){
        layer.msg("以上字段不能为空",{time:500})
    }else{
        $.ajax({
            type: 'get',
            url: 'http://localhost:8763/user/user/login',
            data: {name: name, password: password},
            dataType: 'JSON',
            crossDomain: true,
            success: function (mydata) {
                if(mydata['code']==0){
                    setTokenToCookie(mydata['data']['token'])
                    if(mydata['data']['role']=="admin"){
                        console.log("admin")
                        layer.msg("登录成功",{time:500})
                        //跳转到系统界面
                        setTimeout(function () {
                            window.location.href = "/redirect/admin/user_manage";
                        },500)
                    }else if(mydata['data']['role']=="user"){
                        layer.msg("登录成功",{time:500})
                        //跳转到系统界面
                        setTimeout(function () {
                            window.location.href = "/redirect/user/page_all";
                        },500)
                    }
                }else {
                    layer.msg("用户名或密码不正确，请重新输入", {time: 500})
                    //刷新当前页面
                    setTimeout(function () {
                        window.location.reload()
                    },500)
                }
            },
            error: function () {
                layer.msg("请求失败", {time: 1000})
            }
        })
    }
}