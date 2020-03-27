//删除
function deletefunc(data) {
    console.log(data)

    var token = "Bearer " + getCookie()

    //请求用户名
    var uname = ""
    $.ajax({
        url: 'http://localhost:8763/user/user/getUserInfo',
        type: 'get',
        headers: {Authorization: token},
        dataType: 'json',
        async: false,
        data: {},
        success: function (result) {
            uname = result['data']['name']
        },
        error: function (msg) {
            layer.msg("请求失败", {time: 800})
        }
    })

    console.log(uname)


    var uid = document.getElementById("uid").innerText

    console.log(uid)


    var fid = new Array();
    var ffid = new Array();
    var fi = 0;
    var ffi = 0;

    if (data['type'] != undefined) {
        fid[0] = data['id']
    } else {
        ffid[0] = data['id']
    }

    if (ffid == "") {
        ffid = "null"
    }
    if (fid == "") {
        fid = "null"
    }

    console.log(ffid)
    console.log(fid)

    layer.open({
        type: 1,
        title: "删除文件",
        area: ['250px', '150px'],
        content: "<p style='text-align: center;margin-top: 20px;'>确认删除这些文件吗？</p>",
        btn: ['确认', '取消'],
        btnAlign: 'c',
        btn1: function (index, layero) {
            $.ajax({
                url: 'http://localhost:8763/file/delete',
                type: 'post',
                headers: {Authorization: token},
                data: {
                    ffids: ffid.toString(),
                    fids: fid.toString(),
                    uid: uid,
                    uname: uname
                },
                success: function () {
                    layer.msg("删除成功", {time: 800})
                    layer.closeAll()
                    //window.location.reload();
                },
                error: function () {
                    layer.msg("请求失败", {time: 800})
                    layer.closeAll()
                    //window.location.reload();
                }
            })
            setTimeout(function () {
                window.location.reload()
            }, 800)
        },
        btn2: function (index, layero) {
            layer.closeAll()
        },
        cancel: function (index, layero) {
            layer.closeAll()
        }
    });
}



//彻底删除
function deletefunc2(data){
    console.log(data)

    //
}


