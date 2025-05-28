document.getElementById("studentForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const student = {
        name: document.getElementById("name").value,
        surname: document.getElementById("surname").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    fetch("/rest/api/student/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(student)
    })
        .then(res => res.json())
        .then(data => {
            document.getElementById("response").innerText =
                `Student ${data.name} ${data.surname} registered with ID ${data.id}`;
        })
        .then(data => {
            document.getElementById("response").innerText = data;
        })
        .catch(err => console.error(err));
});