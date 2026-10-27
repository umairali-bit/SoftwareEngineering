// promises
console.log("Calling the API...");

const resp = fetch("https://dummyjson.com/users")

resp.then((response) => {
    console.log(response);

    const results = response.json();
    results.then((res) => {
        console.log(res);

        const users = res.users;

        for (let user of users){
            console.log(user.id + " " + user.firstName + " " + user.lastName);
        }
    });

}).catch((error) => {
    console.log(error);
})

console.log(resp);


// Creating a promise
console.log("1. Starting");

const promise = new Promise((resolve, reject) => {
    setTimeout(() => {
        console.log("2. Resolving");
        resolve("Success!");
    }, 3000);
});

promise.then((message) => {
    console.log("3. Got the data:", message);
});

console.log("4. End of synchronous code");

// Creating a promise with resolve and reject
const promise2 = new Promise((resolve, reject) => {
    setTimeout(() => {
        const success = true;
        if (success) {
            resolve("Promise resolved successfully!");
        } else {
            reject("Promise rejected!");
        }
    }, 3000);
});

promise2.then((message) => {
    console.log("Got the data:", message);
}).catch((error) => {
    console.error("Error:", error);
});
