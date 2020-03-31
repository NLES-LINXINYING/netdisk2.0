function moveto(data) {
    var uid = document.getElementById("uid").innerText


    var fid = new Array();
    var ffid = new Array();
    var fi = 0;
    var ffi = 0;


    if (data['type'] != undefined) {
        fid[0] = data['id']
    } else {
        ffid[0] = data['id']
    }


    layer.open({
        type: 2,
        title: '移动文件到',
        shade: false,
        area: ['450px', '360px'],
        maxmin: false,
        scrollbar: false,
        content: "http://localhost:8763/netdisk/v1/user/moveSelectPath?uid=" + uid + "&ffid=" + ffid + "&fid=" + fid,
        zIndex: layer.zIndex, //重点1
        success: function (layero) {
            layer.setTop(layero); //重点2
        }
    });


}