    document.addEventListener("DOMContentLoaded", async () => {
        showSection("userInfo");


        try {
            await loadUserInfo();
            await loadEnrolledCourses();
            await checkIfUserIsTeacher();
        } catch (error) {
            console.error("Yükləmə zamanı xəta baş verdi:", error);
        }

        const logoutBtn = document.getElementById("logoutBtn");
        const logoutConfirmBox = document.getElementById("logoutConfirm");

        if (logoutBtn && logoutConfirmBox) {
            logoutBtn.addEventListener("click", () => {
                logoutConfirmBox.classList.remove("hidden");
            })
        }

    });

    function showSection(id) {
        document.querySelectorAll(".content section").forEach((section) => {
            section.style.display = "none";
        });
        document.getElementById(id).style.display = "block";
    }

    async function loadUserInfo() {
        try {
            const response = await fetchWithToken("http://localhost:8080/user/me");
            const user = response.data;

            if (!user) {
                console.error("User data boş və ya undefined:", response);
                return;
            }

            const usernameInput = document.getElementById("username");
            const emailInput = document.getElementById("email");
            const phoneInput = document.getElementById("phone");
            const passwordInput = document.getElementById("password");

            if (usernameInput) usernameInput.value = user.username || "";
            if (emailInput) emailInput.value = user.email || "";
            if (phoneInput) phoneInput.value = user.phoneNumber || "";
            if (passwordInput) passwordInput.value = "";
        } catch (err) {
            console.error("User load error:", err);
        }
    }

    document.getElementById("userInfoForm").addEventListener("submit", async function (e) {

        const phoneNumber = document.getElementById("phone")?.value || "";
        const password = document.getElementById("password")?.value || "";

        const updatedData = {phoneNumber, password};

        try {
            const res = await fetchWithToken("http://localhost:8080/user/me/update", {
                method: "PUT",
                body: JSON.stringify(updatedData),
            });
            alert("Profile updated successfully!");
            await loadUserInfo();
        } catch (err) {
            console.error("Update error:", err);
            alert("Failed to update profile.");
        }

    });

    async function loadEnrolledCourses() {
        const response = await fetchWithToken("http://localhost:8080/user/getEnrolledCourses");

        const list = document.getElementById("courseList");

        if (!response || !Array.isArray(response.data)) {
            console.error("Courses data is not an array:", response);
            return;
        }

        list.innerHTML = "";
        response.data.forEach((course) => {
            const li = document.createElement("li");
            li.textContent = course.name;
            list.appendChild(li);
        });
    }

    async function becomeTeacher() {
        try {
            const data = await fetchWithToken("http://localhost:8080/user/me");
            const user = data.data || data;

            if (user.role === "TEACHER" || user.role === "ROLE_TEACHER") {
                // Əgər müəllimsə, teacher panel göstər
                document.getElementById("beATeacherSection").style.display = "none";
                document.getElementById("teacherPanelSection").style.display = "block";
            } else {
                // Müəllim deyilsə, yönləndir teacher_request.html səhifəsinə
                window.location.href = "/teacher_request.html";
            }
        } catch (error) {
            console.error("Xəta:", error);
        }
    }

    document.getElementById("logoutBtn").addEventListener("click", () => {
        document.getElementById("logoutConfirm").classList.remove("hidden");
    });

    function confirmLogout(confirm) {

        const box = document.getElementById("logoutConfirm");
        if (confirm) {
            localStorage.removeItem("access_token");
            localStorage.removeItem("refresh_token");
            localStorage.removeItem("email_verified");
            window.location.href = "/home.html";
        } else {
            box.classList.add("hidden");
        }

    }

    document.getElementById("deleteAccountBtn").addEventListener("click", async function () {

        const confirmed = confirm("Are you sure you want to delete your account? This action is irreversible.");
        if (!confirmed) return;

        try {
            const res = await fetchWithToken("http://localhost:8080/user/me/delete", {
                method: "DELETE"
            });
            alert("Account deleted successfully.");
            confirmLogout(true); // logout funksiyası burada çağırıla bilər
        } catch (err) {
            console.error("Delete error:", err);
            alert("Failed to delete account.");
        }

    });

    async function checkIfUserIsTeacher() {
        try {
            const response = await fetchWithToken("http://localhost:8080/user/checkTeacherStatus", {
                method: "GET"
            });

            const status = response.data; // "Teacher", "Requested", ya da "User"

            const beTeacherBtn = document.getElementById("beATeacherSection");
            const waitingMsg = document.getElementById("waitingMsg");
            const teacherContent = document.getElementById("teacherContent");

            // Hamısını gizlət
            if (beTeacherBtn) beTeacherBtn.style.display = "none";
            if (waitingMsg) waitingMsg.style.display = "none";
            if (teacherContent) teacherContent.style.display = "none";

            if (status === "Teacher") {
                teacherContent.style.display = "block";
                await loadTeacherCourses();
            } else if (status === "Requested") {
                waitingMsg.style.display = "block";
            } else if (status === "User") {
                beTeacherBtn.style.display = "inline-block";
            }
        } catch (error) {
            console.error("Teacher status yoxlanarkən xəta:", error);
        }
    }

    async function loadTeacherCourses() {
        try {
            const response = await fetchWithToken("http://localhost:8080/teacher/myCourses");
            const courses = response.data;

            const courseList = document.getElementById("myCoursesList");
            const noCoursesMsg = document.getElementById("noCoursesMsg");

            courseList.innerHTML = "";

            if (!courses || courses.length === 0) {
                noCoursesMsg.style.display = "block";
            } else {
                noCoursesMsg.style.display = "none";

                courses.forEach(course => {
                    const li = document.createElement("li");
                    li.textContent = course.name;
                    courseList.appendChild(li);
                });
            }

        } catch (err) {
            console.error("Error loading teacher's courses:", err);
        }
    }

    function goToAddCourse() {
        window.location.href = "/course_create.html";
    }
