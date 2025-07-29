let chapterIndex = 0;

function addChapter() {
    const chapterDiv = document.createElement("div");
    chapterDiv.className = "chapter";
    chapterDiv.dataset.index = chapterIndex;

    chapterDiv.innerHTML = `
        <label>Chapter Title</label>
        <input type="text" placeholder="Chapter Title" class="chapterTitle" required>
        <div class="videosContainer"></div>
        <button type="button" onclick="addVideo(this)">+ Add Video</button>
    `;

    document.getElementById("chaptersContainer").appendChild(chapterDiv);
    chapterIndex++;
}

function addVideo(button) {
    const videosContainer = button.previousElementSibling;

    const videoDiv = document.createElement("div");
    videoDiv.className = "video";
    videoDiv.innerHTML = `
        <label>Video Title</label>
        <input type="text" placeholder="Video Title" class="videoTitle" required>
        <label>Video File</label>
        <input type="file" class="videoFile" accept="video/*" required>
    `;

    videosContainer.appendChild(videoDiv);
}

document.getElementById("courseForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const formData = new FormData();

    const name = document.getElementById("courseName").value;
    const description = document.getElementById("courseDescription").value;
    const coverPhoto = document.getElementById("coverPhoto").files[0];

    const dtoCourse = {
        name,
        description,
        chapters: []
    };

    const allVideoFiles = [];

    const chapterDivs = document.querySelectorAll(".chapter");

    chapterDivs.forEach((chapterDiv, cIndex) => {
        const chapterTitle = chapterDiv.querySelector(".chapterTitle").value;
        const chapter = {
            title: chapterTitle,
            order: cIndex,
            videos: []
        };

        const videoDivs = chapterDiv.querySelectorAll(".video");
        videoDivs.forEach((videoDiv, vIndex) => {
            const videoTitle = videoDiv.querySelector(".videoTitle").value;
            const videoFile = videoDiv.querySelector(".videoFile").files[0];

            chapter.videos.push({
                title: videoTitle,
                order: vIndex
            });

            allVideoFiles.push(videoFile);
        });

        dtoCourse.chapters.push(chapter);
    });

    // 📦 JSON blob formatında `data` partını əlavə edirik
    formData.append("data", new Blob([JSON.stringify(dtoCourse)], {
        type: "application/json"
    }));

    // 📷 Cover photo
    formData.append("coverPhoto", coverPhoto);

    // 🎥 Bütün video fayllar
    allVideoFiles.forEach(file => {
        formData.append("videoFiles", file);
    });

    const accessToken = localStorage.getItem("access_token");

    try {
        const response = await fetch("http://localhost:8080/course/teacher/add", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${accessToken}`
            },
            body: formData
        });

        if (response.ok) {
            const result = await response.json();
            if (result?.result) {
                alert("✅ Course created successfully!");
                location.reload();
            } else {
                alert("❌ Error creating course!");
            }
        } else {
            if (response.status === 401 || response.status === 403) {
                alert("🔒 Unauthorized. Please login again.");
            } else {
                alert("💥 Server error: " + response.status);
            }
        }
    } catch (error) {
        alert("🌐 Network error: " + error.message);
    }
});
