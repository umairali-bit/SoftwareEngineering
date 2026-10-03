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