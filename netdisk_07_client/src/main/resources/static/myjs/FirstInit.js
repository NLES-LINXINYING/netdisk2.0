//初始化页面信息（用户，文件）
function firstInit () {

    var token = "Bearer " + getCookie()
    var uid=''

    //获取已使用空间/总空间占比
    $.ajax({
        url: 'http://localhost:8763/user/user/getUserInfo',
        type: 'get',
        headers: {Authorization: token},
        dataType: 'json',
        async: false,
        data: {},
        success: function (result) {
            uid=result['data']['id']


            //字节转MB，1 MB = 1048576 Byte
            var used=(result['data']['usedMemory']/1048576).toFixed(2)
            var total=(result['data']['totalMemory']/1048576).toFixed(2)

            //空间使用情况
            // 基于准备好的dom，初始化echarts实例
            var myChart2 = echarts.init(document.getElementById('main2'));

            // 指定图表的配置项和数据
            var option2 = {
                title: {
                    text: '空间使用情况(单位: MB)',
                    left: 'left',
                    textStyle:{
                        fontWeight:'normal',
                        fontSize:14,
                    }
                },
                //提示信息
                tooltip: {},
                legend: {
                    bottom: 20,
                    left: 'center',
                    data: ['已使用', '未使用']
                },
                color:['red','grey'],
                series: [{
                    type: 'pie',
                    radius: '60%',
                    center: ['50%', '40%'],
                    selectedMode: 'single',
                    minAngle: 0,
                    clockwise: true,
                    data: [
                        {value: used, name: '已使用'},
                        {value: (total-used).toFixed(2), name: '未使用'}
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart2.setOption(option2);
        },
        error: function (msg) {
            layer.msg("请求用户信息失败")
        }
    })


    //获取文件夹个数
    $.ajax({
        url: 'http://localhost:8763/file/folder/numOfFolder?uid='+uid,
        type: 'get',
        headers:
            {
                Authorization: token
            },
        dataType: 'json',
        async: false,
        data: {},
        success: function (result) {
            document.getElementById("numOfFolder").innerText=result['data']
        },
        error: function (msg) {
            layer.msg("请求统计文件夹个数失败")
        }
    })


    //获取文件个数
    $.ajax({
        url: 'http://localhost:8763/file/file/numOfFile?uid='+uid,
        type: 'get',
        headers:
            {
                Authorization: token
            },
        dataType: 'json',
        async: false,
        data: {},
        success: function (result) {
            document.getElementById("numOfFile").innerText=result['data']
        },
        error: function (msg) {
            layer.msg("请求统计文件个数失败")
        }
    })


    //获取各个类别文件空间占比
    $.ajax({
        url: 'http://localhost:8763/file/file/memoryOfType?uid='+uid,
        type: 'get',
        headers:
            {
                Authorization: token
            },
        dataType: 'json',
        async: false,
        data: {},
        success: function (result) {
            var picture=(result['data']['picture']/1048576).toFixed(2)
            var word=(result['data']['word']/1048576).toFixed(2)
            var video=(result['data']['video']/1048576).toFixed(2)
            var torrent=(result['data']['torrent']/1048576).toFixed(2)
            var music=(result['data']['music']/1048576).toFixed(2)
            var other=(result['data']['other']/1048576).toFixed(2)

            //文件使用情况
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '文件明细情况(单位：MB)',
                    left: 'left',
                    textStyle:{
                        fontWeight:'normal',
                        fontSize:14,
                    }
                },
                //提示信息
                tooltip: {},
                legend: {
                    bottom: 20,
                    left: 'center',
                    data: ['图片', '文档', '视频', '种子', '音频', '其他']
                },
                color:['red','orange','yellow','green','blue','grey'],
                series: [{
                    type: 'pie',
                    radius: '60%',
                    center: ['50%', '40%'],
                    selectedMode: 'single',
                    minAngle: 0,
                    clockwise: false,
                    data: [
                        {value: picture, name: '图片'},
                        {value: word, name: '文档'},
                        {value: video, name: '视频'},
                        {value: torrent, name: '种子'},
                        {value: music, name: '音频'},
                        {value: other, name: '其他'},
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        },
        error: function (msg) {
            layer.msg("请求统计各个类别文件空间占比失败")
        }
    })
}