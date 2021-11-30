let goBackButton = document.querySelector('#goBack');

goBackButton.addEventListener('click', async () => {

    window.location.href = 'index.html';

});

let signUpForRealButton = document.querySelector('#signUpBtnForReal');

signUpForRealButton.addEventListener('click', signUp);

async function signUp() {

    let firstNameInput = document.querySelector('#firstName');
    let lastNameInput = document.querySelector('#lastName');
    let emailAddressInput = document.querySelector('#emailAddress');
    let usernameInput = document.querySelector('#username');
    let passwordInput = document.querySelector('#password');

    let selectUserRole = document.querySelector('#userRole');
    let value = selectUserRole.options[selectUserRole.selectedIndex].value;

    // let formData = new FormData();

    // formData.append('ersUsername', usernameInput.value);
    // formData.append('ersPassword', passwordInput.value);
    // formData.append('ersFirstName', firstNameInput.value);
    // formData.append('ersLastName', lastNameInput.value);
    // formData.append('ersEmail', emailAddressInput.value);
    // formData.append('ersRole', value);

    // for (var values of formData.values()) {
    //     console.log(values);
    // }

    let res = await fetch('http://localhost:8080/signup', {
        method: 'POST',
        credentials: 'include',
        body: JSON.stringify({
            ersUsername: usernameInput.value,
            ersPassword: passwordInput.value,
            ersFirstName: firstNameInput.value,
            ersLastName: lastNameInput.value,
            ersEmail: emailAddressInput.value,
            ersRole: value
        })

    });

    if (res.status === 200) {
        let successSignupModal = document.querySelector('#success-signup');

        successSignupModal.classList.add('is-active');
    }

}

let successSignupModal = document.querySelector('#success-signup');

let goBackToHome = successSignupModal.querySelector('#goBackToHomeBtn');
goBackToHome.addEventListener('click', () => {
    successSignupModal.classList.remove('is-active');

    window.location.href = 'index.html';
});