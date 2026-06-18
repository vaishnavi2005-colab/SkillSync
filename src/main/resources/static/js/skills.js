async function loadSkills() {


const response =
    await fetch('/skills');

const skills =
    await response.json();

let rows = "";

skills.forEach(skill => {

    rows += `
        <tr>

            <td>${skill.id}</td>

            <td>${skill.skillName}</td>

            <td>

                <button
                    class="btn btn-danger btn-sm"
                    onclick="deleteSkill(${skill.id})">

                    Delete

                </button>

            </td>

        </tr>
    `;
});

document.getElementById("skillTable")
        .innerHTML = rows;

}

async function addSkill() {

const skill = {

    skillName:
        document.getElementById("skillName").value
};

await fetch('/skills', {

    method: 'POST',

    headers: {
        'Content-Type': 'application/json'
    },

    body: JSON.stringify(skill)
});

document.getElementById("skillName").value = "";

loadSkills();


}

async function deleteSkill(id) {


await fetch(`/skills/${id}`, {

    method: 'DELETE'
});

loadSkills();


}

loadSkills();
