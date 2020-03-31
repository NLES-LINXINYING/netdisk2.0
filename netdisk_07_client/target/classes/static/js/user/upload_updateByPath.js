// 点击文件夹
// 更新头部显示的路径
// 更新表格内容
function clickfolder(ffname) {

    var token = "Bearer " + getCookie()
    var uid = document.getElementById("uid").innerText;
    var path = sessionStorage.getItem('uploadPath')
    console.log(path)

    var curpath = path + "" + ffname + "/"
    console.log(curpath)


    // 更新头部显示的路径
    updateCurpath(ffname)

    //更新sessionStorge中当前路径
    sessionStorage.setItem('uploadPath',curpath)


    //重载数据
    layui.use('table', function () {
        var table = layui.table
        table.reload('ftable', {
            url: 'http://localhost:8763/file/all/findByPath?uid=' + uid + "&path=" + curpath
            , method: 'get'
            , headers: {Authorization: token}
        });
    });
}


//点击文件夹，更新路径
//path记录当前路径，ffname为点击的文件夹
function updateCurpath(ffname) {
    var curpath = document.getElementById('curpath')

    var a = document.createElement('a')
    var p2 = document.createElement('p')

    a.style.marginLeft = '10px'
    a.innerText = ffname
    //todo,点击应该更新表格
    //a.onclick=clickfolder(ffname)

    p2.style.marginLeft = '10px'
    p2.innerText = '/'

    curpath.append(a)
    curpath.append(p2)
}


// 返回上一级目录
// 更新头部显示路径
// 更新表格内容
function back() {
    var token = "Bearer " + getCookie()
    var uid=document.getElementById('uid').innerText
    var curpath = sessionStorage.getItem('uploadPath')
    console.log(curpath)
    var syspath = document.getElementById('path1').innerText

    //console.log(curpath)
    // console.log(syspath)

    //如果当前目录已是根目录
    if (curpath == syspath) {
        layer.msg('当前为根目录')
    } else {
        // 更新头部显示路径
        updateCurpath2()


        //更新cookie中当前路径
        var curpath2 = ""
        var strs = curpath.split('/')
        for (var i = 0; i < strs.length - 2; i++) {
            curpath2 += strs[i] + '/'
        }
        console.log(curpath2)
        sessionStorage.setItem('uploadPath',curpath2)


        //更新表格内容
        layui.use('table', function () {
            var table = layui.table
            table.reload('ftable', {
                url: 'http://localhost:8763/file/all/findByPath?uid=' + uid + "&path=" + curpath2,
                method: 'get',
                headers: {Authorization: token}
            });
        });
    }


}


function updateCurpath2() {
    var curpath = document.getElementById('curpath')

    var alist = document.getElementById('curpath').getElementsByTagName('a')
    var plist = document.getElementById('curpath').getElementsByTagName('p')

    var a = document.getElementById('curpath').getElementsByTagName('a')[alist.length - 1]
    var p = document.getElementById('curpath').getElementsByTagName('p')[plist.length - 1]

    curpath.removeChild(p)
    curpath.removeChild(a)
}
