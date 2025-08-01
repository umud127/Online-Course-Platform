const params = new URLSearchParams(window.location.search);
const courseId = params.get("courseId");
const token = localStorage.getItem("access_token");

const courseTitle = document.getElementById("courseTitle");
const courseDescription = document.getElementById("courseDescription");
const chapterList = document.getElementById("chapterList");
const videoPlayer = document.getElementById("videoPlayer");

function setupNavbar() {
    const authLink = document.getElementById("authLink");
    if (token) {
        authLink.innerHTML = `
          <a href="/profile.html">
            <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png" alt="Profile">
          </a>`;
    } else {
        authLink.innerHTML = `<a href="/sign_up.html" style="color: white;">Sign Up / Sign In</a>`;
    }
}

function toggleSidebar() {
    const sidebar = document.getElementById("sidebar");
    sidebar.classList.toggle("closed");
}

function loadCourse() {
    fetch(`/course/${courseId}`)
        .then(res => res.json())
        .then(data => {
            const course = data.data;
            courseTitle.textContent = course.name;
            courseDescription.textContent = course.description;
            displayChapters(course.chapters);
        })
        .catch(err => {
            courseTitle.textContent = "❌ Course not found!";
            console.error(err);
        });
}

function displayChapters(chapters) {
    chapterList.innerHTML = "";
    let firstVideoUrl = null;

    chapters.forEach(chapter => {
        const chapterDiv = document.createElement("div");
        chapterDiv.classList.add("chapter-item");

        chapterDiv.innerHTML = `<strong>${chapter.title}</strong><ul></ul>`;
        const videoUl = chapterDiv.querySelector("ul");

        chapter.videos.forEach(video => {
            const li = document.createElement("li");
            li.textContent = video.title;
            li.onclick = () => {
                videoPlayer.src = video.url;
                videoPlayer.load();
                videoPlayer.play();
            };

            if (!firstVideoUrl && video.url) {
                firstVideoUrl = video.url;
            }

            videoUl.appendChild(li);
        });

        chapterList.appendChild(chapterDiv);
    });

    if (firstVideoUrl) {
        videoPlayer.src = firstVideoUrl;
        videoPlayer.load();
        videoPlayer.play();
    }
}

if (courseId) {
    setupNavbar();
    loadCourse();
} else {
    courseTitle.textContent = "Invalid course.";
}