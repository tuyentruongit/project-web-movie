const stars = document.querySelectorAll(".star");
const ratingValue = document.getElementById("rating-value");

let currentRating = 0;

stars.forEach((star) => {
    star.addEventListener("mouseover", () => {
        resetStars();
        let rating = parseInt(star.getAttribute("data-rating"));
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
   function sendData() {
            // Lấy giá trị từ ô input
            var inputData = document.getElementById("dataInput").value;

            // Kiểm tra nếu giá trị không rỗng
            if (inputData.trim() !== "") {
                // Tạo đối tượng dữ liệu để gửi đến server
               const data = {
                                                  comment: inputData,
                                                  ratting : rating,
                                                  movieId: 178
                                              };

                // Gửi yêu cầu fetch đến API endpoint
                fetch("http://your-spring-boot-api-endpoint", {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(data),
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Lỗi khi gửi yêu cầu.');
                    }
                    return response.json();
                })
                .then(responseData => {
                    console.log('Dữ liệu đã được gửi và xử lý thành công:', responseData);
                })
                .catch(error => {
                    console.error('Lỗi:', error);
                });
            } else {
                console.log('Vui lòng nhập dữ liệu trước khi gửi.');
            }
        }


onst url = "http://localhost:2310/api/reviews";
const input = document.getElementById("comment");
const buttonSubmit = document.getElementById("submitCreteReview");
buttonSubmit.addEventListener('click', function(){
   const inputData = input.value
   const rating = currentRating
   if (inputData.trim() !== "") {
      const data = {
         comment: inputData,
         ratting : rating,
         movieId: 178
     };

      // Gửi yêu cầu fetch đến API endpoint
      fetch("url", {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
          },
          body: JSON.stringify(data),
      })
      .then(response => {
          if (!response.ok) {
              throw new Error('Lỗi khi gửi yêu cầu.');
          }
          return response.json();
      })
      .then(responseData => {
          console.log('Dữ liệu đã được gửi và xử lý thành công:', responseData);
      })
      .catch(error => {
          console.error('Lỗi:', error);
      });
  } else {
      console.log('Vui lòng nhập dữ liệu trước khi gửi.');
  }
});
//document.getElementById('submitEditReview').addEventListener('click', function () {
//    let inputData = document.getElementById('comment').value;
//
//    let data = {
//        "rating": currentRating,
//        "comment": inputData,
//        "movieId": 178
//    };
//
//    fetch('/api/reviews/301', {
//        method: 'PUT',
//        headers: {
//            'Content-Type': 'application/json'
//        },
//        body: JSON.stringify(data)
//    })
//        .then(response => response.text())
//        .then(result => {
//            console.log(result);
//            // Xử lý phản hồi từ server nếu cần
//        })
//        .catch(error => console.error('Error:', error));
//});
//
//document.getElementById('submitDeleteReview').addEventListener('click', function () {
//    fetch('/api/reviews/301', {
//        method: 'DELETE',
//        headers: {
//            'Content-Type': 'application/json'
//        },
//    })
//        .then(response => response.text())
//        .then(result => {
//            console.log(result);
//            // Xử lý phản hồi từ server nếu cần
//        })
//        .catch(error => console.error('Error:', error));
//});