document.getElementById("signinForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const credentials = {
        email: document.querySelector("input[name='email']").value,
        password: document.querySelector("input[name='password']").value
    };

    fetch("/public/authenticate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(credentials)
    })
        .then(res => {
            if (!res.ok) {
                throw new Error("Login failed");
            }
            return res.json();
        })
        .then(data => {
            const resp = document.getElementById("response");

            if (data.result && data.data && (data.data.access_token || data.data.refresh_token)) {
                resp.style.color = "green";
                resp.innerText = "Giriş uğurludur! 🎉";
                localStorage.setItem("access_token", data.data.access_token);
                localStorage.setItem("refresh_token", data.data.refresh_token);
                window.location.href = "/explore.html";
            } else {
                resp.style.color = "red";
                if (data.errorMessage && data.errorMessage.messageType) {
                    resp.innerText = "Xəta: " + data.errorMessage.messageType;
                } else {
                    resp.innerText = "Email və ya şifrə yanlışdır.";
                }
            }
        })
        .catch(err => {
            console.error(err);
            const resp = document.getElementById("response");
            resp.style.color = "red";
            resp.innerText = "Giriş zamanı xəta baş verdi.";
        });
});