function Fun(){};
var fn = new Fun();
console.log(fn.__proto__ === Fun.prototype);
console.log(Fun.prototype.__proto__ === Object.prototype);
console.log(Object.prototype.__proto__ === null);

console.log(Function.__proto__ === Object.__proto__ );
console.log(Function.prototype.__proto__ === Fun.prototype.__proto__); 

console.log(Fun.prototype.constructor == Fun);