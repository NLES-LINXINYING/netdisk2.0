//还原回收站资源
function revertfunc(data){
    var token="Bearer "+getCookie()

    //console.log(data)

    var rids=new Array()
    rids[0]=data.id


    layer.open({
        type: 1,
        title: "还原文件",
        area: ['250px', '150px'],
        shade: 0,
        content: "<p style='text-align: center;margin-top: 20px;'>还原后资源会在原路径，确认还原？</p>",
        btn: ['确认', '取消'],
        btnAlign: 'c',
        btn1: function (index, layero) {
            $.ajax({
                url: 'http://localhost:8763/file/recyclebin/revert',
                type: 'post',
                headers: {Authorization: token},
                data: {
                    rids:rids.toString()
                },
                success: function (result) {
                    if (result.code==0){
                        layer.msg("还原成功")
                    }
                    setTimeout(function () {
                        window.parent.location.reload()
                    },800)
                },
                error: function () {
                    layer.msg("请求失败", {time: 800})
                    layer.closeAll()
                }
            })
        },
        btn2: function (index, layero) {
            layer.closeAll()
        },
        cancel: function (index, layero) {
            layer.closeAll()
        }
    });
}
