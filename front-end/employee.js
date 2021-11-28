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
        }
    }
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

})
