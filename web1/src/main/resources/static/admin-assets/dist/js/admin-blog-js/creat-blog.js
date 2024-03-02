
const titleBlogEl = document.getElementById("title");
let contentBlogEl = document.getElementById("content");
const  descriptionBlogEl = document.getElementById("description");
const saveBlog= document.getElementById("save");
const statusBlogEl = document.getElementById("status");
contentBlogEl.classList.add("d-inline-block");
easyMDE.codemirror.on('change', function () {
    contentBlogEl.value = easyMDE.value();
});

// tạo blog
saveBlog.addEventListener('click',(e)=>{
    e.preventDefault();
    if (!$("#form-blog").valid()) return;
    // if (contentBlogEl.value==="")return;
    let status = false ;
    if (statusBlogEl.value==="1"){
        status = true;
    }
    const dataBlog = {
        title : titleBlogEl.value,
        description : descriptionBlogEl.value,
        content : contentBlogEl.value,
        status : status
    }
    console.log(dataBlog);
    axios.post("/api/admin/blogs", dataBlog)
        .then((res) => {
            titleBlogEl.innerText='';
            contentBlogEl.innerText='';
            descriptionBlogEl.innerText='';
            toastr.success("Tạo blog thành công ");
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
        easyMDE: {
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
        easyMDE: {
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



