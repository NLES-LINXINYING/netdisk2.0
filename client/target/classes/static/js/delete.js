//删除
function deletefunc() {
    var uid=document.getElementById("uid1").innerText
    var uname=document.getElementById("username1").innerText
    var data = layui.table.checkStatus('ftable').data
    var length = data.length

    console.log(uname)


    if (length == 0) {
        layer.msg("请选择文件", {time: 1000})
    } else {
        var fid = new Array();
        var ffid = new Array();
        var fi = 0;
        var ffi = 0;
        for (var i = 0; i < length; i++) {
            if (data[i].type != undefined) {
                fid[fi++] = data[i].id
            } else {
                ffid[ffi++] = data[i].id
            }
        }
        if (ffid == "") {
            ffid = "null"
        }
        if (fid == "") {
            fid = "null"
        }


        layer.open({
            type: 1,
            title: "删除文件",
            area: ['250px', '150px'],
            content: "<p style='text-align: center;margin-top: 20px;'>确认删除这些文件吗？</p>",
            btn: ['确认', '取消'],
            btnAlign: 'c',
            btn1: function (index, layero) {
                $.ajax({
                    url: '/all/delete',
                    type: 'post',
                    data: {
                        ffids: ffid.toString(),
                        fids: fid.toString(),
                        uid:uid,
                        uname:uname
                    },
                    success: function () {
                        layer.msg("删除成功", {time: 5000})
                        layer.closeAll()
                        //window.location.reload();
                    },
                    error: function () {
                        layer.msg("请求失败", {time: 5000})
                        layer.closeAll()
                        //window.location.reload();
                    }
                })
                setTimeout(function () {
                    window.location.reload()
                },1000)
            },
            btn2: function (index, layero) {
                layer.closeAll()
            },
            cancel: function (index, layero) {
                layer.closeAll()
            }
        });
    }
}


