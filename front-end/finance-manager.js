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
        if (userObj.ersRole === 'Employee') {
            window.location.href = 'employee-home.html';
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
    let res = await fetch(`http://localhost:8080/reimbursements`, {
        credentials: 'include',
        method: 'GET'
    });

    populate(res);
}


let addAReimbursementLink = document.querySelector('#add-reimbursement');

addAReimbursementLink.addEventListener('click', newReimbursement);

let reimbModal = document.querySelector('#new-reimbursement-modal');

function newReimbursement() {

  reimbModal.classList.add('is-active');

}

async function populate(res) {
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

        let tdViewReceipt = document.createElement('td');
        let viewImageButton = document.createElement('button');
        viewImageButton.innerHTML = 'View Receipt';
        viewImageButton.className = "button is-link"
        tdViewReceipt.appendChild(viewImageButton);

        viewImageButton.addEventListener('click', () => {
            let viewReceiptModal = document.querySelector('#view-receipt-modal');

            let modalCloseElement = viewReceiptModal.querySelector('button');
            modalCloseElement.addEventListener('click', () => {
                viewReceiptModal.classList.remove('is-active');
            });

            let modalContentElement = viewReceiptModal.querySelector('.modal-content');
            modalContentElement.innerHTML = '';

            let imageElement = document.createElement('img');
            imageElement.setAttribute('src', `http://localhost:8080/reimbursements/${reimbursement.reimbId}/customerReceipt`);
            modalContentElement.appendChild(imageElement);

            viewReceiptModal.classList.add('is-active');

        });

        tr.appendChild(tdReimbId);
        tr.appendChild(tdReimbAmount);
        tr.appendChild(tdReimbSubmitted);
        tr.appendChild(tdReimbResolved);
        tr.appendChild(tdReimbStatus);
        tr.appendChild(tdReimbType);
        tr.appendChild(tdReimbDescription);
        tr.appendChild(tdReimbAuthor);
        tr.appendChild(tdReimbResolver);
        tr.appendChild(tdViewReceipt);

        tbodyElement.appendChild(tr);

    }

}

//localhost:8080/reimbursements?reimbStatus=Rejected
//`http://localhost:8080/reimbursements?reimbStatus=${reimbursement.reimbStatus}`

let filterRequestByAllLink = document.querySelector('#filter-all');
filterRequestByAllLink.addEventListener('click', populateTableWithReimbursements);

let filterRequestByRejectedLink = document.querySelector('#filter-rejected');
filterRequestByRejectedLink.addEventListener('click', async () => {

    let res = await fetch(`http://localhost:8080/reimbursements?reimbStatus=Rejected`, {
        credentials: 'include',
        method: 'GET'

    });

    populate(res);

});

let filterRequestByPendingLink = document.querySelector('#filter-pending');
filterRequestByPendingLink.addEventListener('click', async () => {

    let res = await fetch(`http://localhost:8080/reimbursements?reimbStatus=Pending`, {
        credentials: 'include',
        method: 'GET'

    });

    populate(res);

});

let filterRequestByApprovedLink = document.querySelector('#filter-approved');
filterRequestByApprovedLink.addEventListener('click', async () => {

    let res = await fetch(`http://localhost:8080/reimbursements?reimbStatus=Approved`, {
        credentials: 'include',
        method: 'GET'

    });

    populate(res);

});

let closeButton = reimbModal.querySelector('#cancel-reimbursement');
closeButton.addEventListener('click', () => {

    reimbModal.classList.remove('is-active');

});

let closeButtonV2 = document.querySelector('#cancel-reimbursement-v2');
closeButtonV2.addEventListener('click', () => {

    reimbModal.classList.remove('is-active');

});

let submitReimbursementButton = document.querySelector('#sumbit-reimbursement');

submitReimbursementButton.addEventListener('click', addReimbursement);

async function addReimbursement () {

    let reimbTypeInput = document.querySelector('#reimb-type');
    let value = reimbTypeInput.options[reimbTypeInput.selectedIndex].value;

    let reimbDescription = document.querySelector('#reimb-description');
    let reimbAmount = document.querySelector('#reimb-amount');

    const file = fileInput.files[0];

    let formData = new FormData();
    formData.append('reimbAmount', reimbAmount.value);
    formData.append('reimbType', value);
    formData.append('reimbDescription', reimbDescription.value);
    formData.append('reimbCustomerReceipt', file);

    let res = await fetch('http://localhost:8080/newReimbursement', {
        method: 'POST',
        credentials: 'include',
        body: formData
    });

    if (res.status === 201) {
        populateTableWithReimbursements();
    }
}

const fileInput = document.querySelector('#reim-receipt input[type=file]');
  fileInput.onchange = () => {
    if (fileInput.files.length > 0) {
      const fileName = document.querySelector('#reim-receipt .file-name');
      fileName.textContent = fileInput.files[0].name;
    }
  }
