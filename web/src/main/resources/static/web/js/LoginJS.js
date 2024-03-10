const passwordInput = document.getElementById("btnInput");
const btnPass = document.getElementById("btnPassword")
btnPass.addEventListener('click', function() {

    if (passwordInput.getAttribute('type') === 'password') {
        passwordInput.setAttribute('type', 'text');

    } else {
        passwordInput.setAttribute('type', 'password');
    }
})

const email = document.getElementById("inputEmail");
const formLogin = document.getElementById("form-login");

formLogin.addEventListener('submit' ,(e) => {
    e.preventDefault();
    if (!$('#form-login').valid()) return;
    const  data = {
        email : email.value,
        password : passwordInput.value
    }

    // gửi data lên sever sử dụn axios
    axios.post('api/auth/login',data)
        .then((res) => {
            if (res.status === 200){
                toastr.success("Đăng nhập thành công");
                setTimeout(()=>{
                    window.location.href="/";
                },1500)
            }

        })
        .catch((err)=> {
            toastr.error(err.response.data.message);
        })

})



$('#form-login').validate({
    rules: {
        contentEmail: {
            required: true,
            email: true,
        },
        contentPassword: {
            required: true,
        },
    },
    messages: {
        contentEmail: {
            required: "Vui lòng nhập email",
            email: "Email không đúng định dạng"
        },
        contentPassword: {
            required: "Vui lòng nhập mật khẩu",
        },
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});