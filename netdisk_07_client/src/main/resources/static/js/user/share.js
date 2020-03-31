//分享
function sharefunc(data) {
    var table = layui.table

    var uid = document.getElementById("uid").innerText


    var fid = new Array();
    var ffid = new Array();
    var fi = 0;
    var ffi = 0;

    if (data.type != undefined) {
        fid[0] = data.id
    } else {
        ffid[0] = data.id
    }


    layer.open({
        type: 2,
        title: '分享文件',
        shade: false,
        area: ['400px', '300px'],
        maxmin: false,
        scrollbar: false,
        content: "http://localhost:8763/netdisk/v1/user/addShare?uid=" + uid + "&ffid=" + ffid + "&fid=" + fid,
        zIndex: layer.zIndex, //重点1
        success: function (layero) {
            layer.setTop(layero); //重点2
        }
    });
}


//取消分享
function nosharefunc(data) {
    var token = "Bearer " + getCookie()


    var mids = new Array();


    mids[0] = data.id


    layer.msg("是否确认删除该分享链接？",
        {
            time: 30000,
            btnAlign: 'c',
            btn: ["确认", "取消"],
            btn1: function (index) {
                $.ajax({
                    url: 'http://localhost:8763/file/myshare/delete',
                    type: 'get',
                    headers: {Authorization: token},
                    data: {
                        mids: mids.toString()
                    },
                    success: function () {
                        layer.msg("取消分享成功")
                        setTimeout(function () {
                            layer.closeAll()
                            window.location.reload();
                        },800)

                    },
                    error: function () {
                        layer.msg("请求失败", {time: 800})
                        layer.closeAll()
                    }
                })
                //layer.close(index)
            },
            btn2: function (index) {
                layer.close(index)
            }
        }
    )

}