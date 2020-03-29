//初始化页面信息（用户，文件）
function pageInit() {

    var token = "Bearer " + getCookie()
    var uid=''

    //获取用户名，用户角色
    $.ajax({
        url: 'http://localhost:8763/user/user/getUserInfo',
        type: 'get',
        headers: {Authorization: token},
        dataType: 'json',
        async: false,
        data: {},
        success: function (result) {
            // console.log(result)
            uid=result['data']['id']
            document.getElementById("uid").innerText=uid
            document.getElementById("username").innerText=result['data']['name']
            document.getElementById("username2").innerText='你好，'+result['data']['name']
            if(result['data']['type']==2){
                document.getElementById("userroles").innerText='管理员'
            }else{
                document.getElementById("userroles").innerText='普通用户'
            }
            document.getElementById("path1").innerText='D:/upload/'+result['data']['name']+'/'
            document.getElementById("path2").innerText='D:/upload/'+result['data']['name']+'/'

            if(result['data']['picture']!=null){
                $('#mypicture').attr('src',"http://localhost:8763/user/user/getPicture?uid="+uid);
            }
        },
        error: function (msg) {
            layer.msg("请求用户信息失败")
        }
    })



    //获取各个类别文件个数
    $.ajax({
        url: 'http://localhost:8763/file/file/numOfType?uid='+uid,
        type: 'get',
        headers: {Authorization: token},
        dataType: 'json',
        async: false,
        data: {},
        success: function (result) {
            document.getElementById("pictureNum").innerText=result['data']['picture']
            document.getElementById("wordNum").innerText=result['data']['word']
            document.getElementById("videoNum").innerText=result['data']['video']
            document.getElementById("torrentNum").innerText=result['data']['torrent']
            document.getElementById("musicNum").innerText=result['data']['music']
            document.getElementById("otherNum").innerText=result['data']['other']
        },
        error: function (msg) {
            layer.msg("请求统计文件各个类别个数失败")
        }
    })
}