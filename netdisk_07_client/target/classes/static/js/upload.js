//单文件上传
layui.use('upload', function () {
    var upload = layui.upload;
    var uid = document.getElementById("uid1").innerText
    var path = document.getElementById("path2").innerText
    //console.log(path)

    //上传文件
    var uploadInst = upload.render({
        elem: '#uploadfile' //绑定元素
        , url: '/upload/uploadFile/' //上传接口
        , accept: 'file' //允许上传的文件类型
        , data: {
            uid: uid,
            path: path
        }
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            //上传完毕回调
            if (res.code == 0) {
                layer.alert("上传成功！！！", {
                    icon: 1,
                    title: "提示",
                    btn: ['确认']
                }, function () {
                    location.reload()
                });
            } else {
                layer.alert("上传失败!！！", {
                    icon: 5,
                    title: "提示",
                    btn: ['确认']
                }, function () {
                    location.reload()
                });
            }
            layer.closeAll('loading');
        }
        , error: function () {
            //请求异常回调
            layer.alert("请求异常!！！", {
                icon: 5,
                title: "提示",
                btn: ['确认']
            }, function () {
                location.reload()
            });
            layer.closeAll('loading')
        }
    });
});


//文件夹上传
function uploadfolder() {
    var uid = document.getElementById("uid1").innerText
    var path = document.getElementById("path2").innerText

    document.getElementById("folder").click();
    document.getElementById("folder").onchange = function (e) {
        var file = $("#folder").val()
        var filesize = 0;
        var formData = new FormData()
        var uploadfiles = []
        if (file != undefined) {
            //console.log("ajax")
            var files = e.target.files;
            var test=e.target.folders;

            console.log(test)

            var fcount = files.length;
            for (var i = 0; i < files.length; i++) {
                //console.log(files[i])
                formData.append('uploadFiles', files[i])
            }
            //其它参数
            formData.append('uid', uid)
            formData.append('path', path)

            $.ajax({
                url: '/upload/uploadFolder',
                type: 'POST',
                cache: false, //上传文件不需要缓存
                data: formData,
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                before: function () {
                    layer.load(); //上传loading
                },
                success: function (data) {
                    if (data.code == 0) {
                        /*layer.alert("上传成功！！！", {
                            icon: 1,
                            title: "提示"
                        });*/
                        alert("上传成功!！！")
                    } else {
                        /*layer.alert("上传失败!！！", {
                            icon: 5,
                            title: "提示"
                        });*/
                        alert("上传失败!！！")
                    }
                    layer.closeAll('loading');
                    window.location.reload();
                },
                error: function (data) {
                    /*layer.alert("请求异常!！！", {
                        icon: 5,
                        title: "提示"
                    });*/
                    alert("请求异常!！！")
                    layer.closeAll('loading');
                    window.location.reload();
                }
            })

        }
    };
}
