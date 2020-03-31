//新建文件夹
function newFolder() {
    var uid = document.getElementById("uid").innerText
    var path = sessionStorage.getItem('uploadPath')
    var token="Bearer "+getCookie()

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

            $.ajax({
                url: 'http://localhost:8763/file/folder/add',
                type: 'post',
                headers: {Authorization: token},
                dataType: 'JSON',
                data: {
                    name: ffname,
                    path: path,
                    uid: uid
                },
                success: function (msg) {
                    if (msg['code'] == 0) {
                        layer.close(index)
                        layer.msg("新建文件夹成功")
                        setTimeout(function () {
                            window.location.reload()
                        },800)

                    } else {
                        layer.close(index)
                        layer.msg("已存在同名文件夹",{time:800})
                    }
                },
                error: function (msg) {
                    layer.close(index)
                    layer.msg("请求失败",{time:800})
                }
            })
        },
        btn2: function (index, layero) {
            layer.closeAll()
        },
        cancel: function (layero, index) {
            layer.closeAll()
        }
    })
}