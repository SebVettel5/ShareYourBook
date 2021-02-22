/*
	Strongly Typed by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
*/

(function($) {

	var	$window = $(window),
		$body = $('body');

	// Breakpoints.
		breakpoints({
			xlarge:  [ '1281px',  '1680px' ],
			large:   [ '981px',   '1280px' ],
			medium:  [ '737px',   '980px'  ],
			small:   [ null,      '736px'  ]
		});

	// Play initial animations on page load.
		$window.on('load', function() {
			window.setTimeout(function() {
				$body.removeClass('is-preload');
			}, 100);
		});

	// Dropdowns.
		$('#nav > ul').dropotron({
			mode: 'fade',
			noOpenerFade: true,
			hoverDelay: 150,
			hideDelay: 350
		});

	// Nav.

		// Title Bar.
			$(
				'<div id="titleBar">' +
					'<a href="#navPanel" class="toggle"></a>' +
				'</div>'
			)
				.appendTo($body);

		// Panel.
			$(
				'<div id="navPanel">' +
					'<nav>' +
						$('#nav').navList() +
					'</nav>' +
				'</div>'
			)
				.appendTo($body)
				.panel({
					delay: 500,
					hideOnClick: true,
					hideOnSwipe: true,
					resetScroll: true,
					resetForms: true,
					side: 'left',
					target: $body,
					visibleClass: 'navPanel-visible'
				});

})(jQuery);

	var flag = false;
			window.onload=function(){
			// 获取弹窗
			var lSpan = document.getElementById('logSpan');
			
			// 打开弹窗的按钮对象
			var btn = document.getElementById("loginButton");
			
			// 获取 <span> 元素，用于关闭弹窗 that closes the modal
			var span = document.getElementsByClassName("close")[0];
			
			//获取验证码<div>,用于隐藏这个div
			var checkarea = document.getElementById("checkcodearea");
			
			//获取弹窗面板的登录按钮
			var lgbt = document.getElementById("lgbt");
			
			// 点击按钮打开弹窗
			btn.onclick =  function(){
			    lSpan.style.display = "block";
			}
			
			// 点击 <span> (x), 关闭弹窗
			span.onclick = function() {
			    lSpan.style.display = "none";
			    flag=false;
			}
			
//			 //在用户点击其他地方时，关闭弹窗
//			window.onclick = function(event) {
//			    if (event.target == lSpan) {
//			        lSpan.style.display = "none";
//			    }
//			}
			lgbt.onclick = function(){
				
			var usercount = document.getElementById("UserCount").value;
			var userpassword = document.getElementById("UserPassword").value;
			var ucountlen = usercount.length;
			var checkcode = document.getElementById("checkcode");
			if(flag){
				lgbt.type ="submit";
			}
			else{
				alert(ucountlen);
				//判断
				switch(ucountlen){
					case 11:alert("读者");lgbt.type="submit";break;
					case 7:alert("机构-----我们将向您的密保邮箱发送一封验证码，注意查收");show();break;
					case 5:alert("管理员-----我们将向您的密保邮箱发送一封验证码，注意查收");show();break;
					default:alert("错误");break;
				}
			}
			
		}
			//点击登录按钮之后出现验证码框
			function show(){
				var checkarea = document.getElementById("checkcodearea");
				checkarea.style.display = "flex";
				var lgbt = document.getElementById("lgbt");
				flag=true;
			}		
		}
			
