let loginButton = document.querySelector('#loginBtn');

loginButton.addEventListener('click', isLoggedIn);

async function isLoggedIn() {
    let usernameInput = document.querySelector('#username');
    let passwordInput = document.querySelector('#password');


        let res = await fetch('http://localhost:8080/login', {
            method: 'POST',
            credentials: 'include',
            body: JSON.stringify({
                ersUsername: usernameInput.value,
                ersPassword: passwordInput.value
            }) 
        });

        let data = await res.json();

        // Check if login is incorrect or not
        
        // if the status code is 400, data will represent an object
        // with the "message" property, which we can display
        // if (res.status === 400) {
        //     let loginErrorMessage = document.createElement('p');
        //     let loginDiv = document.querySelector('#login-info');

        //     loginErrorMessage.innerHTML = data.message;
        //     loginErrorMessage.style.color = 'red';
        //     loginDiv.appendChild(loginErrorMessage);
        // }

        // However, if the status code is 200, the data variable will represent an object
        // that corresponds with the User
        // We can go ahead and check what role the user has
        if (res.status === 200) {
            console.log(data.ersRole);
            if (data.ersRole === 'Employee') {
                // redirect to associate homepage
                window.location.href = 'employee-home.html';
        //     } else if (data.ersRole === 'Finance Manager') {
        //         window.location.href = 'trainer-homepage.html';
            }
        }

}