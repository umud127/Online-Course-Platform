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
                <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
                     alt="Profile" style="width:32px; height:32px; border-radius:50%;">
            </a>`;
    } else {
        authLink.innerHTML = `<a href="/sign_up.html" style="color: white;">Sign Up / Sign In</a>`;
    }
}

function toggleSidebar() {
    const sidebar = document.getElementById("sidebar");
    const toggleBtn = document.getElementById("toggleBtn");

    sidebar.classList.toggle("closed");
    sidebar.classList.toggle("open");
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

    chapters.forEach((chapter, chapterIndex) => {
        const chapterDiv = document.createElement("div");
        chapterDiv.classList.add("chapter-item");

        chapterDiv.innerHTML = `<strong>${chapter.title}</strong><ul></ul>`;

        const videoUl = chapterDiv.querySelector("ul");

        chapter.videos.forEach((video, videoIndex) => {
            const li = document.createElement("li");
            li.textContent = video.title;
            li.onclick = () => {
                videoPlayer.src = video.url;
                videoPlayer.load();
                videoPlayer.play();
            };

            // Save first video URL
            if (!firstVideoUrl && video.url) {
                firstVideoUrl = video.url;
            }

            videoUl.appendChild(li);
        });

        chapterList.appendChild(chapterDiv);
    });

    // Auto-play first video
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

// const params = new URLSearchParams(window.location.search);
// const courseId = params.get("courseId");
// const token = localStorage.getItem("access_token");
//
// const courseTitle = document.getElementById("courseTitle");
// const courseDescription = document.getElementById("courseDescription");
// const chapterList = document.getElementById("chapterList");
// const videoPlayer = document.getElementById("videoPlayer");
//
// const sidebarContainer = document.getElementById("sidebarContainer");
// const toggleBtn = document.getElementById("toggleBtn");
//
// // Navbar funksiyası
// function setupNavbar() {
//     const authLink = document.getElementById("authLink");
//     if (token) {
//         authLink.innerHTML = `
//             <a href="/profile.html" title="Profile">
//                 <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
//                      alt="Profile" style="width:32px; height:32px; border-radius:50%;">
//             </a>`;
//     } else {
//         authLink.innerHTML = `<a href="/sign_up.html" style="color: white;">Sign Up / Sign In</a>`;
//     }
// }
//
// // Sidebar açıb bağlama funksiyası
// function toggleSidebar() {
//     sidebarContainer.classList.toggle("closed");
// }
//
// // Video yükləmə və chapters göstərmə funksiyası
// function loadCourse() {
//     fetch(`/course/${courseId}`)
//         .then(res => {
//             if (!res.ok) throw new Error("Course not found");
//             return res.json();
//         })
//         .then(data => {
//             const course = data.data;
//             courseTitle.textContent = course.name;
//             courseDescription.textContent = course.description;
//
//             displayChapters(course.chapters);
//         })
//         .catch(err => {
//             courseTitle.textContent = "❌ Course not found!";
//             console.error(err);
//         });
// }
//
// function displayChapters(chapters) {
//     chapterList.innerHTML = "";
//     let firstVideoUrl = null;
//
//     chapters.forEach(chapter => {
//         const chapterDiv = document.createElement("div");
//         chapterDiv.classList.add("chapter-item");
//
//         chapterDiv.innerHTML = `<strong>${chapter.title}</strong><ul></ul>`;
//
//         const videoUl = chapterDiv.querySelector("ul");
//
//         chapter.videos.forEach(video => {
//             const li = document.createElement("li");
//             li.textContent = video.title;
//             li.onclick = () => {
//                 videoPlayer.src = video.url;
//                 videoPlayer.load();
//                 videoPlayer.play();
//             };
//
//             if (!firstVideoUrl && video.url) {
//                 firstVideoUrl = video.url;
//             }
//
//             videoUl.appendChild(li);
//         });
//
//         chapterList.appendChild(chapterDiv);
//     });
//
//     // İlk videonu avtomatik oynat
//     if (firstVideoUrl) {
//         videoPlayer.src = firstVideoUrl;
//         videoPlayer.load();
//         videoPlayer.play();
//     }
// }
//
// // Event listener toggle düyməsinə
// toggleBtn.addEventListener("click", toggleSidebar);
//
// // Əsas iş
// if (courseId) {
//     setupNavbar();
//     loadCourse();
// } else {
//     courseTitle.textContent = "Invalid course.";
// }
