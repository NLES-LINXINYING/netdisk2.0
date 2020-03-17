function search() {
    var name=document.getElementById("search1").value
    var uid=getUid()

    console.log(name)

    //数据重载
    table.reload('file', {
        url: '/search/searchByName'
        ,where: { //设定异步数据接口的额外参数，任意设
            uid: uid
            ,name: name
        }
    });
}