 1 // 点击全选
 2 var ck = document.getElementById('ck');
 3 var cks =document.getElementsByName('ck');
 4 ck.onclick = function (){
 5     for(var i of cks ){
 6         i.checked = true;
 7     }
 8 }
 9 
10 //更改input的值并且计算总额，调用函数，！important
11 function add(idName){
12     var open = idName.getAttribute('class'); //获取他点击的地方
13     var int = idName.parentNode.childNodes[3].value;//获取点击的int的value值
14     var price = idName.parentNode.parentNode.childNodes[7].innerHTML;//获取单价
15     var sum = idName.parentNode.parentNode.childNodes[11].innerHTML;//获取小计
16     var jf = idName.parentNode.parentNode.childNodes[5].innerHTML;//获取积分
17     var jfz = idName.parentNode.parentNode.childNodes[5].getAttribute('name');//获取购买单件商品的积分值即它的name值
18     var jf2 = document.getElementById('jf2');
19     if(open == 'add'){//判断input的class名字
20         int = parseInt(int) 1;
21     }else if(open == 'minus'){
22         if(int > 1){
23             int = parseInt(int)-1;
24         }
25     }
26     idName.parentNode.childNodes[3].value = int;//重新定义int的value值
27     idName.parentNode.parentNode.childNodes[11].innerHTML = parseInt(price) * parseInt(int);//重新定义总价
28     idName.parentNode.parentNode.childNodes[5].innerHTML = parseInt(int) * parseInt(jfz);    //定义单个商品购买可获得的积分
29 
30 }
31 
32 //价格框失去焦点时
33 var nubs = document.getElementsByName('nub');
34 for(var i of nubs){
35     i.onblur = bl;
36 }
37 function bl(fo){
38     var intv = this.value;                // 获取input框的value值
39     var price = this.parentNode.parentNode.childNodes[7].innerHTML;        //获取商品的单价
40     var jf = this.parentNode.parentNode.childNodes[5].innerHTML;//获取积分
41     var jfz = this.parentNode.parentNode.childNodes[5].getAttribute('name');//获取购买单件商品的积分值即它的name值
42     this.parentNode.parentNode.childNodes[11].innerHTML = parseInt(price) * parseInt(intv);
43     this.parentNode.parentNode.childNodes[5].innerHTML = parseInt(intv) * parseInt(jfz);    //定义单个商品购买可获得的积分
44 }
45 
46 //更改积分和总价
47 var bo = document.getElementsByTagName('body')[0];
48 bo.onload = com;
49 function com(){
50     var zjf = document.getElementById('jf2');//获取总积分
51     var zj = document.getElementById('zj');//获取在总价
52     var jf = document.getElementsByClassName('jf');//获取单件商品的积分
53     var xj = document.getElementsByClassName('xj');//获取单件商品的小计
54     var jfz = 0 ;    //定义所有商品的积分为0
55     var xjz = 0 ;
56     for (var x = 0;x<jf.length;x  ){
57         jfz = jfz   parseInt(jf[x].innerHTML); //循环单件商品的积分并且累加到 jfz 中;
58     }
59     for (var i = 0;i<xj.length;i  ){
60         xjz = xjz   parseInt(xj[i].innerHTML); //循环单件商品的小计并且累加到 xjz 中;
61     }
62     zjf.innerHTML = jfz ; //定义所有商品的总积分
63     zj.innerHTML = xjz ; //定义所有商品的总价
64 }
65 
66 //单个删除商品
67 var dels = document.getElementsByClassName('del');
68 for(var x of dels){
69     x.onclick = del ;
70 }
71 function del(){
72     this.parentNode.parentNode.previousSibling.previousSibling.remove();//previousSibling 上一个兄弟
73     this.parentNode.parentNode.remove();
74 }
75 
76 //多选框打钩的删除
77 var dele = document.getElementById('alldel'); //获取删除所选的按钮
78 dele.onclick = delec ;        //添加点击事件
79 function delec(){
80     var cks = document.getElementsByName('ck');  //获取每个多选框
81     console.log(cks);
82     for (var i=cks.length-1;i>=0;i--){
83         if(cks[i].checked == true){    //循环每个多选框，若他的checked值为true这删除他和她的父亲
84             cks[i].parentNode.parentNode.previousSibling.previousSibling.remove();
85             cks[i].parentNode.parentNode.remove();
86             ck.checked = false;
87         }
88     }
89 }