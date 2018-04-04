$("#loginBtn").click(function() {
	var user = new Object();
	user.loginCode = $.trim($("#loginCode").val());
	user.password = $.trim($("#password").val());
	user.inputCode = $.trim($("#inputCode").val());
	user.isStart = 1;

	if (user.loginCode == "" || user.loginCode == null) {
		$("#loginCode").focus;
		$("#formtip").css("color", "red");
		$("#formtip").html("登录名不能为空");
	} else if (user.password == "" || user.password == null) {
		$("#password").focus;
		$("#formtip").css("color", "red");
		$("#formtip").html("密码不能为空");
	} else if (user.inputCode == "" || user.inputCode == null) {
		$("#inputCode").focus;
		$("#formtip").css("color", "red");
		$("#formtip").html("验证码不能为空");
	} else {
		$("#formtip").html("");

		$.ajax({
			type : 'POST',
			url : 'login.html',
			data : {
				user : JSON.stringify(user)
			},
			dataType : 'html',
			timeout : 1000,
			error : function() {
				$("#formtip").css("color", "red");
				$("#formtip").html("登录失败，请重新尝试！");
			},
			success : function(result) {
				if (result == "success") {
					window.location.href = 'main.html';
				} else if (result == "errorloginCode") {
					$("#formtip").css("color", "red");
					$("#formtip").html("登录名错误，请正确输入！");
				} else if (result == "errorpassword") {
					$("#formtip").css("color", "red");
					$("#formtip").html("密码错误，请正确输入！");
				} else {
					$("#formtip").css("color", "red");
					$("#formtip").html("登录失败，请重新尝试！");
				}
			},
		});
	}
});

function createCode() {
	var code = "";
	var codeLength = 4; // 验证码的长度
	var checkCode = document.getElementById("checkCode");
	var codeChars = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'); // 所有候选组成验证码的字符，当然也可以用中文的
	for ( var i = 0; i < codeLength; i++) {
		var charNum = Math.floor(Math.random() * 52);
		code += codeChars[charNum];
	}
	if (checkCode) {
		checkCode.className = "code";
		checkCode.innerHTML = code;
	}
}
function validateCode() {
	var inputCode = document.getElementById("inputCode").value;
	if (inputCode.length <= 0) {
		alert("请输入验证码！");
	} else if (inputCode.toUpperCase() != code.toUpperCase()) {
		alert("验证码输入有误！");
		createCode();
	} else {
		alert("验证码正确！");
	}
}

window.onload = function() {
	createCode();
}