// functions in JavaScript

function greet() {
    console.log("Greetings from Umair")
}


greet();
greet();

// functions in JavaScript with parameters
function sayHello(name) {
    console.log(name, "says Hello");
}

sayHello("Umair");

// functions in JavaScript with return value
function multiply(num1, num2) {
    console.log("Multiplying", num1, "and", num2);
    return num1 * num2;
}

const res = multiply(5, 10);
console.log("Result:", res);

// arrow functions in JavaScript
const add = (a,b) => {
    console.log("Adding", a, "and", b);
    return a + b;
}

const sum = add(5, 10);
console.log("Sum:", sum);

// putting a function inside a variable
const divide = function(num1, num2) {
    console.log("Dividing", num1, "by", num2);
    return num1 / num2;
};

const result = divide(10, 2);
console.log("Result:", result);

// One line functions in JavaScript
const greeting = () => console.log("Hello from arrow function");
greeting();

const subtract = (num1, num2) => console.log("Subtracting", num2, "from", num1, ":", num1 - num2);
subtract(20, 10);

// default parameters in JavaScript functions
const greetingFunc = (name = "Guest") => console.log("Hello", name);
greetingFunc("Umair");
greetingFunc(); // will use default parameter

// passing functions as parameters in JavaScript
const calculate = (num1, num2, operation) => {
    console.log("Calculating", num1, "and", num2);
    return operation(num1, num2);
}

function addFunc(a, b) {
    return a + b;
}

const result1 = calculate(5, 10, addFunc);
console.log("Result of addition:", result1);

const multiplyNum = (a, b) => a * b;
const result2 = calculate(5, 10, multiplyNum);
console.log("Result of multiplication:", result2);

const divideNum = calculate(10, 2, (a, b) => a / b);
console.log("Result of division:", divideNum);

// returning a function from a function in JavaScript
// currying function
const mulFunction = (a) => {
    return (b) => a * b;
    }
const double = mulFunction(2);
const result3 = double(10);
console.log("Result of doubling:", result3);
