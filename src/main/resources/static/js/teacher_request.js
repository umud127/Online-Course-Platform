document.getElementById("teacherRequestForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const form = new FormData();
    form.append("photo", document.getElementById("photo").files[0]);
    form.append("description", document.getElementById("description").value);
    form.append("message", document.getElementById("message").value);
    form.append("topics", document.getElementById("topics").value);

    try {
        const response = await fetchWithToken("http://localhost:8080/teacher/request", {
            method: "POST",
            body: form
        });

        const result = await response.json();

        if (result.result) {
            document.getElementById("responseMessage").classList.remove("hidden");
        } else {
            alert("Xəta baş verdi: " + result.errorMessage.message);
        }
    } catch (error) {
        console.error("Request error:", error);
    }
});
