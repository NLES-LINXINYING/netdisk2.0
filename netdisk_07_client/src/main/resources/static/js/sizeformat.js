//大小转换
function sizeformat(size) {
    var result=""
    var flag=0;
    while(size>=1024){
        size /= 1024
        flag += 1
    }
    if(flag==0){
        result += size.toFixed(2) + 'B'
    }else if(flag==1){
        result += size.toFixed(2) + 'KB'
    }else if(flag==2){
        result += size.toFixed(2) + 'MB'
    }else if(flag==3){
        result += size.toFixed(2) + 'GB'
    }else{
        result += "文件异常"
    }

    return result
}