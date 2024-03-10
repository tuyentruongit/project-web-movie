let titleEl = document.getElementById("title");
let descriptionEl = document.getElementById("description");
let contentEl = document.getElementById("content");
let statusBlogEl = document.getElementById("status");
const saveBlog = document.getElementById("update");
// quản lý ảnh
const imageContainer = document.querySelector(".image-container");
const btnChoseImage = document.getElementById("btn-chose-image");
const btnDeleteImage = document.getElementById("btn-delete-image");
const inputImage = document.getElementById("avatar");
const thumbnailPreview = document.getElementById("thumbnail");


const renderBlog = () => {
    titleEl.value = currentBlog.title;
    descriptionEl.value = currentBlog.description;
    easyMDE.value(currentBlog.content);
    thumbnailPreview.src=currentBlog.thumbnail;
    contentEl.value = currentBlog.content;

}

// lắng nghe sự kiện của easyMDE
easyMDE.codemirror.on('change', function () {
    contentEl.value = easyMDE.value();
});
let idBlogAdmin = currentBlog.id;

saveBlog.addEventListener('click', (e) => {
    e.preventDefault();
    if (!$("#form-blog-detail").valid()) return;
    if (contentEl.value === '') {
        alert("Vui lòng nhập nội dung");
        return;
    }
    let status = false;
    if (statusBlogEl.value === "1") {
        status = true;
    }
    const dataBlog = {
        title: titleEl.value,
        description: descriptionEl.value,
        content: contentEl.value,
        thumbnail : thumbnailPreview.getAttribute("src"),
        status: status
    }
    axios.put("/api/admin/blogs/" + idBlogAdmin, dataBlog)
        .then((res) => {
            toastr.success("Lưu thành công ");
            setTimeout(() => {
                window.location.href = "/admin/blogs/owns-blog";
            }, 1500);
        })
        .catch((err) => {
            toastr.error(err.response.data.message);
        })

})

$('#form-blog-detail').validate({
    rules: {
        title: {
            required: true,
        }, content: {
            required: true,
        }, description: {
            required: true
        },
    }, messages: {
        title: {
            required: "Vui lòng nhập tiêu đề blog",

        }, content: {
            required: "Vui lòng nhập nội dung của blog",

        }, description: {
            required: "Vui lòng nhập mô tả cho blog"
        }
    }, errorElement: 'span', errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    }, highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    }, unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});
const deleteBlog = document.getElementById("deleteBlog");
deleteBlog.addEventListener('click', () => {
    const isConfirm = confirm("Bạn có chắc mình muốn xóa blog này không?");
    if (!isConfirm) return;
    axios.delete("/api/admin/blogs/" + idBlogAdmin)
        .then((res) => {
            toastr.success("Xóa Thành Công ");
            setTimeout(() => {
                window.location.href = "/admin/blogs/owns-blog";
            }, 1500);
        })
        .catch((err) => {
            toastr.error(err.response.data.message);
        })

});




// hiển thị danh sách ảnh
const renderImage = images => {
    let htmlImage = "";
    images.forEach(image => {
        htmlImage += `<div class="image-item" onclick="choseImage(this)"  data-id="${image.id}">
                                <img src="${image.url}"
                                     alt="">
                       </div>`;
    });
    imageContainer.innerHTML = htmlImage;
}
inputImage.addEventListener('change' ,(e)=>{
    // lấy ra thông tin file mà người ta upload
    const file = e.target.files[0];
    // tạo đối tượng
    const formData = new FormData();
    formData.append("file",file);
    axios.post("/api/admin/images",formData)
        .then((res)=>{
            imageList.unshift(res.data);
            renderImage(imageList);
            renderPagination(imageList);
            toastr.success("Upload ảnh thành công ");

        })
        .catch((err)=>{
            console.log(err);
        })



})
const choseImage = (el) =>{
    const selectedEl = document.querySelector(".image-item.selected");
    if (selectedEl){
        selectedEl.classList.remove("selected");
    }
    el.classList.add("selected")
    btnChoseImage.disabled=false;
    btnDeleteImage.disabled = false;

}
// chọn ảnh
btnChoseImage.addEventListener('click',()=>{
    const selectedEl = document.querySelector(".image-item.selected");
    if (!selectedEl){
        toastr.error("Vui lòng chọn ảnh ");
    }
    const urlImage= selectedEl.querySelector("img").getAttribute("src");
    // thumbnailPreview.setAttribute("src",urlImage);
    thumbnailPreview.src = urlImage;
    $('#modal-image').modal('hide');

})
// xóa ảnh
btnDeleteImage.addEventListener('click',()=>{
    const selectedEl = document.querySelector(".image-item.selected");
    if (!selectedEl){
        toastr.error("Vui lòng chọn ảnh cần xóa ");
    }
    const imageId= selectedEl.getAttribute("data-id");
    axios.delete(`/api/admin/images/${imageId}`)
        .then((res)=>{

           imageList = imageList.filter(image => image.id!==imageId);
           renderImage(imageList)
            renderPagination(imageList);
            btnChoseImage.disabled=true;
            btnDeleteImage.disabled = true;
            toastr.success("Xóa ảnh thành công ");

        })
        .catch((err)=>{
            console.log(err);
        })

})
const  renderPagination = (images) =>{
    $('#image-pagination').pagination({
        dataSource:images,
        pageSize: 12,
        pageRange: null,
        showPageNumbers: true,
        callback: function(data, pagination) {
        renderImage(data);
    }
    })
}


renderImage(imageList)
renderPagination(imageList);
renderBlog();