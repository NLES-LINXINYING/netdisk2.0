//根据fid找文件名字
function findFname(fid) {
    var name=""

    $.ajax({
        url: '/file/findById',
        type: 'get',
        dataType: 'json',
        async: false,
        data: {
            id: fid
        },
        success: function (msg) {
            console.log(msg.name)
            name += msg.name
        },
        error: function (msg) {
            layer.msg("请求失败", {time: 1000})
        }
    })
    return name
}

//根据ffid找文件夹名字
function findFFname(ffid) {
    var name

    $.ajax({
        url: '/folder/findById',
        type: 'get',
        dataType: 'json',
        async: false,
        data: {
            id: ffid
        },
        success: function (msg) {
            name = msg.name
        },
        error: function (msg) {
            layer.msg("请求失败", {time: 1000})
        }
    })

    return name
}