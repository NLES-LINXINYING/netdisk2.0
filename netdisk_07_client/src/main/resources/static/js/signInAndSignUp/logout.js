//退出登录
function logout() {
    deleteCookie()
    //deletePath()
    window.location="http://localhost:8763/netdisk/v1/login"
}