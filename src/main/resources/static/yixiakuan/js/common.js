// 验证手机号
function isPhoneNo(phone) {
    var pattern = /^1[34578]\d{9}$/;
    return pattern.test(phone);
}

// 验证身份证
function isCardNo(card) {
    var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    return pattern.test(card);
}

//验证银行卡
function checkbankcard(bankcard) {
    var account = bankcard;
    if (account == "" || account.length < 16 || account.length > 19) {
        layer.open({
            style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
            content: '银行卡号数必须在16到19之间',
            shade: false,
            time: 1
        })
        $('#yhk').focus();
        return false
    }
    var num = /^\d*$/;  //全数字
    if (!num.exec(account)) {
        layer.open({
            style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
            content: '银行卡号必须全为数字',
            shade: false,
            time: 1
        })
        $('#yhk').focus();
        return false
    }
//开头6位
    var strBin = "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
    if (strBin.indexOf(account.substring(0, 2)) == -1) {
        layer.open({
            style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
            content: '银行卡号开头6位不符合规范',
            shade: false,
            time: 1
        })
        $('#yhk').focus();
        return false;
    }
    var lastNum = account.substr(account.length - 1, 1);//取出最后一位（与luhm进行比较）


    var first15Num = account.substr(0, account.length - 1);//前15或18位
    var newArr = new Array();
    for (var i = first15Num.length - 1; i > -1; i--) {    //前15或18位倒序存进数组
        newArr.push(first15Num.substr(i, 1));
    }
    var arrJiShu = new Array();  //奇数位*2的积 <9
    var arrJiShu2 = new Array(); //奇数位*2的积 >9

    var arrOuShu = new Array();  //偶数位数组
    for (var j = 0; j < newArr.length; j++) {
        if ((j + 1) % 2 == 1) {//奇数位
            if (parseInt(newArr[j]) * 2 < 9)
                arrJiShu.push(parseInt(newArr[j]) * 2);
            else
                arrJiShu2.push(parseInt(newArr[j]) * 2);
        }
        else //偶数位
            arrOuShu.push(newArr[j]);
    }

    var jishu_child1 = new Array();//奇数位*2 >9 的分割之后的数组个位数
    var jishu_child2 = new Array();//奇数位*2 >9 的分割之后的数组十位数
    for (var h = 0; h < arrJiShu2.length; h++) {
        jishu_child1.push(parseInt(arrJiShu2[h]) % 10);
        jishu_child2.push(parseInt(arrJiShu2[h]) / 10);
    }

    var sumJiShu = 0; //奇数位*2 < 9 的数组之和
    var sumOuShu = 0; //偶数位数组之和
    var sumJiShuChild1 = 0; //奇数位*2 >9 的分割之后的数组个位数之和
    var sumJiShuChild2 = 0; //奇数位*2 >9 的分割之后的数组十位数之和
    var sumTotal = 0;
    for (var m = 0; m < arrJiShu.length; m++) {
        sumJiShu = sumJiShu + parseInt(arrJiShu[m]);
    }

    for (var n = 0; n < arrOuShu.length; n++) {
        sumOuShu = sumOuShu + parseInt(arrOuShu[n]);
    }

    for (var p = 0; p < jishu_child1.length; p++) {
        sumJiShuChild1 = sumJiShuChild1 + parseInt(jishu_child1[p]);
        sumJiShuChild2 = sumJiShuChild2 + parseInt(jishu_child2[p]);
    }
    //计算总和
    sumTotal = parseInt(sumJiShu) + parseInt(sumOuShu) + parseInt(sumJiShuChild1) + parseInt(sumJiShuChild2);

    //计算Luhm值
    var k = parseInt(sumTotal) % 10 == 0 ? 10 : parseInt(sumTotal) % 10;
    var luhm = 10 - k;

    //Luhm验证通过
    if (lastNum == luhm) {
        alert("验证通过");
        return true
    }
    else {
        //银行卡号必须符合Luhm校验
        layer.open({
            style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
            content: '银行卡号有误',
            shade: false,
            time: 1
        })
        $('#yhk').focus();
        return false;
    }
    alert("a")
}

function check() {
    var username = $("#username").val();
    var card = $("#sfz").val();
    var phone = $("#phone").val();
    var bankcard = $("#yhk").val();
    if (username == '') {
        layer.open({
            style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
            content: '请输入您的姓名',
            shade: false,
            time: 1
        })
        $('#username').focus();
        return false;
    }
    if (card == '') {
        layer.open({
            style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
            content: '请输入您的身份证号码',
            shade: false,
            time: 1
        })
        $('#sfz').focus();
        return false;
    } else {
        if (isCardNo(card) == false) {
            layer.open({
                style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
                content: '身份证号不正确',
                shade: false,
                time: 1
            })
            $('#sfz').focus();
            return false;
        }

    }
    if (phone == '') {
        layer.open({
            style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
            content: '请输入您的手机号码',
            shade: false,
            time: 1
        })
        $('#phone').focus();
        return false;
    } else {
        if (isPhoneNo(phone) == false) {
            layer.open({
                style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
                content: '手机号码不合法',
                shade: false,
                time: 1
            })
            $('#phone').focus();
            return false;
        }

    }
    if(checkbankcard(bankcard)){
        return true;
    }else{
        return false;
    }


}

$(function () {
    //首页第一个电话部分随机
    var a = Math.floor(Math.random() * 10);
    var num1 = [];
    for (var i = 0; i < 5; i++) {
        num1[i] = Math.floor(Math.random() * 9000 + 999);
        for (var j = 0; j < i; j++) {
            if (num1[i] == num1[j]) {
                i--;
            }
            var b = num1[i]
        }
    }
    $('#ind-sp1').text(a);
    $('#ind-sp2').text(b);
    //首页点击提交信息
    $('#tijiao').on('click', function () {
        if (check()) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                data: {
                    username: $("#username").val(),
                    sfz: $("#sfz").val(),
                    phone: $("#phone").val(),
                    bankcard: $("#yhk").val(),
                },
                cache: false,
                url: '/index/test',
                success: function (data) {
                    if (data.code == 1) {
                        layer.open({
                            style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
                            content: '提交成功',
                            shade: false,
                            time: 1
                        })
                        window.location.href = "test.html";
                    } else {
                        layer.open({
                            style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
                            content: data,
                            shade: false,
                            time: 1
                        })
                    }
                },
                error: function () {
                    layer.open({
                        style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
                        content: '网络异常',
                        shade: false,
                        time: 1
                    })
                    window.location.href = "test.html";
                }
            });
        }
    })
})