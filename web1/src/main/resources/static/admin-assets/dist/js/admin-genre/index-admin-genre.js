const btnCreateGenre =document.getElementById("btn-create-genre");
const btnUpdateGenre =document.querySelectorAll(".btn-success");
const btnDeleteGenre =document.querySelectorAll(".btn-danger");
let titleModal = document.getElementById("modal-title");
let nameGenre = document.getElementById("name-genre");
let btnSaveGenre = document.getElementById("save-genre");
let idGenre = null;


btnSaveGenre.addEventListener('click',()=>{
    if (idGenre==null){
        functionCreateGenre();
        return;
    }else {
        functionUpdateGenre(idGenre)
        return;
    }
})



const uploadGenre = (idge)=>{
    idGenre = parseInt(btnUpdateGenre.value);
    const currentGenre = genreList.find(gen => gen.id===id);
    nameGenre.value= currentGenre.name;
    $('#modal-genre').modal('show')
    titleModal.textContent="Chỉnh sửa thể loại";
}

btnCreateGenre.addEventListener('click' ,() =>{
    $('#modal-genre').modal('show')
    titleModal.textContent="Tạo thể loại mới";
    nameGenre.value='';
} )




const functionCreateGenre = ()=>{
    const data={
        name:nameGenre.value
    }
    axios.post("/api/admin/genre",data)
        .then((res)=>{
            toastr.success("Lưu thành công ");
            $('#modal-genre').modal('hidden')
            setTimeout(()=>{
                window.location.reload()
            },1500)
        })
        .catch(()=>{
            toastr.error("Thể loại đã tồn tại  ");
            $('#modal-genre').modal('hidden')
        })
}
const  functionUpdateGenre = (idGe)=>{
    const data={
        name:nameGenre.value
    }
    axios.put(`/api/admin/genre/update/${id}`,data)
        .then((res)=>{
            toastr.success("Lưu thành công ");
            $('#modal-genre').modal('hidden')
            idGenre=null;
            setTimeout(()=>{
                window.location.reload()
            },1500)
        })
        .catch(()=>{
            toastr.error("Thể loại đã tồn tại  ");
            $('#modal-genre').modal('hidden')
        })
}


btnDeleteGenre.addEventListener('click' ,(e) =>{
    let isConfirm  = confirm("Bạn có chắc muốn xóa thể loại này không")
    if (!isConfirm)return;
    let id = parseInt(btnUpdateGenre.value);
    console.log(id)
    axios.delete(`/api/admin/genre/delete/${id}`)
        .then((res)=>{
            toastr.success("Xóa thành công ");
            setTimeout(()=>{
                window.location.reload()
            },1500)
        })
        .catch((er)=>{
            console.log(er)
            toastr.error("Xóa thất bại");
        })
} )

// $("#form-genre").validate({
//     rules: {
//         nameGenre: {
//             nameGenre: true,
//         },
//     },
//     messages: {
//         nameGenre: {
//             required: "Vui lòng nhập tên thể loại",
//         }
//     },
//     errorElement: 'span',
//     errorPlacement: function (error, element) {
//         error.addClass('invalid-feedback');
//         element.closest('.form-group').append(error);
//     },
//     highlight: function (element, errorClass, validClass) {
//         $(element).addClass('is-invalid');
//     },
//     unhighlight: function (element, errorClass, validClass) {
//         $(element).removeClass('is-invalid');
//     }
// });


