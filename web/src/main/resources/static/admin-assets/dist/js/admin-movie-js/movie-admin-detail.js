const titleEl = document.getElementById('title');
const descriptionEl = document.getElementById('description');
const relishedYearEl = document.getElementById("releaseYear");
const  statusEl = document.getElementById("status")
const btnSave = document.getElementById("btn-create");
const  posterMovie = document.getElementById("thumbnail");
const movieTypeEl = document.getElementById("type");


let idMovie = movieCurrent.id;

const renderDataMovie = ()=>{
    titleEl.value=movieCurrent.title;
    descriptionEl.value=movieCurrent.description;
    relishedYearEl.value = movieCurrent.relishYear;
    statusEl.value=movieCurrent.status;
    let directorIds = movieCurrent.directorList.map(actor => actor.id);
    $('#director').val(directorIds);
    $('#director').trigger('change');
    let actorIds = movieCurrent.actorList.map(actor => actor.id);
    console.log(actorIds);
    $('#actor').val(actorIds);
    $('#actor').trigger('change');
    let genreIds = movieCurrent.genreList.map(genre => genre.id);
    console.log(genreIds);
    $('#genre').val(genreIds);
    $('#genre').trigger('change');



}


btnSave.addEventListener('click' ,(e)=>{
    e.preventDefault();
    if (!$('#form-create-movie').valid()) return;
    let status = false;
    if (statusEl.getAttribute("value")==="true"){
        status=true;
    }
    let directorValue = $('#director').val();
    let actorValue = $('#actor').val();
    let genreValue = $('#genre').val();
    let directorList = directorValue.map(dir => parseInt(dir));
    let actorList = actorValue.map(dir => parseInt(dir));
    let genreList = genreValue.map(dir => parseInt(dir));
    const data = {
        title : titleEl.value,
        description : descriptionEl.value,
        type : movieTypeEl.value,
        releaseYear : relishedYearEl.value,
        status : status,
        directorIds : directorList,
        actorIds : actorList,
        genreIds : genreList,
        poster : posterMovie.getAttribute("src")
    }
    axios.put("/api/admin/movies/"+idMovie,data)
        .then((res)=>{
            toastr.success("Lưu thành công ");
            setTimeout(() => {
                window.location.href = "/admin/movies";
            }, 1500);
        })
        .catch(()=>{
            toastr.error("Lưu thất bại ");
        })
})
$('#form-create-movie').validate({
    rules: {
        title: {
            required: true,
        },
        director: {
            required: true,
        },
        description: {
            required: true
        },
        actor: {
            required: true
        },
        genre: {
            required: true
        },
        relishedAt: {
            required: true
        },
        thumbnail: {
            required: true
        }
    }, messages: {
        title: {
            required: "Vui lòng nhập tiêu đề phim",

        }, director: {
            required: "Vui lòng nhập đạo diễn bộ phim",

        }, description: {
            required: "Vui lòng nhập mô tả cho phim"
        }
        , actor: {
            required: "Vui lòng nhập diễn viên"
        }
        , genre: {
            required: "Vui lòng nhập thể loại "
        }
        , relishedAt: {
            required: "Vui lòng nhập năm sản xuất"
        }
        , thumbnail: {
            required: "Vui lòng chọn hình ảnh"
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
const deleteMovie = document.getElementById("deleteMovie");
deleteMovie.addEventListener('click', () => {
    const isConfirm = confirm("Bạn có chắc mình muốn xóa bộ phim này không?");
    if (!isConfirm) return;
    axios.delete("/api/admin/movies/" + idMovie)
        .then((res) => {
            toastr.success("Xóa Thành Công ");
            setTimeout(() => {
                window.location.href = "/admin/movies";
            }, 1500);
        })
        .catch((err) => {
            toastr.error(err.response.data.message);
        })

});

renderDataMovie();

































