var _ = require('underscore')
// setTimeout(function() {
//     console.log(1)
// }, 0);
//
// new Promise(function exec(resolve) {
//     console.log(2);
//     for (var i=0; i<10000; i++) {
//         i === 9999 && resolve();
//     }
//
//     console.log(3);
// }).then(function() {
//     console.log(4);
// })
//
// console.log(5);

var a = [[1], [2], [3]];
var b = [...a];
b.shift().shift();
console.log(b);



