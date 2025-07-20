async function fetchWithToken(url, options = {}) {
    let accessToken = localStorage.getItem("access_token");
    let refreshToken = localStorage.getItem("refresh_token");

    let response = await fetch(url, {
        ...options,
        headers: {
            ...(options.headers || {}),
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json"
        }
    });

    if (response.status === 401 || response.status === 403) {
        const refreshResponse = await fetch("/public/refresh_token", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ refreshToken: refreshToken })
        });

        if (refreshResponse.ok) {
            const refreshData = await refreshResponse.json();

            if (refreshData.result && refreshData.data) {
                localStorage.setItem("access_token", refreshData.data.access_token);
                localStorage.setItem("refresh_token", refreshData.data.refresh_token);

                const retriedResponse = await fetch(url, {
                    ...options,
                    headers: {
                        ...(options.headers || {}),
                        Authorization: `Bearer ${refreshData.data.access_token}`,
                        "Content-Type": "application/json"
                    }
                });

                try {
                    return await retriedResponse.json();
                } catch (e) {
                    return null;
                }
            }
        }

        logout();
        return null;
    }

    if (!response.ok) {
        if (response.status === 403 || response.status === 400) {
            logout();
            return null;
        }
    }

    try {
        return await response.json();
    } catch (e) {
        return null;
    }
}


function logout() {
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
    window.location.href = "/sign_in.html";
}