//根据token向后台请求用户名字
window.onload=function () {
    var token="Bearer "+getCookie("my_token")
    $.ajax({
        url: 'http://localhost:8763/user/user/getUserName',
        type: 'get',
        headers: {Authorization: token},
        data: {},
        dataType: 'JSON',
        crossDomain: true,
        success: function (result) {
            document.getElementById("username").innerText=result['data']
        },
        error: function () {
            layer.msg("请求失败", {time: 1000})
        }
    })
}