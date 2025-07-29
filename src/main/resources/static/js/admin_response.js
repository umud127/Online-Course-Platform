let currentPage = 0;
const requestsPerPage = 10;
let totalPages = 0;
let currentRejectingUserId = null;
let currentRejectingRequestId = null;

document.addEventListener("DOMContentLoaded", () => {
    loadPage(currentPage);
});

async function loadPage(page) {
    try {
        const data = await fetchWithToken(`http://localhost:8080/adminResponse/getAllRequests?page=${page}&size=${requestsPerPage}`);
        const pageData = data.data;

        renderPage(pageData.content);
        totalPages = pageData.totalPages;
        currentPage = pageData.currentPage;

        renderPagination();
    } catch (err) {
        console.error("Error loading requests:", err);
    }
}

function renderPage(requests) {
    const container = document.getElementById("requests-container");
    container.innerHTML = "";

    requests.forEach(req => {
        const userId = req.userId || null;

        const card = document.createElement("div");
        card.className = "request-card";

        // Card inner HTML except buttons
        card.innerHTML = `
            <img src="${req.photoUrl}" alt="photo" />
            <div class="card-info">
                <p><strong>User ID:</strong> ${req.userId}</p>
                <p><strong>Description:</strong> ${req.description}</p>
                <p><strong>Message:</strong> ${req.messageToAdmin}</p>
                <p><strong>Subjects:</strong> ${req.subjects}</p>
            </div>
        `;

        // Create buttons separately to add event listeners properly
        const actions = document.createElement("div");
        actions.className = "card-actions";

        const acceptBtn = document.createElement("button");
        acceptBtn.className = "accept-btn";
        acceptBtn.innerText = "Accept";
        acceptBtn.disabled = !userId;
        acceptBtn.addEventListener("click", () => {
            acceptRequest(req.id, userId);
        });

        const rejectBtn = document.createElement("button");
        rejectBtn.className = "reject-btn";
        rejectBtn.innerText = "Reject";
        rejectBtn.disabled = !userId;
        rejectBtn.addEventListener("click", () => {
            openRejectModal(req.id, userId);
        });

        actions.appendChild(acceptBtn);
        actions.appendChild(rejectBtn);

        card.appendChild(actions);
        container.appendChild(card);
    });
}

function renderPagination() {
    const pagination = document.getElementById("pagination-controls");
    pagination.innerHTML = "";

    for (let i = 0; i < totalPages; i++) {
        const btn = document.createElement("button");
        btn.innerText = i + 1;
        btn.disabled = (i === currentPage);
        btn.addEventListener("click", () => loadPage(i));
        pagination.appendChild(btn);
    }
}

window.acceptRequest = async function (requestId, userId) {
    console.log("acceptRequest → requestId:", requestId);
    console.log("acceptRequest → userId:", userId);

    if (!userId) {
        alert("User ID is missing. Cannot proceed.");
        return;
    }

    try {
        const body = {
            teacherRequestId: Number(requestId),
            userId: Number(userId),
            decision: "ACCEPT",
            messageToTeacher: ""
        };

        const response = await fetchWithToken("http://localhost:8080/adminResponse/response", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        });

        if (response.result && response.data === "Success") {
            alert("Request accepted!");
            loadPage(currentPage);
        } else {
            alert("Failed to accept request.");
        }
    } catch (error) {
        console.error("Error accepting request:", error);
    }
};

window.openRejectModal = function (requestId, userId) {
    currentRejectingUserId = userId;
    currentRejectingRequestId = requestId;

    const modal = document.getElementById("rejectModal");
    modal.classList.remove("hidden");

    const sendBtn = document.getElementById("sendRejectBtn");
    const closeModal = document.getElementById("closeModal");

    // Remove old event listeners to avoid multiple bindings on repeated open
    sendBtn.replaceWith(sendBtn.cloneNode(true));
    closeModal.replaceWith(closeModal.cloneNode(true));

    const newSendBtn = document.getElementById("sendRejectBtn");
    const newCloseModal = document.getElementById("closeModal");

    newSendBtn.addEventListener("click", async () => {
        const message = document.getElementById("rejectMessage").value.trim();

        if (!message) {
            alert("Please enter a message for rejection.");
            return;
        }

        const body = {
            teacherRequestId: Number(currentRejectingRequestId),
            userId: Number(currentRejectingUserId),
            decision: "REJECT",
            messageToTeacher: message
        };

        try {
            const response = await fetchWithToken("http://localhost:8080/adminResponse/response", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(body)
            });

            if (response.result && response.data === "Success") {
                alert("Reject message sent.");
                modal.classList.add("hidden");
                document.getElementById("rejectMessage").value = "";
                loadPage(currentPage);
            } else {
                alert("Failed to send rejection.");
            }
        } catch (error) {
            console.error("Error sending rejection:", error);
        }
    });

    newCloseModal.addEventListener("click", () => {
        modal.classList.add("hidden");
    });
};
