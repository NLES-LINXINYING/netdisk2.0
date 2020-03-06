//退出登录
function logout() {
    $.ajax({
        type: "post",
        url: "/netdisk/logout",
        data: {},
        dataType: "text",
        success: function (msg) {
            if (msg == "1") {
                window.location.href = "/login";
            } else {
                alert("登出失败!");
            }
        },
        error: function () {
            alert("请求失败!");
        }
    })
}