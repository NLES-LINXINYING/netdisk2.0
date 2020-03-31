//重命名
function renamefunc(data) {

    var token = "Bearer " + getCookie()

    if (data==null) {
        layer.msg("请选择文件", {time: 800})
    } else  {

        layer.open({
            type: 1,
            area: ['300px', '180px'],
            title: '重命名',
            content: $("#renameform"),
            shade: 0,
            btnAlign: 'c',
            scrollbar: false,
            success: function (index, layero) {
                document.getElementById("ffname1").value = data['name']
            },
            btn: ['提交', '取消']
            , btn1: function (index, layero) {
                var ffname = $("#ffname1").val()
                console.log(ffname)

                // 文件夹重命名
                if (data['type'] == undefined) {
                    $.ajax({
                        url: 'http://localhost:8763/file/folder/updateName',
                        type: 'put',
                        headers: {Authorization: token},
                        dataType: 'json',
                        data: {
                            name: ffname,
                            id: data['id']
                        },
                        success: function (msg) {
                            if (msg['data'] == 1) {
                                layer.msg("重命名成功")
                                setTimeout(function () {
                                    window.parent.location.reload();
                                },800)
                            } else {
                                layer.msg("失败，该文件夹已存在")
                                setTimeout(function () {
                                    window.parent.location.reload();
                                },800)
                            }
                        },
                        error: function (msg) {
                            layer.msg("请求文件重命名失败")
                            setTimeout(function () {
                                window.parent.location.reload();
                            },800)
                        }
                    })
                }

                // 文件重命名
                else {
                    $.ajax({
                        url: 'http://localhost:8763/file/file/updateName',
                        type: 'put',
                        headers: {Authorization: token},
                        dataType: 'json',
                        data: {
                            name: ffname,
                            id: data.id
                        },
                        success: function (msg) {
                            if (msg['code'] == 0) {
                                layer.msg("重命名成功")
                                setTimeout(function () {
                                    window.parent.location.reload();
                                },800)
                            } else {
                                layer.msg("失败，该文件已存在")
                                setTimeout(function () {
                                    window.parent.location.reload();
                                },800)
                            }
                        },
                        error: function (msg) {
                            layer.msg("请求失败")
                            setTimeout(function () {
                                window.parent.location.reload();
                            },800)
                        }
                    })
                }
            },
            btn2: function (index, layero) {
                layer.closeAll()
            },
            cancel: function (layero, index) {
                layer.closeAll()
            }
        });

    }
}
