let passwordUserEl = document.querySelector("#btnInput");
let btnPass = document.getElementById("btnPassword");
btnPass.addEventListener('click', function() {
    if (passwordUserEl.getAttribute('type') === 'password') {
        passwordUserEl.setAttribute('type', 'text');

    } else {
        passwordUserEl.setAttribute('type', 'password');
    }
})
let inputPassCF = document.getElementById("btnInputConfirm")
let btnPassCF = document.getElementById("btnPasswordConfirm")
btnPassCF.addEventListener('click', function() {
    if (inputPassCF.getAttribute('type') === 'password') {
        inputPassCF.setAttribute('type', 'text');
    } else {
        inputPassCF.setAttribute('type', 'password');
    }
})
let submitRegister  = document.getElementById("btn-register");
let nameUserEl = document.querySelector("#nameUser");
let emailUserEl = document.querySelector("#emailUser");

submitRegister.addEventListener('click' , (e)=>{
    e.preventDefault()
    if (!$('#register').valid()) return;

    let  data = {
        email : emailUserEl.value,
        password : passwordUserEl.value,
        name : nameUserEl.value
    }
    console.log(data)
    // gọi api đẩy dữ liệu lên server
    axios.post('api/regist',data)
        .then((res) => {
            if (res.status === 200){
                toastr.success("Tạo tài khoản thành công");
                setTimeout(()=>{
                    window.location.href="/log-in";
                },1500)
            }

        })
        .catch((err)=> {
            toastr.error(err.response.data.message);
        })

})
$('#register').validate({
    rules: {
        nameUser : {
            required:true,
        },
        contentEmail: {
            required: true,
            email: true,
        },
        contentPassword: {
            required: true,
        },
        contentPasswordConfirm: {
            required: true,
            equalTo: "#btnInput"
        }

    },
    messages: {
        nameUser:{
            required: "Vui lòng nhập tên của bạn"
        },
        contentEmail: {
            required: "Vui lòng nhập email",
            email: "Email không đúng định dạng"
        },
        contentPassword: {
            required: "Vui lòng nhập mật khẩu",
        },
        contentPasswordConfirm: {
            required: "Vui lòng nhập mật khẩu",
            equalTo: "Mật khẩu xác nhận không khớp"
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