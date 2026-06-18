async function loadStats(){


const employees =
    await fetch('/employees')
    .then(r=>r.json());

const skills =
    await fetch('/skills')
    .then(r=>r.json());

const tasks =
    await fetch('/tasks')
    .then(r=>r.json());

document.getElementById("employeeCount")
        .innerText = employees.length;

document.getElementById("skillCount")
        .innerText = skills.length;

document.getElementById("taskCount")
        .innerText = tasks.length;

const allocated =
    tasks.filter(
        t=>t.status==="IN_PROGRESS"
    );

document.getElementById("allocatedCount")
        .innerText = allocated.length;


}

loadStats();
