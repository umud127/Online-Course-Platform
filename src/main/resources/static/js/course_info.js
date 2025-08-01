const params = new URLSearchParams(window.location.search);
const courseId = params.get("courseId");

const nameEl = document.getElementById("name");
const descEl = document.getElementById("description");
const coverEl = document.getElementById("cover");
const countEl = document.getElementById("clickCount");
const enrollBtn = document.getElementById("enrollBtn");
const enrollMessage = document.getElementById("enrollMessage");

const chaptersList = document.getElementById("chaptersList");
const token = localStorage.getItem("access_token");

function loadCourseInfo(courseId) {
    fetch(`/course/${courseId}`)
        .then(res => {
            if (!res.ok) throw new Error("Course not found");
            return res.json();
        })
        .then(data => {
            const course = data.data;
            nameEl.textContent = course.name;
            descEl.textContent = course.description;
            coverEl.src = course.coverPhoto || "https://via.placeholder.com/800x300?text=No+Image";
            countEl.textContent = course.clickCount;

            displayChapters(course.chapters);
            increaseClickCount(courseId);
        })
        .catch(err => {
            console.error("Course loading error:", err);
            nameEl.textContent = "Error loading course.";
            enrollBtn.style.display = "none";
        });
}

function increaseClickCount(id) {
    fetch(`/course/increaseClickCount/${id}`, { method: "PUT" })
        .catch(error => console.warn("Click count artırıla bilmədi:", error));
}

function handleEnrollment(id) {
    fetch(`/course/toEnroll/${id}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        }
    })
        .then(res => {
            if (!res.ok) throw new Error("Enrollment failed");
            return res.json();
        })
        .then(() => {
            enrollMessage.classList.remove("hidden");
            enrollMessage.textContent = "✅ Successfully enrolled!";
            enrollMessage.style.color = "green";

            //when finishing enrolling, check again button to change from the enroll button to watch button
            setupEnrollOrWatchButton(courseId);
        })
        .catch(err => {
            enrollMessage.classList.remove("hidden");
            enrollMessage.textContent = "❌ Enrollment failed.";
            enrollMessage.style.color = "red";
            console.error("Enrollment error:", err);
        });
}

function displayChapters(chapters) {
    chaptersList.innerHTML = "";

    if (!chapters || chapters.length === 0) {
        chaptersList.innerHTML = "<p>No chapters available.</p>";
        return;
    }

    chapters.forEach(chapter => {
        const chapterDiv = document.createElement("div");
        chapterDiv.className = "chapter";

        const title = document.createElement("div");
        title.className = "chapter-title";
        title.textContent = chapter.title;

        const videosUl = document.createElement("ul");
        videosUl.className = "video-list";

        chapter.videos.forEach(video => {
            const li = document.createElement("li");
            li.textContent = video.title;
            videosUl.appendChild(li);
        });

        chapterDiv.appendChild(title);
        chapterDiv.appendChild(videosUl);
        chaptersList.appendChild(chapterDiv);
    });
}

function setupNavbar() {
    const authLink = document.getElementById("authLink");
    if (token) {
        authLink.innerHTML = `
            <a href="/profile.html">
                <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png" 
                alt="Profile" style="width:32px; height:32px; border-radius:50%;">
            </a>`;
    } else {
        authLink.innerHTML = `<a href="/sign_up.html">Sign Up / Sign In</a>`;
    }
}

function setupEnrollOrWatchButton(courseId) {
    if (!token) {
        enrollBtn.textContent = "Sign in to enroll";
        enrollBtn.disabled = true;
        return;
    }

    // 1. Check if user is the teacher (OWNER)
    fetch(`/course/checkTeacherHaveCourse/${courseId}`, {
        headers: { "Authorization": "Bearer " + token }
    })
        .then(res => {
            if (!res.ok) return false; // Əgər 403 və ya 401-disə
            return res.json();
        })
        .then(isOwner => {
            if (isOwner) {
                enrollBtn.textContent = "🎓 It's your course.";
                enrollBtn.disabled = true;
            } else {
                // Əgər teacher deyilsə və ya owner deyilsə
                fetch(`/course/checkStudentHaveCourse/${courseId}`, {
                    headers: { "Authorization": "Bearer " + token }
                })
                    .then(res => {
                        if (!res.ok) return false;
                        return res.json();
                    })
                    .then(isStudent => {
                        if (isStudent) {
                            enrollBtn.textContent = "▶️ Watch Course";
                            enrollBtn.onclick = () => {
                                window.location.href = `/course_watch.html?courseId=${courseId}`;
                            };
                        } else {
                            enrollBtn.textContent = "Enroll Now";
                            enrollBtn.onclick = () => handleEnrollment(courseId);
                        }
                    });
            }
        });

}

// --- INIT ---
if (courseId) {
    setupNavbar();
    loadCourseInfo(courseId);
    setupEnrollOrWatchButton(courseId);
} else {
    nameEl.textContent = "No course selected.";
    enrollBtn.style.display = "none";
}
