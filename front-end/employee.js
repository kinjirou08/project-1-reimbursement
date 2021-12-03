// Initial page load
window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/loginStatus', {
        method: 'GET',
        credentials: 'include'
    });
    // If the above request results in a 200 status code, that means we are actually logged in
    // So we need to take the userRole information and determine where to redirect them to
    if (res.status === 200) {
        let userObj = await res.json();

            if (userObj.ersRole === 'Finance Manager') {
                window.location.href = 'finance-manager-home.html';
            } else if (res.status === 401) {
                window.location.href = 'index.html';
        }
    }
    populateTableWithReimbursements();
});

let logoutBtn = document.querySelector('#logoutBtn');

logoutBtn.addEventListener('click', async () => {

    let res = await fetch('http://localhost:8080/logout', {
        'method': 'POST',
        'credentials': 'include'
    });

    if (res.status === 200) {
        window.location.href = 'index.html';
    }

});

async function populateTableWithReimbursements() {
    let res = await fetch('http://localhost:8080/reimbursements/user', {
        credentials: 'include',
        method: 'GET'
    });

    let tbodyElement = document.querySelector("#reimbursement-table tbody");
    tbodyElement.innerHTML = '';
    let reimbursementArray =  await res.json();

    for (let i = 0; i < reimbursementArray.length; i++) {
        let reimbursement = reimbursementArray[i];

        let tr = document.createElement('tr');

        let tdReimbId = document.createElement('td');
        tdReimbId.innerHTML = reimbursement.reimbId;

        let tdReimbAmount = document.createElement('td');
        tdReimbAmount.innerHTML = '$'+ reimbursement.reimbAmount;

        let tdReimbSubmitted = document.createElement('td');
        tdReimbSubmitted.innerHTML = reimbursement.reimbSubmitted;

        let tdReimbResolved = document.createElement('td');
        let tdReimbResolver = document.createElement('td');
        if (reimbursement.reimbResolved != null) {
        tdReimbResolved.innerHTML = reimbursement.reimbResolved;
        tdReimbResolver.innerHTML = reimbursement.reimbResolver;
        } else {
            tdReimbResolved.innerHTML = 'Not Yet Resolved'
            tdReimbResolver.innerHTML = "No Resolver Yet"
        }

        let tdReimbStatus = document.createElement('td');
        tdReimbStatus.innerHTML = reimbursement.reimbStatus;

        let tdReimbType = document.createElement('td');
        tdReimbType.innerHTML = reimbursement.reimbType;

        let tdReimbDescription = document.createElement('td');
        tdReimbDescription.innerHTML = reimbursement.reimbDescription;

        let tdReimbAuthor = document.createElement('td');
        tdReimbAuthor.innerHTML = reimbursement.reimbAuthor;

        tr.appendChild(tdReimbId);
        tr.appendChild(tdReimbAmount);
        tr.appendChild(tdReimbSubmitted);
        tr.appendChild(tdReimbResolved);
        tr.appendChild(tdReimbStatus);
        tr.appendChild(tdReimbType);
        tr.appendChild(tdReimbDescription);
        tr.appendChild(tdReimbAuthor);
        tr.appendChild(tdReimbResolver);

        tbodyElement.appendChild(tr);

    }
}

let addAReimbursementLink = document.querySelector('#add-reimbursement');

addAReimbursementLink.addEventListener('click', newReimbursement);

let reimbModal = document.querySelector('#new-reimbursement-modal');

function newReimbursement() {

  reimbModal.classList.add('is-active');

}

let closeButton = reimbModal.querySelector('#cancel-reimbursement');
closeButton.addEventListener('click', () => {

    reimbModal.classList.remove('is-active');

});

let closeButtonV2 = document.querySelector('#cancel-reimbursement-v2');
closeButtonV2.addEventListener('click', () => {

    reimbModal.classList.remove('is-active');

});

const fileInput = document.querySelector('#reim-receipt input[type=file]');
  fileInput.onchange = () => {
    if (fileInput.files.length > 0) {
      const fileName = document.querySelector('#reim-receipt .file-name');
      fileName.textContent = fileInput.files[0].name;
    }
  }


