$('#btn-register').click(function(){
	if($('#register-form').css('display') == 'none'){
		$('#label-register').addClass('active');
		$('#label-login').removeClass('active');
		$('#register-form').toggle(1000);
		$('#login-form').toggle(1000);
	}
});

$('#btn-login').click(function(){
	if($('#login-form').css('display') == 'none'){
		$('#label-login').addClass('active');
		$('#label-register').removeClass('active');
		$('#register-form').toggle(1000);
		$('#login-form').toggle(1000);
	}
});

$().ready(function(){
	$("#register-form").validate({
		rules: {
			username:{
				required:true
			},
			email:{
				required:true,
				email:true
			},
			password:{
				required:true,
				equalTo:"#repassword"
			},
			repassword:{
				required:true
			},	
			messages:{
				username:"File this field",
				email:"File this field",
				password:"File this field",
				repassword:"File this field"
			}
		}
	});
});

$().ready(function(){
	$("#login-form").validate({
		rules: {
			username:{
				required:true
			},
			password:{
				required:true
			}
		}
	});
});