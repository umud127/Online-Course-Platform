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
        .then(res => res.json())
        .then(data => displayCourses(data))
        .catch(() => {
            document.getElementById("courseList").innerHTML = "<div class='course-card'>Failed to load top courses.</div>";
        });
}

function fetchCourses(name) {
    fetch(`http://localhost:8080/rest/api/course/search?name=${encodeURIComponent(name)}&limit=5`)
        .then(res => res.json())
        .then(data => displayCourses(data))
        .catch(() => {
            document.getElementById("courseList").innerHTML = "<div class='course-card'>Failed to load search results.</div>";
        });
}

function displayCourses(courses) {
    const courseList = document.getElementById("courseList");
    courseList.innerHTML = "";

    if (!courses || courses.length === 0) {
        courseList.innerHTML = "<div class='course-card'>No courses found.</div>";
        return;
    }

    courses.forEach(course => {
        const courseCard = document.createElement("div");
        courseCard.className = "course-card";

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
            <p class="click-count">Clicks: ${course.clickCount}</p>
        `;

        courseList.appendChild(courseCard);
    });
}
