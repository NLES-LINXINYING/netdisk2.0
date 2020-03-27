//根据token获取用户名和更新容量条
window.onload = function () {

    var token = "Bearer " + getCookie()

    $.ajax({
        url: 'http://localhost:8763/user/user/getUserInfo',
        type: 'get',
        headers:
            {
                Authorization: token
            },
        dataType: 'json',
        async: false,
        data: {},
        success: function (result) {
            document.getElementById("uid").innerText=result['data']['id']
            document.getElementById("username").innerText=result['data']['name']
            document.getElementById("path1").innerText="D:/upload/"+result['data']['name']+"/"

            var used = result['data']['usedMemory']
            var total = result['data']['totalMemory']
            var percent = used / total
            percent = percent * 100

            layui.use('element', function () {
                var element = layui.element;
                element.progress('percent', percent + '%')
            });

            //由字节换算成G，保留两位小数
            used = used / 1073741824
            total = total / 1073741824

            var text = used.toFixed(2) + 'G/' + total.toFixed(2) + 'G';
            document.getElementById("text1").innerText = text;
        },
        error: function (msg) {
            layer.msg("请求失败", {time: 1000})
        }
    })
}