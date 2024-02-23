const inputPass = document.getElementById("btnInput")
const btnPass = document.getElementById("btnPassword")
btnPass.addEventListener('click', function() {
    if (inputPass.getAttribute('type') === 'password') {
        inputPass.setAttribute('type', 'text');

    } else {
        inputPass.setAttribute('type', 'password');
    }
})
const inputPassCF = document.getElementById("btnInputConfirm")
const btnPassCF = document.getElementById("btnPasswordConfirm")
btnPassCF.addEventListener('click', function() {
    if (inputPassCF.getAttribute('type') === 'password') {
        inputPassCF.setAttribute('type', 'text');

    } else {
        inputPassCF.setAttribute('type', 'password');
    }
})


const submitFormCreate = document.getElementById("btnSubmit");
submitFormCreate.addEventListener("click" , ()=>{
    let inputPassword = document.getElementById("btnInput").value;
    let inputPassConfirm = document.getElementById("btnInputConfirm").value;
    let message = document.getElementById("message");
    if (inputPassword.length !==0){
        if (inputPassword===inputPassConfirm){
            message.innerText = "Mật khẩu trùng khớp "
         }else {
            message.innerText = "Mật khẩu không trùng khớp "
        }
    }
})


