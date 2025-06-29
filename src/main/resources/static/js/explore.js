document.addEventListener("DOMContentLoaded", () => {
    fetchTop5Courses();

    document.getElementById("searchBtn").addEventListener("click", () => {
        const searchTerm = document.getElementById("searchInput").value.trim();
        if (searchTerm === "") {
            fetchTop5Courses();
        } else {
            fetchCourses(searchTerm);
        }
    });
});

function fetchTop5Courses() {
    fetch('http://localhost:8080/rest/api/course/top5')
        .then(res => res.json()) // ✅ yalnız bir dəfə
        .then(result => displayCourses(result.data)) // .data içindən çıxar
        .catch(() => {
            document.getElementById("courseList").innerHTML = "<div class='course-card'>Failed to load top courses.</div>";
        });
}

function fetchCourses(name) {
    fetch(`http://localhost:8080/rest/api/course/search?name=${encodeURIComponent(name)}&limit=5`)
        .then(res => res.json()) // ✅ yalnız bir dəfə
        .then(result => displayCourses(result.data)) // .data içindən çıxar
        .catch(() => {
            document.getElementById("courseList").innerHTML = "<div class='course-card'>Failed to load search results.</div>";
        });
}


function displayCourses(courses) {
    debugger;
    const courseList = document.getElementById("courseList");
    if (!courseList) {
        console.error("Error: #courseList element tapılmadı!");
        return;
    }

    courseList.innerHTML = "";

    if (!courses || courses.length === 0) {
        courseList.innerHTML = "<div class='course-card'>No courses found.</div>";
        return;
    }

    courses.forEach(course => {
        const courseCard = document.createElement("div");
        courseCard.className = "course-card";
        courseCard.style.cursor = "pointer";

        const imageUrl = course.coverPhoto && course.coverPhoto.trim() !== ""
            ? course.coverPhoto
            : "https://via.placeholder.com/300x180?text=No+Image";

        courseCard.innerHTML = `
            <div class="course-img-container">
                <img src="${imageUrl}" 
                     alt="${course.name}" 
                     class="course-img"
                     onerror="this.onerror=null;this.src='https://via.placeholder.com/300x180?text=No+Image';" />
            </div>
            <h3>${course.name}</h3>
            <p class="course-desc">${course.description}</p>
            <p class="click-count" id="click-count-${course.id}">Clicks: ${course.clickCount}</p>
        `;

        // ✅ CLICK EVENT
        courseCard.addEventListener("click", () => {
            debugger;
            fetch(`http://localhost:8080/rest/api/course/${course.id}/increaseClickCount`, {
                method: "PUT"
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Click count artırılmadı");
                    }

                    // ✅ Click sayı dərhal artırılsın
                    const countElement = document.getElementById(`click-count-${course.id}`);
                    if (countElement) {
                        const current = parseInt(countElement.textContent.replace("Clicks: ", ""));
                        countElement.textContent = `Clicks: ${current + 1}`;
                    }
                })
                .catch(error => {
                    console.error("Click artırılarkən xəta:", error);
                });
        });

        courseList.appendChild(courseCard);
    });
}
