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

                    //存token
                    setCookie(mydata['data']['token'])

                    //初始化当前路径
                    var path="D:/upload/"+mydata['data']['uname']+"/"

                    setPath(path)
                    //console.log(getPath())

                    var token="Bearer "+getCookie()

                    if(mydata['data']['role']=="admin"){
                       /* //页面跳转，验证token
                        $.ajax({
                            url: 'http://localhost:8763/netdisk/v1/admin/user_manage',
                            type:"get",
                            headers:{Authorization:token},
                            async: false,
                            data: {},
                            success:function(msg){
                                window.location.href = "http://localhost:8040/netdisk/v1/admin/user_manage";
                            },
                            error:function () {

                            }

                        })*/
                        window.location.href='http://localhost:8763/netdisk/v1/admin/user_manage'
                    }else if(mydata['data']['role']=="user"){
                        /*//页面跳转，验证token
                        $.ajax({
                            url: "http://localhost:8763/netdisk/v1/user/page_first",
                            type:"get",
                            headers:{Authorization:token},
                            async: false,
                            data: {},
                            success:function(msg){
                                window.location.href = "http://localhost:8040/netdisk/v1/user/page_first";
                            },
                            error:function () {

                            }

                        })*/
                        window.location.href='http://localhost:8763/netdisk/v1/user/page_first'
                    }
                }else {
                    layer.msg("用户名或密码不正确，请重新输入")
                    //刷新当前页面
                    setTimeout(function () {
                        window.location.reload()
                    },500)
                }
            },
            error: function () {
                layer.msg("请求失败", {time: 500})
            }
        })
    }
}