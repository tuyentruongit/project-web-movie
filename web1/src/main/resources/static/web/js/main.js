const stars = document.querySelectorAll(".star");
const ratingValue = document.getElementById("rating-value");

let currentRating = 0;

stars.forEach((star) => {
    star.addEventListener("mouseover", () => {
        resetStars();
        const rating = parseInt(star.getAttribute("data-rating"));
        highlightStars(rating);
    });

    star.addEventListener("mouseout", () => {
        resetStars();
        highlightStars(currentRating);
    });

    star.addEventListener("click", () => {
        currentRating = parseInt(star.getAttribute("data-rating"));
        ratingValue.textContent = `Bạn đã đánh giá ${currentRating} sao.`;
        highlightStars(currentRating);
    });
});

function resetStars() {
    stars.forEach((star) => {
        star.classList.remove("active");
    });
}

function highlightStars(rating) {
    stars.forEach((star) => {
        const starRating = parseInt(star.getAttribute("data-rating"));
        if (starRating <= rating) {
            star.classList.add("active");
        }
    });
}

let idReviewUpdate = null
//dóng mở theo bootstrap
const modalReviewConfig = new bootstrap.Modal('#exampleModal', {
    keyboard: false
})
// xử lý modal đánh giá
const btnOpenReview = document.getElementById("btn-modal-review");
const modalReviewTitle = document.getElementById("exampleModalLabel");
const reviewContent = document.getElementById("reviewContent");
btnOpenReview.addEventListener('click', () => {
    modalReviewConfig.show();
    modalReviewTitle.textContent = "Đánh giá bộ phim";
})
//xử lý modal cập nhật review
const openModalUpdateReview = id => {
    modalReviewConfig.show();
    modalReviewTitle.textContent = "Cập nhật đánh giá ";

    //tìm kiếm review theo ID
    const review = reviewList.find(review => review.id === id);
    //cập nhật dữ liệu cho review
    reviewContent.value = review.comment;
    currentRating = review.rating;
    ratingValue.textContent = `Bạn đã đánh giá ${currentRating} sao.`
    highlightStars(currentRating);
    // lưu lại ID review cần cập nhật
    idReviewUpdate = review.id;


}
// xử lý khi đóng modal
const closeModalUpdateReview = document.getElementById('exampleModal')
closeModalUpdateReview.addEventListener('hidden.bs.modal', function (event) {
    currentRating = 0;
    ratingValue.textContent = "";
    resetStars();
    reviewContent.value = "";
    //reset review ID
    idReviewUpdate = null;

})


// xử l khi click vào nút gửi đánh giá
const btnSubmitReview = document.getElementById("submitReview");
btnSubmitReview.addEventListener('click', () => {

    // kiểm tra xem đã nhập nội dung chưa
    if (!$("#form-review").valid()) return;



    if (idReviewUpdate) {
        updateReview();
    } else {
        createReview();
    }


})


// validate

$('#form-review').validate({
    rules: {
        content: {
            required: true,
        }
    },
    messages: {
        content: {
            required: "Vui lòng nhập nội dung đánh giá ",
            email: "Please enter a valid email address"
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




// format date
// Format date
const formatDate = (dateString) => {
    const date = new Date(dateString);

    const day = `0${date.getDate()}`.slice(-2); // `05` -> 05 , '015' -> 15
    const month = `0${date.getMonth() + 1}`.slice(-2);
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
};


// hiển thị danh sách đánh giá lên giao diện
const reviewListEL = document.querySelector(".review-list");
const renderReview = (reviews) => {
    let html = "";
    reviews.forEach(review => {
        let  htmlStar = "";
        for (let i = 0 ; i<= review.rating; i++){
            htmlStar +=`<i class="fa-solid fa-star" style="color: #FFD43B;"></i>`
        }
        html += `<div class="row ">
                        <div class="col-1">
                            <div class="avatar-user ">
                                <img class="w-100" src="${review.user.avatar}" alt="">
                            </div>
                        </div>
                        <div class="col-11">
                            <div class="content-comment">
                                <h6>
                                   ${review.user.name}
                                </h6>
                                <span class="star">
                                ${htmlStar}
                                </span>
                                <p class="mb-6">
                                   ${review.comment}
                                </p>
                                 <span class="rating-time mb-0 ms-2">${formatDate(review.createdAt)}</span>
                                <span class="update-review">
                                <button class="btn bg-transparent text-decoration-underline text-primary" onclick="openModalUpdateReview(${review.id})" href="">Chỉnh sửa </button>
                                </span>
                                <span class="delete-review">
                                 <button class="btn bg-transparent text-decoration-underline text-danger" onclick="deleteReview(${review.id})"> Xóa </button>
                                </span>
                            </div>

                        </div>
                    </div>`
    });
    reviewListEL.innerHTML = html;
};

//pagination
const  renderPagination = (reviews) =>{
    $('#review-pagination').pagination({
        dataSource: reviews,
    callback: function(data, pagination) {
        renderReview(data);
    }
})
}

// tạo mới review
const createReview = () => {
    if (currentRating === 0) {
        toastr.warning("Vui lòng chọn số sao .....");
        return;
    }
    const dataReview = {
        comment: reviewContent.value,
        rating: currentRating,
        movieId: movieId.id
    }
    console.log(dataReview);
    axios.post("/api/reviews", dataReview)
        .then((res) => {
            reviewList.unshift(res.data)
            renderReview(reviewList);
            modalReviewConfig.hide();
            toastr.success("Tạo đánh giá thành công ");
        })
        .catch((err) => {
            toastr.error(err.response.data.message);
        })
}


// cập nhật review
const updateReview = () => {

    if (currentRating === 0) {
        toastr.warning("Vui lòng chọn số sao .....");
        return;
    }


    const dataReview = {
        comment: reviewContent.value,
        rating: currentRating,
        movieId: movieId.id
    }
    console.log(dataReview);
    // gọi api để cập nhật review
    axios.put("/api/reviews/" + idReviewUpdate, dataReview)
        .then((res) => {
            const index = reviewList.findIndex(review => review.id === idReviewUpdate)
            reviewList[index] = res.data;
            renderReview(reviewList);
            modalReviewConfig.hide();
            toastr.success("Cập nhật đánh giá thành công ");


          // window.location.reload();

        })
        .catch((err) => {
            toastr.error(err.response.data.message);
        })
}

// Xóa Review
const deleteReview = id => {
    const isConfirm = confirm("Bạn có chắc mình muốn xóa review này không?");
    if (!isConfirm) return;
    // Gọi Api để xóa

    axios.delete("/api/reviews/" + id)
        .then((res) => {
            reviewList = reviewList.filter(review => review.id !== id)
            renderReview(reviewList);
            toastr.success("Xóa thành công.");
        })
        .catch((err) => {
            toastr.error(err.response.data.message);
        })
}


// vừa vào trang gọi render review list
renderPagination(reviewList)
