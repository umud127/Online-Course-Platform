// when submit button clicked
document.getElementById("signupForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const emailVerified = localStorage.getItem("email_verified");
    if (emailVerified !== "true") {
        const resp = document.getElementById("response");
        resp.style.color = "red";
        resp.innerText = "Zəhmət olmasa əvvəlcə email təsdiqləyin.";
        return; // ❌ qeydiyyatı blokla
    }

    const user = {
        username: document.querySelector("input[name='username']").value,
        email: document.querySelector("input[name='email']").value,
        phoneNumber: document.querySelector("input[name='phone']").value,
        password: document.querySelector("input[name='password']").value
    };

    fetch("/public/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
        .then(res => res.json())
        .then(data => {
            const resp = document.getElementById("response");
            if (data.result) {
                resp.style.color = "green";
                resp.innerText = "Uğurla qeydiyyatdan keçdiniz! 🎉";
                localStorage.setItem("access_token", data.data.access_token);
                localStorage.setItem("refresh_token", data.data.refresh_token);
                window.location.href = "/explore.html";
            } else {
                resp.style.color = "red";
                resp.innerText = "Xəta: " + data.errorMessage.messageType;
            }
        })
        .catch(err => {
            console.error(err);
            const resp = document.getElementById("response");
            resp.style.color = "red";
            resp.innerText = "Server xətası baş verdi.";
        });
});

// when send button clicked
document.querySelector(".send-code-btn").addEventListener("click", function() {
    const email = document.querySelector("input[name='email']").value.trim();
    const resp = document.getElementById("response");

    if (!email) {
        resp.style.color = "red";
        resp.innerText = "Zəhmət olmasa email ünvanını daxil edin.";
        return;
    }

    fetch("/otp/send", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email: email })
    })
        .then(res => {
            if (!res.ok) throw new Error("Server cavabında problem var");
            return res.json();
        })
        .then(data => {
            if (data.result) {  // BaseEntity-də result sahəsi
                resp.style.color = "green";
                resp.innerText = data.data || "Təsdiq kodu email ünvanınıza göndərildi! 📧";
            } else {
                resp.style.color = "red";
                resp.innerText = "Kod göndərmək mümkün olmadı: " + (data.errorMessage?.message || "Naməlum xəta");
            }
        })
        .catch(() => {
            resp.style.color = "red";
            resp.innerText = "Server xətası baş verdi.";
        });
});

// when verify button clicked
document.querySelector(".verify-code-btn").addEventListener("click", function () {
    const email = document.querySelector("input[name='email']").value.trim();
    const code = document.querySelector("input[name='code']").value.trim();
    const resp = document.getElementById("response");

    if (!email || !code) {
        resp.style.color = "red";
        resp.innerText = "Email və kod daxil edilməlidir.";
        return;
    }

    fetch("/otp/verify", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            otp: code
        })
    })
        .then(res => res.json())
        .then(data => {
            const resp = document.getElementById("response");
            if (data.result) {
                resp.style.color = "green";
                resp.innerText = data.data; // "OTP kodu düzgündür!"
                localStorage.setItem("email_verified", "true");
            } else {
                resp.style.color = "red";
                resp.innerText = "Kod səhvdir: " + (data.errorMessage?.message || "Naməlum xəta");
            }
        })
        .catch(() => {
            const resp = document.getElementById("response");
            resp.style.color = "red";
            resp.innerText = "Server xətası baş verdi.";
        });
});