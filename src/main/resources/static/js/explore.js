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

    updateNavbarBasedOnAuth()
});

function fetchTop5Courses() {
    fetch('http://localhost:8080/course/top5')
        .then(res => res.json())
        .then(result => displayCourses(result.data))
        .catch(() => {
            document.getElementById("courseList").innerHTML = "<div class='course-card'>Failed to load top courses.</div>";
        });
}

function fetchCourses(name) {
    fetch(`http://localhost:8080/course/search?name=${encodeURIComponent(name)}&limit=5`)
        .then(res => res.json())
        .then(result => displayCourses(result.data))
        .catch(() => {
            document.getElementById("courseList").innerHTML = "<div class='course-card'>Failed to load search results.</div>";
        });
}

function displayCourses(courses) {
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

        courseCard.addEventListener("click", () => {
            window.location.href = `/course_info.html?courseId=${course.id}`;
        });

        courseList.appendChild(courseCard);
    });
}

function updateNavbarBasedOnAuth() {
    const token = localStorage.getItem("access_token");
    const authLink = document.getElementById("authLink");

    if (token) {
        authLink.innerHTML = `
            <a href="/profile.html">
                <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png" 
                     alt="Profile" 
                     style="width:32px; height:32px; border-radius:50%;">
            </a>
        `;
    } else {
        authLink.innerHTML = `<a href="/sign_up.html">Sign Up / Sign In</a>`;
    }
}