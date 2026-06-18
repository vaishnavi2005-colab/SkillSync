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

async function allocateTask() {


const selectedTaskId =
    localStorage.getItem("selectedTaskId");

if (selectedTaskId) {

    const response =
        await fetch(
            `/allocation/assign-existing/${selectedTaskId}`,
            {
                method: 'POST'
            });

    const task =
        await response.json();

    document.getElementById("result")
            .innerHTML = `

            <div class="alert alert-success">

                <h5>${task.title}</h5>

                <p>Status :
                <b>${task.status}</b></p>

                <p>Assigned Employee :
                <b>
                ${task.assignedEmployee
                    ? task.assignedEmployee.name
                    : 'No Employee Available'}
                </b></p>

            </div>
            `;

    localStorage.removeItem(
        "selectedTaskId");

    return;
}

const dto = {

    title:
        document.getElementById("title").value,

    description:
        document.getElementById("description").value,

    requiredSkillId:
        parseInt(
            document.getElementById("skillSelect").value),

    complexity:
        document.getElementById("complexity").value,

    deadline:
        document.getElementById("deadline").value
};

const response =
    await fetch('/allocation/assign', {

        method: 'POST',

        headers: {
            'Content-Type': 'application/json'
        },

        body: JSON.stringify(dto)
    });

const task =
    await response.json();

document.getElementById("result")
        .innerHTML = `

        <div class="alert alert-success">

            <h5>${task.title}</h5>

            <p>${task.description}</p>

            <p>
                Status :
                <b>${task.status}</b>
            </p>

            <p>
                Assigned Employee :
                <b>
                ${task.assignedEmployee
                    ? task.assignedEmployee.name
                    : 'No Employee Available'}
                </b>
            </p>

        </div>
        `;


}

loadSkills();
