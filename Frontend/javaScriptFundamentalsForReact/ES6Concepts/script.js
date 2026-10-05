// Spread Operator


function show(a,b,...args){
    console.log(a);
    console.log(b);
    console.log(args);
}
show('one','two','three','four','five','six');

const sum = (a,b,...c) => {
    let result = a
    result += b 

    console.log(c);

    for(let i = 0; i < c.length; i++){
        result += c[i]
    }

    return result 
} 

console.log(sum(4 , 5 , 6 , 7 , 8 , 9 , 10));

const person = {
    name: "Saul Goodman",
    age: 45,
    city: "Albuquerque"
}

const address = {
    street: "123 Main St",
    state: "New Mexico",
    zip: "87101"
}

const personWithAddress = {
    ...person, 
    ...address }

console.log(personWithAddress);


const arr1 = [1,2,3,4,5]
const arr2 = [6,7,8,9,10]

const combinedArr = [...arr1, ...arr2]
console.log(combinedArr);

// Object destructuring

const city = {
    name: "Albuquerque",
    state: "New Mexico",
    population: 560000,
    zip: "87101"
}

let{name, population, ...allOthers} = city
console.log(name);
console.log(population)
console.log(allOthers)


const arr = [1,2,3,4,5,6,7,8,9]
const [a,b,...rest] = arr
console.log(a);
console.log(b);
console.log(rest);


// temple literals

const professor = "Walter White"
const age = 50

const message = `My name is ${professor} and I am ${age} years old.`
console.log(message);
