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


function downloaddemo() {
    var data=layui.table.checkStatus('ftable').data
    var length=data.length
    console.log(data)


    if(length>0){
        var flag=0
        for(var i=0;i<length;i++){
            if(data[i].type==undefined){
                flag=1
                break
            }
        }

        if(flag==1){
            layer.msg('暂不支持文件夹下载',{time:1000})
        }else{
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
            layer.msg('下载成功',{time:1000})
            /*setTimeout(function () {
                window.location.reload()
            },5000)*/
        }
    }else{
        layer.msg('请选择文件',{time:1000})
    }
    //window.parent.location.reload()
}