// Initial page load
let userObj;
window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/loginStatus', {
        method: 'GET',
        credentials: 'include'
    });
    if (res.status === 200) {
        userObj = await res.json();
        if (userObj.ersRole === 'Finance Manager') {
            window.location.href = 'finance-manager-home.html';
        }
    } else if (res.status === 401) {
        window.location.href = 'index.html';
    }
    //localStorage.setItem('userObj', JSON.stringify(userObj));
    populateTableWithReimbursements();


});
//let retrievedPerson = JSON.parse(localStorage.getItem('userObj'));





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
    let reimbursementArray = await res.json();

    for (let i = 0; i < reimbursementArray.length; i++) {
        let reimbursement = reimbursementArray[i];

        let tr = document.createElement('tr');

        let tdReimbId = document.createElement('td');
        tdReimbId.innerHTML = reimbursement.reimbId;

        let tdReimbAmount = document.createElement('td');
        tdReimbAmount.innerHTML = '$' + reimbursement.reimbAmount;

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

let submitReimbursementButton = document.querySelector('#sumbit-reimbursement');

submitReimbursementButton.addEventListener('click', addReimbursement);

async function addReimbursement() {

    let reimbTypeInput = document.querySelector('#reimb-type');
    let value = reimbTypeInput.options[reimbTypeInput.selectedIndex].textContent;

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

    let data = await res.json();

    if (res.status === 201) {
        let successMessage = document.createElement('p');
        let employeeDiv = document.querySelector('#message');
        employeeDiv.innerHTML = '';

        successMessage.innerHTML = "You've successfully added a new reimbursement!"
        successMessage.style.color = '#00d1b2';
        successMessage.style.textAlign = 'center';
        employeeDiv.appendChild(successMessage);

        reimbTypeInput.selectedIndex = 0;
        reimbDescription.value = '';
        reimbAmount.value = '';
        let refreshFileName = document.querySelector('#reim-receipt .file-name');
        refreshFileName.innerHTML = 'Receipt must be of JPEG/JPG, PNG, or GIF';

        populateTableWithReimbursements();

    }

    if (res.status === 400) {

        let errorMessage = document.createElement('p');
        let employeeDiv = document.querySelector('#message');
        employeeDiv.innerHTML = '';

        errorMessage.innerHTML = data.message;
        errorMessage.style.color = '#f14668';
        errorMessage.style.textAlign = 'center';
        employeeDiv.appendChild(errorMessage);

    }
}

const fileInput = document.querySelector('#reim-receipt input[type=file]');
fileInput.onchange = () => {
    if (fileInput.files.length > 0) {
        const fileName = document.querySelector('#reim-receipt .file-name');
        fileName.textContent = fileInput.files[0].name;
    }
}


