document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("teacherRequestForm");
    const responseMessage = document.getElementById("responseMessage");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const photoInput = document.getElementById("photo");
        const description = document.getElementById("description").value.trim();
        const message = document.getElementById("message").value.trim();
        const topics = document.getElementById("topics").value.trim();

        // Tokeni localStorage-dən götür
        const token = localStorage.getItem("access_token");

        if (!token) {
            alert("Zəhmət olmasa əvvəlcə daxil olun!");
            return;
        }

        if (photoInput.files.length === 0) {
            alert("Zəhmət olmasa şəkil seçin.");
            return;
        }

        const formData = new FormData();
        formData.append("description", description);
        formData.append("messageToAdmin", message);
        formData.append("subjects", topics);
        formData.append("file", photoInput.files[0]); // Backend tərəfdə `@RequestParam("file")` adı ilə gəlir

        try {
            const response = await fetch("http://localhost:8080/teacher_request/request", {
                method: "POST",
                headers: {
                    "Authorization": "Bearer " + token
                },
                body: formData
            });

            if (response.ok) {
                form.reset();
                responseMessage.classList.remove("hidden");
                responseMessage.textContent = "Müraciətiniz uğurla göndərildi! Təşəkkür edirik.";
                responseMessage.style.color = "green";
            } else if (response.status === 400 || response.status === 401) {
                alert("Zəhmət olmasa hesabınıza daxil olun və ya düzgün məlumat verin.");
            } else {
                alert("Naməlum xəta baş verdi.");
            }
        } catch (error) {
            console.error("Xəta baş verdi:", error);
            alert("Şəbəkə xətası! İnternet bağlantınızı yoxlayın.");
        }
    });
});
