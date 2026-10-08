// loops in Java

for(let i = 0; i <=5; i++) {

    console.log(i, i*2);
}

let s = "Hello World";
let j = 0;
while(j < s.length) {
    console.log(s[j]);
    j++;
}

let t = "Hello World";
let k = 0;
while(k < 10) {
    console.log(t);
    k++;
}

let m = 0
do{
    console.log("This is a do while loop");
    m++
} while(m < 4) 


// for in loop

const person = {
    name: "Walter White",
    age: 50,
    city: "Albuquerque"
}

for (let key in person) {
    console.log(key, person[key]);
}

// for of loop

const char = "Breaking Bad";
for (let c of char) {
    console.log(c);
}