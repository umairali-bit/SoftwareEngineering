// set-timeout
console.log("Code executing before timeout");
const id = setTimeout(() => {
    console.log("This is a timeout");
}, 5000);

console.log("Timeout ID:", id);

clearTimeout(id);

console.log("Code executing after timeout");

// set-interval
let i = 1
function showTime() {
    const time = new Date()
    console.log(time.toLocaleTimeString());

    i++
    if(i == 5) {
        clearInterval(ids)
    }
}

const ids = setInterval(showTime, 1000);

