//容量进度条更新
window.onload=function capacity() {
    var uid = document.getElementById("uid1").innerText

    $.ajax({
        type: "get",
        url: "/user/updateUsedMemory",
        data: {
            uid: uid
        },
        dataType: 'text',
        success: function (msg) {
            var total = document.getElementById("totalMemory1").innerText;
            var percent = msg / total
            percent = percent * 100

            layui.use('element', function () {
                var element = layui.element;
                element.progress('percent', percent + '%')
            });

            //由字节换算成G，保留两位小数
            msg = msg / 1073741824
            total = total / 1073741824

            var text = msg.toFixed(2) + 'G/' + total.toFixed(2) + 'G';
            document.getElementById("text1").innerText = text;
        },
        error: function () {
            alert("请求失败!");
        }
    })
}