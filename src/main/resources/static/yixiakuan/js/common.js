var url = 'http://127.0.0.1/';
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
    var ac = account.length
    if (account == "" || ac < 16 || ac > 19) {
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
        var name = $("#username").val();
        var idCard = $("#sfz").val();
        var phone = $("#phone").val();
        var bankCard = $("#yhk").val();
        var money = GetQueryString('num');
        $$.ajax({
            url:url + 'customer/bankCard/validation',
            type:'get',
            data: {
                name:name,
                idCard: idCard,
                phone: phone,
                bankCard: bankCard
            },
            success:function(result){
                console.log('aaa````````````````');
                console.log(result.data);
                if (result.statusCode == 200) {
                    $$.ajax({
                        url:url + 'customer',
                        type:'post',
                        data: {
                            money:money,
                            name:name,
                            idCard: idCard,
                            phone: phone,
                            bankCard: bankCard
                        },
                        success:function(result){
                            console.log('aaa````````````````');
                            console.log(result.data);
                            if (result.statusCode == 200) {
                                layer.open({
                                    style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
                                    content: '提交成功',
                                    shade: false,
                                    time: 2
                                })

                                window.location.href = "pay.html?realName="+encodeURI(name)+"&idCard="+idCard+"&phone="+phone+"&bankCard="+bankCard;
                            } else {
                                layer.open({
                                    style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
                                    content: result.errorMsg,
                                    shade: false,
                                    time: 2
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
                            //window.location.href = "test.html";
                        }
                    });
                    //window.location.href = "pay.html?realName="+encodeURI(realName)+"&idCard="+idCard+"&phone="+phone;
                } else {
                    layer.open({
                        style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
                        content: result.errorMsg,
                        shade: false,
                        time: 2
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
            }
        });
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

}

function check() {
    var username = $("#username").val();
    var card = $("#sfz").val();
    var phone = $("#phone").val();
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

    return true;

}

//首页点击提交信息
$(function () {
    $('#tijiao').on('click', function () {
        if (check()) {
            var realName = $("#username").val()
            var idCard = $("#sfz").val()
            var phone = $("#phone").val()
            window.location.href = "test.html?realName="+encodeURI(realName)+"&idCard="+idCard+"&phone="+phone;
            //$$.ajax({
            //    url:url + 'customer/idCard/validation',
            //    type:'get',
            //    data: {
            //        realName:realName,
            //        idCard: idCard
            //    },
            //    success:function(result){
            //        console.log('aaa````````````````');
            //        console.log(result.data);
            //        if (result.statusCode == 200) {
            //            layer.open({
            //                style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
            //                content: '提交成功',
            //                shade: false,
            //                time: 2
            //            })
            //
            //            window.location.href = "test.html?realName="+encodeURI(realName)+"&idCard="+idCard+"&phone="+phone;
            //        } else {
            //            layer.open({
            //                style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
            //                content: result.errorMsg,
            //                shade: false,
            //                time: 2
            //            })
            //        }
            //    },
            //    error: function () {
            //        layer.open({
            //            style: 'border:none; background-color: rgba(0,0,0,.8); color:#fff;font-size:.28rem;',
            //            content: '网络异常',
            //            shade: false,
            //            time: 1
            //        })
            //        //window.location.href = "test.html";
            //    }
            //});

        }
    })
    //保存数据、跳转
    $('#yufu').on('click',function(){
        var bankcard = $("#yhk").val();
        checkbankcard(bankcard);
    })
})

//获取url参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);  //获取url中"?"符后的字符串并正则匹配
    var context = "";
    if (r != null)
        context = r[2];
    reg = null;
    r = null;
    return context == null || context == "" || context == "undefined" ? "" : context;
}

//带参数跳转
function gourl(url){
    var num = GetQueryString('num');
    var realName = GetQueryString("realName");
    var idCard = GetQueryString("idCard");
    var phone = GetQueryString("phone");
    window.location.href = url + "?realName="+realName+"&idCard="+idCard+"&phone="+phone+"&num="+num;
}

var $$={
    /*传递参数对象，返回拼接之后的字符串*/
    /*{‘name’:’jack,’age’:20}=>  name=jack&age=20&*/
    getParmeter:function(data){
        var result="";
        for(var key in data){
            result=result+key+"="+data[key]+"&";
        }
        /*将结果最后多余的&截取掉*/
        return result.slice(0,-1);
    },
    /*实现ajax请求*/
    ajax:function(obj){
        /*1.判断有没有传递参数，同时参数是否是一个对象*/
        if(obj==null || typeof obj!="object"){
            return false;
        }
        /*2.获取请求类型,如果没有传递请求方式，那么默认为get*/
        var type=obj.type || 'get';
        /*3.获取请求的url  location.pathname:就是指当前请求发起的路径*/
        var url=obj.url || location.pathname;
        /*4.获取请求传递的参数*/
        var data=obj.data || {};
        /*4.1获取拼接之后的参数*/
        data=this.getParmeter(data);
        /*5.获取请求传递的回调函数*/
        var success=obj.success || function(){};

        /*6:开始发起异步请求*/
        /*6.1:创建异步对象*/
        var xhr=new XMLHttpRequest();
        /*6.2:设置请求行,判断请求类型，以此决定是否需要拼接参数到url*/
        if(type=='get'){
            url=url+"?"+data;
            /*重置参数，为post请求简化处理*/
            data=null;
        }
        xhr.open(type,url);
        /*6.2:设置请求头:判断请求方式，如果是post则进行设置*/
        if(type=="post"){
            xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        }
        /*6.3:设置请求体,post请求则需要传递参数*/
        xhr.send(data);

        /*7.处理响应*/
        xhr.onreadystatechange=function(){
            /*8.判断响应是否成功*/
            if(xhr.status==200 && xhr.readyState==4){
                /*客户端可用的响应结果*/
                var result=null;
                /*9.获取响应头Content-Type ---类型是字符串*/
                var grc=xhr.getResponseHeader("Content-Type");
                /*10.根据Content-Type类型来判断如何进行解析*/
                if(grc.indexOf("json") != -1){
                    /*转换为js对象*/
                    result=JSON.parse(xhr.responseText);
                }
                else if(grc.indexOf("xml") != -1){
                    result=xhr.responseXML;
                }
                else{
                    result=xhr.responseText;
                }
                /*11.拿到数据，调用客户端传递过来的回调函数*/
                success(result);
            }
        }

    }
};