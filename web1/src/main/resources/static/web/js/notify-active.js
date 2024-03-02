toastr.options = {
    "closeButton": false,
    "debug": false,
    "newestOnTop": false,
    "progressBar": false,
    "positionClass": "toast-top-right",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}

// xử lý active menu

const activeMenu = ()=>{
    const pathname = window.location.pathname;
    console.log(pathname)
    const items = document.querySelectorAll(".menu-item");
    items.forEach(item =>{
        // if (item.getAttribute('href') ===pathname){
        //     item.classList.add("active");
        // }
        if (item.getAttribute('href') === pathname){
            item.classList.replace('text-dark','text-success')
        }else {
            item.classList.replace('text-success','text-dark')
        }
    })

}
activeMenu()

