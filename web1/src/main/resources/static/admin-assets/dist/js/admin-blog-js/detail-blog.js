let titleEl = document.getElementById("title");
let descriptionEl = document.getElementById("description");
let contentEl = document.getElementById("content");
let statusBlogEl = document.getElementById("status");
const saveBlog = document.getElementById("update");




const renderBlog = ()=>{
    titleEl.value = currentBlog.title;
    descriptionEl.value = currentBlog.description;
    easyMDE.value(currentBlog.content);
    contentEl.value = currentBlog.content;
}

// lắng nghe sự kiện của easyMDE
easyMDE.codemirror.on('change', function () {
    contentEl.value = easyMDE.value();
});
let idBlogAdmin = currentBlog.id;

saveBlog.addEventListener('click',(e)=>{
    e.preventDefault();
    if (!$("#form-blog-detail").valid()) return;
    if (contentEl.value===''){
        alert("Vui lòng nhập nội dung");
        return;
    }
    let status = false ;
    if (statusBlogEl.value==="1"){
        status = true;
    }
    const dataBlog = {
        title : titleEl.value,
        description : titleEl.value,
        content : contentEl.value,
        status : status
    }
    console.log(dataBlog);
    axios.put("/api/admin/blogs/"+idBlogAdmin, dataBlog)
        .then((res) => {
            toastr.success("Lưu thành công ");
            setTimeout(()=>{
                window.location.href="/admin/blogs/owns-blog";
            },1500);
        })
        .catch((err) => {
            toastr.error(err.response.data.message);
        })

})

$('#form-blog-detail').validate({
    rules: {
        title: {
            required: true,
        },
        content: {
            required: true,
        },
        description: {
            required: true
        },
    },
    messages: {
        title: {
            required: "Vui lòng nhập tiêu đề blog",

        },
        content: {
            required: "Vui lòng nhập nội dung của blog",

        },
        description: {
            required : "Vui lòng nhập mô tả cho blog"
        }
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
const deleteBlog = document.getElementById("deleteBlog");
deleteBlog.addEventListener('click',()=>{
    const isConfirm = confirm("Bạn có chắc mình muốn xóa blog này không?");
    if (!isConfirm) return;
    axios.delete("/api/admin/blogs/"+idBlogAdmin)
        .then((res) => {
            toastr.success("Xóa Thành Công ");
            setTimeout(()=>{
                window.location.href="/admin/blogs/owns-blog";
            },1500);
        })
        .catch((err) => {
            toastr.error(err.response.data.message);
        })

});
renderBlog();

