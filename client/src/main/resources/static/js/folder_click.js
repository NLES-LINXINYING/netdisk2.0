//点击文件夹
function clickfolder(data) {
    var uid = document.getElementById("uid1").innerText;
    var path = document.getElementById("path2").innerText
    var curpath = path + "" + data + "/"
    //console.log(curpath)
    document.getElementById("path2").innerText = curpath;
    //console.log(document.getElementById("path2").innerText)

    //更新session记录的路径
    $.ajax({
        type: "post",
        url: "/user/chgPath",
        data: {
            path: curpath
        },
        success: function () {
        },
        error: function () {
            alert("请求失败!");
        }
    })
    //更新，获取最新的session
    window.location.reload()


    //重载数据
    layui.use('table', function () {
        var table = layui.table
        table.reload('ftable', {
            url: '/all/findByPath'
            , where: {
                uid: uid,
                path: curpath
            }
        });

    });
}