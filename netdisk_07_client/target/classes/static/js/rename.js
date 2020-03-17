//重命名
function renamefunc() {
    var data = layui.table.checkStatus('ftable').data
    var length = data.length
    console.log(data[0])
    console.log(length)

    if (length == 0) {
        layer.msg("请选择文件", {time: 800})
    } else if (length == 1) {

        layer.open({
            type: 1,
            area: ['300px', '180px'],
            title: '重命名',
            content: $("#renameform"),
            shade: 0,
            btnAlign: 'c',
            scrollbar: false,
            success: function (index, layero) {
                document.getElementById("ffname1").value = data[0].name
                //console.log(111)
            },
            btn: ['提交', '取消']
            , btn1: function (index, layero) {
                var ffname = $("#ffname1").val()
                //console.log(ffname)
                if (data[0].type == undefined) {
                    //console.log("folder")
                    $.ajax({
                        url: '/folder/updateName',
                        type: 'get',
                        dataType: 'text',
                        data: {
                            name: ffname,
                            id: data[0].id
                        },
                        success: function (msg) {
                            //console.log(msg)
                            if (msg == "1") {
                                layer.msg("重命名成功", {time: 1000})
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
                } else {
                    $.ajax({
                        url: '/file/updateName',
                        type: 'get',
                        dataType: 'text',
                        data: {
                            name: ffname,
                            id: data[0].id
                        },
                        success: function (msg) {
                            //console.log(msg)
                            if (msg == "1") {
                                layer.msg("重命名成功", {time: 1000})
                                //console.log(111)
                                layer.closeAll()
                                window.location.reload();
                            } else {
                                layer.msg("失败，该文件已存在", {time: 1000})
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
                }

            },
            btn2: function (index, layero) {
                layer.closeAll()
                window.location.reload();
            },
            cancel: function (layero, index) {
                layer.closeAll()
                window.location.reload();
            }
        });

    } else {
        layer.msg("只能对一个文件夹或文件进行重命名", {time: 1000})
    }
}
