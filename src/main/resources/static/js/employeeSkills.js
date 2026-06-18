async function loadDropdowns() {


const employees =
    await fetch('/employees')
    .then(r => r.json());

const skills =
    await fetch('/skills')
    .then(r => r.json());

let employeeOptions = "";
let skillOptions = "";

employees.forEach(emp => {

    employeeOptions +=
        `<option value="${emp.id}">
            ${emp.name}
        </option>`;
});

skills.forEach(skill => {

    skillOptions +=
        `<option value="${skill.id}">
            ${skill.skillName}
        </option>`;
});

document.getElementById("employeeSelect")
        .innerHTML = employeeOptions;

document.getElementById("skillSelect")
        .innerHTML = skillOptions;


}

async function loadMappings() {


const mappings =
    await fetch('/employee-skills')
    .then(r => r.json());

let rows = "";

mappings.forEach(mapping => {

    rows += `
        <tr>

            <td>${mapping.id}</td>

            <td>${mapping.employee.name}</td>

            <td>${mapping.skill.skillName}</td>

            <td>${mapping.experienceLevel}</td>

        </tr>
    `;
});

document.getElementById("mappingTable")
        .innerHTML = rows;

}

async function assignSkill() {


const dto = {

    employeeId:
        parseInt(
            document.getElementById("employeeSelect").value),

    skillId:
        parseInt(
            document.getElementById("skillSelect").value),

    experienceLevel:
        document.getElementById("experienceLevel").value
};

await fetch('/employee-skills', {

    method: 'POST',

    headers: {
        'Content-Type': 'application/json'
    },

    body: JSON.stringify(dto)
});

loadMappings();


}

loadDropdowns();
loadMappings();
