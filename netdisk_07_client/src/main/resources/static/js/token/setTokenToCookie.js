//将token存进cookie
function setTokenToCookie(value) {
    var Days = 1; //此 cookie 将被保存 1 天
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = "my_token =" + escape(value) + ";expires=" + exp.toGMTString();
}