function updateNavbarBasedOnAuth() {
    const token = localStorage.getItem("access_token");
    const authLink = document.getElementById("authLink");

    if (token) {
        authLink.innerHTML = `
            <a href="/profile.html">
                <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png" 
                     alt="Profile" 
                     style="width:32px; height:32px; border-radius:50%;">
            </a>
        `;
    } else {
        authLink.innerHTML = `<a href="/sign_up.html">Sign Up / Sign In</a>`;
    }
}