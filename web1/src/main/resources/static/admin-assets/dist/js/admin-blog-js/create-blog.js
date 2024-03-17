
const titleBlogEl = document.getElementById("title");
let contentBlogEl = document.getElementById("content");
const  descriptionBlogEl = document.getElementById("description");
const saveBlog= document.getElementById("save");
const statusBlogEl = document.getElementById("status");
// quản lý ảnh
easyMDE.codemirror.on('change', function () {
    contentBlogEl.value = easyMDE.value();
});
// tạo blog
saveBlog.addEventListener('click',(e)=>{
    e.preventDefault();
    if (!$("#form-blog").valid()) return;
    if (contentBlogEl.value===''){
        alert("Vui lòng nhập nội dung");
        return;
    }
    let status = false ;
    if (statusBlogEl.value==="1"){
        status = true;
    }
    const  srcImage = thumbnailPreview.getAttribute("src");
    console.log(srcImage);
    if (srcImage===""){
        toastr.error("Vui lòng chọn hình ảnh cho blog");
        return;
    }
    const dataBlog = {
        title : titleBlogEl.value,
        description : descriptionBlogEl.value,
        content : contentBlogEl.value,
        thumbnail: srcImage,
        status : status
    }
    axios.post("/api/admin/blogs", dataBlog)
        .then((res) => {
            const  id = res.data.id;
            toastr.success("Tạo blog thành công ");
            setTimeout(()=>{
                window.location.href="/admin/blogs/"+id+"/detail-blog";
            },1500);
        })
        .catch((err) => {
            toastr.error(err.response.data.message);
        })

})

$('#form-blog').validate({
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



