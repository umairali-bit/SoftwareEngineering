// creating and object

const person = {
    name:"Walter White",
    age: 50,
    address:{
        street:"308 Negra Arroyo Lane",
        city:"Albuquerque",
        state:"New Mexico"
    },
    isActive: true,
    hobbies: ["Chemistry", "Cooking", "Money Laundering"],

    walk: function() {
        console.log(this.name +" is walking");
    }
};

console.log(person);
console.log(person.name);
console.log(person.address.city);
console.log(person.hobbies[1]);

person.name = "Heisenberg";
console.log(person.name);

person.walk();

import { greetings as grretFn, PI_VALUE } from "./greet.js";
grretFn();
console.log(PI_VALUE);

import addFn from "./greet.js";
console.log(addFn(5, 10));