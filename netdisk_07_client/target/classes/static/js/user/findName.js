var token = "Bearer " + getCookie()

//根据fid找文件名字
function findFname(fid) {
    var name=""

    $.ajax({
        url: 'http://localhost:8763/file/file/findById',
        type: 'get',
        headers: {Authorization: token},
        dataType: 'json',
        async: false,
        data: {
            id: fid
        },
        success: function (result) {
            console.log(result.data.name)
            name += result.data.name
        },
        error: function (msg) {
            layer.msg("请求失败", {time: 1000})
        }
    })
    return name
}

//根据ffid找文件夹名字
function findFFname(ffid) {
    var name=""

    $.ajax({
        url: 'http://localhost:8763/file/folder/findById',
        type: 'get',
        headers: {Authorization: token},
        dataType: 'json',
        async: false,
        data: {
            id: ffid
        },
        success: function (result) {
            name += result.data.name
        },
        error: function (msg) {
            layer.msg("请求失败", {time: 1000})
        }
    })

    return name
}