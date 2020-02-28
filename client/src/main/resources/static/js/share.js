//分享
function sharefunc() {
    var table=layui.table
    var uid=document.getElementById("uid1").innerText
    var data=layui.table.checkStatus('ftable').data
    var length=data.length;

    if(length>0){
        var fid=new Array();
        var ffid=new Array();
        var fi=0;
        var ffi=0;
        for(var i=0;i<length;i++){
            if(data[i].type != undefined){
                fid[fi++]=data[i].id
            }else{
                ffid[ffi++]=data[i].id
            }
        }

        layer.open({
            type: 2,
            title: '分享文件',
            shade: false,
            area: ['400px', '300px'],
            maxmin: false,
            scrollbar: false,
            content: "/addShare?uid=" + uid+"&ffid="+ffid+"&fid="+fid,
            zIndex: layer.zIndex, //重点1
            success: function (layero) {
                layer.setTop(layero); //重点2
            }
        });
    }else{
        layer.msg("请选择文件",{time:800})
    }
}