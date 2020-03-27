//文件名变色
function mouseenter(str) {
    var name = document.getElementById(str);
    name.style.color = '#17C4E2';
}
function mouseleave(str) {
    var name = document.getElementById(str);
    name.style.color = 'grey';
}