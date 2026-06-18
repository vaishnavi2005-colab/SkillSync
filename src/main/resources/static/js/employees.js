async function loadEmployees() {

const response =
    await fetch('/employees');

const employees =
    await response.json();

let rows = "";

employees.forEach(emp => {

    rows += `
        <tr>

            <td>${emp.id}</td>

            <td>${emp.name}</td>

            <td>${emp.email}</td>

            <td>${emp.availability}</td>

            <td>${emp.maxCapacity}</td>

            <td>${emp.currentTaskCount}</td>

            <td>

                <button
                    class="btn btn-danger btn-sm"
                    onclick="deleteEmployee(${emp.id})">

                    Delete

                </button>

            </td>

        </tr>
    `;
});

document.getElementById("employeeTable")
        .innerHTML = rows;


}

async function addEmployee() {


const employee = {

    name:
        document.getElementById("name").value,

    email:
        document.getElementById("email").value,

    availability:
        document.getElementById("availability").value,

    maxCapacity:
        parseInt(
            document.getElementById("capacity").value),

    currentTaskCount: 0
};

await fetch('/employees', {

    method: 'POST',

    headers: {
        'Content-Type': 'application/json'
    },

    body: JSON.stringify(employee)
});

loadEmployees();
```

}

async function deleteEmployee(id) {

```
await fetch(`/employees/${id}`, {

    method: 'DELETE'
});

loadEmployees();


}

loadEmployees();
