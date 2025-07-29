// const courseData = [
//     {
//         title: "Chapter 1: Introduction",
//         videos: [
//             { title: "Welcome", url: "https://www.w3schools.com/html/mov_bbb.mp4" },
//             { title: "Overview", url: "https://www.w3schools.com/html/movie.mp4" },
//         ],
//     },
//     {
//         title: "Chapter 2: Core Concepts",
//         videos: [
//             { title: "OOP in Java", url: "https://www.w3schools.com/html/mov_bbb.mp4" },
//             { title: "Spring Boot Basics", url: "https://www.w3schools.com/html/movie.mp4" },
//         ],
//     },
// ];
//
// const sidebar = document.getElementById("sidebar");
// const videoPlayer = document.getElementById("videoPlayer");
//
// courseData.forEach((chapter, cIndex) => {
//     const chapterDiv = document.createElement("div");
//     chapterDiv.className = "chapter";
//
//     const chapterTitle = document.createElement("div");
//     chapterTitle.className = "chapter-title";
//     chapterTitle.textContent = chapter.title;
//     chapterTitle.addEventListener("click", () => {
//         chapterDiv.classList.toggle("open");
//     });
//
//     const videoList = document.createElement("div");
//     videoList.className = "video-list";
//
//     chapter.videos.forEach((video, vIndex) => {
//         const videoItem = document.createElement("div");
//         videoItem.className = "video-item";
//         videoItem.textContent = video.title;
//
//         videoItem.addEventListener("click", () => {
//             videoPlayer.src = video.url;
//             videoPlayer.classList.remove("fade-in");
//             void videoPlayer.offsetWidth; // reset animation
//             videoPlayer.classList.add("fade-in");
//             videoPlayer.play();
//         });
//
//         videoList.appendChild(videoItem);
//     });
//
//     chapterDiv.appendChild(chapterTitle);
//     chapterDiv.appendChild(videoList);
//     sidebar.appendChild(chapterDiv);
// });