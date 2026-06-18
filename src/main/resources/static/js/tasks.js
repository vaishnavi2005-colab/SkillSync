async function loadSkills() {

const skills =
    await fetch('/skills')
    .then(r => r.json());

let options = "";

skills.forEach(skill => {

    options += `
        <option value="${skill.id}">
            ${skill.skillName}
        </option>
    `;
});

document.getElementById("skillSelect")
        .innerHTML = options;

}

async function loadTasks() {

const tasks =
    await fetch('/tasks')
    .then(r => r.json());

let rows = "";

tasks.forEach(task => {

    rows += `
    <tr>

        <td>${task.id}</td>

        <td>${task.title}</td>

        <td>
            ${task.requiredSkill
                ? task.requiredSkill.skillName
                : '-'}
        </td>

        <td>${task.complexity}</td>

        <td>${task.status}</td>

        <td>

            ${task.status === 'PENDING'
                ?
                `<button
                    class="btn btn-success btn-sm me-2"
                    onclick="goToAllocation(${task.id})">

                    Allocate

                </button>`
                : ''
            }

            ${task.status === 'IN_PROGRESS'
                ?
                `<button
                    class="btn btn-primary btn-sm me-2"
                    onclick="completeTask(${task.id})">

                    Complete

                </button>`
                : ''
            }

            <button
                class="btn btn-danger btn-sm"
                onclick="deleteTask(${task.id})">

                Delete

            </button>

        </td>

    </tr>
    `;
});

document.getElementById("taskTable")
        .innerHTML = rows;

}

async function addTask() {

const task = {

    title:
        document.getElementById("title").value,

    description:
        document.getElementById("description").value,

    requiredSkill: {
        id: parseInt(
            document.getElementById("skillSelect").value)
    },

    complexity:
        document.getElementById("complexity").value,

    status: "PENDING"
};

await fetch('/tasks', {

    method: 'POST',

    headers: {
        'Content-Type': 'application/json'
    },

    body: JSON.stringify(task)
});

loadTasks();

}

function goToAllocation(taskId) {

localStorage.setItem(
    "selectedTaskId",
    taskId);

window.location.href =
    "allocation.html";

}

async function completeTask(id) {

const task =
    await fetch(`/tasks/${id}`)
    .then(r => r.json());

task.status = "COMPLETED";

await fetch(`/tasks/${id}`, {

    method: 'PUT',

    headers: {
        'Content-Type': 'application/json'
    },

    body: JSON.stringify(task)
});

loadTasks();

}

async function deleteTask(id) {

await fetch(`/tasks/${id}`, {

    method: 'DELETE'
});

loadTasks();

}

loadSkills();
loadTasks();
