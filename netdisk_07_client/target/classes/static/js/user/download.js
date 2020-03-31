//目前仅支持文件下载（不包含文件夹）
function downloaddemo(data) {

    if (data != null) {


        var name = data['name']
        var path = data['path']

        console.log(name)
        console.log(path)

        var url = "http://localhost:8763/file/download/downloadFile?name=" + name + "&path=" + path

        let a = document.createElement('a') // 创建a标签
        let e = document.createEvent('MouseEvents') // 创建鼠标事件对象
        e.initEvent('click', false, false) // 初始化事件对象
        a.href = url // 设置下载地址
        a.download = '' // 设置下载文件名
        a.click()

        layer.msg('下载成功', {time: 800})
    }
}



/*//多文件下载
function downloadFiles() {
    //alert(111)
    console.log(document.getElementById("path2").innerText)
    var data=layui.table.checkStatus('ftable').data
    var length=data.length;
    console.log(data)


    for(var i=0;i<length;i++){
        var name=data[i].name
        var path=data[i].path
        var url="/download/downloadFile?name="+name+"&path="+path

        let a = document.createElement('a') // 创建a标签
        let e = document.createEvent('MouseEvents') // 创建鼠标事件对象
        e.initEvent('click', false, false) // 初始化事件对象
        a.href = url // 设置下载地址
        a.download = '' // 设置下载文件名
        a.click()
    }
}


//单文件下载
function downloadFile(name,path){
    var url="/download/downloadFile?name="+name+"&path="+path;
    window.open(url)
}*/