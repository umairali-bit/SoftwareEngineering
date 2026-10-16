// arrays in Javascript can store anything 

const a = ['a', 2, true, "hello", {name: "Hank"}, ()=>"hello world"];
console.log(a)
console.log(a[5]()) // hello world

for(let value of a){
    console.log(value)
}

a.push("new value") // adds a new value to the end of the array
console.log(a)

a.pop() // removes the last value from the array
console.log(a)

a.forEach((value, index, array) => {
    console.log(value, index, array)
})

a.sort
console.log(a) // sorts the array in place, but only works for strings and numbers

console.log(a.includes("hello")) // checks if the array includes a certain value
const b = a.indexOf(2) // returns the index of the value in the array, or -1 if not found
console.log(b)

a.splice(1, 2) // removes 2 values starting from index 1
console.log(a)

a.splice(2, 0, "new value") // adds a new value at index 2 without removing any values
console.log(a)

// filter

const c = [2,5,7,8,9,10]
const d = c.filter(value => value >5) // returns a new array with values that pass the test
console.log(d)


// map
const e = [2,5,7,8,9,10]
const f = e.map(value => value * 2) // returns a new array with the results of calling a function on every element in the array
console.log(f)

// chaining filer and map
const g = [2,5,7,8,9,10]
const h = g.filter(value => value >5).map(value => value * 2) // returns a new array with the results of calling a function on every element in the array that pass the test
console.log(h)

// passing an object to the map function
const i = g.map((value) => {
    return{
        "name": "Walter" + value,
        age: value
    }
})
console.log(i)

// find method
const j = i.find(value => value.name == "Walter8") // returns the first element in the array that passes the test

// reduce method
const k = [2,5,7,8,9,10]
const l = k.reduce((accumulator, currentValue) => accumulator + currentValue, 0) // returns a single value that is the result of calling a function on every element in the array
console.log(l) // 41