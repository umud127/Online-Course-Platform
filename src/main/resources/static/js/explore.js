document.addEventListener("DOMContentLoaded", () => {
    // İlk yüklənəndə bütün kursları göstər (boş string - filter yoxdur)
    fetchCourses("");

    // Axtarış düyməsinə klik olduqda
    document.getElementById("searchBtn").addEventListener("click", () => {
        const searchTerm = document.getElementById("searchInput").value.trim();
        fetchCourses(searchTerm);
    });
});

function fetchCourses(name = "") {
    const url = `/rest/api/course/search?name=${encodeURIComponent(name)}`;

    fetch(url)
        .then(response => {
            if (!response.ok) throw new Error("Failed to fetch courses");
            return response.json();
        })
        .then(data => {
            displayCourses(data);
        })
        .catch(error => {
            console.error("Error fetching courses:", error);
            document.getElementById("courseList").innerHTML = "<p>Failed to load courses.</p>";
        });
}

function displayCourses(courseArray) {
    const courseList = document.getElementById("courseList");
    courseList.innerHTML = "";  // əvvəlki kursları təmizlə

    if (!courseArray || courseArray.length === 0) {
        courseList.innerHTML = "<p>No courses found.</p>";
        return;
    }

    // Dinamik kurs kartlarını yarat
    courseArray.forEach(course => {
        const card = document.createElement("div");
        card.className = "course-card";
        card.textContent = course.name;  // backend-dən gələn `name` sahəsi
        courseList.appendChild(card);
    });
}
