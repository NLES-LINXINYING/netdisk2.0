//新建文件夹
function newFolder() {
    var uid = document.getElementById("uid1").innerText
    var path = document.getElementById("path2").innerText

    layer.open({
        type: 1,
        area: ['300px', '180px'],
        title: '新建文件夹',
        content: $("#newfolder1"),
        shade: 0,
        btnAlign: 'c',
        scrollbar: false,
        btn: ['提交', '取消']
        , btn1: function (index, layero) {
            var ffname = $("#ffname").val()
            console.log(ffname)

            $.ajax({
                url: '/folder/add',
                type: 'post',
                dataType: 'text',
                data: {
                    name: ffname,
                    path: path,
                    uid: uid
                },
                success: function (msg) {
                    //console.log(msg)
                    if (msg == "1") {
                        layer.msg("新建文件夹成功", {time: 1000})
                        //console.log(111)
                        layer.closeAll()
                        window.location.reload();
                    } else {
                        layer.msg("失败，该文件夹已存在", {time: 1000})
                        layer.closeAll()
                        window.location.reload();
                    }
                },
                error: function (msg) {
                    layer.msg("请求失败", {time: 1000})
                    layer.closeAll()
                    window.location.reload();
                }
            })
        },
        btn2: function (index, layero) {
            layer.closeAll()
            window.location.reload();
        },
        cancel: function (layero, index) {
            layer.closeAll()
            window.location.reload();
        }
    })
}