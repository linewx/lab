var _ = require('underscore')

function splat_apply(func) {
    return function(array) {
        //unpackage aguments
        return func.apply(null, array)
    }
}

function splat_call(func) {
    return function(array) {
        return func.call(null, )
    }
}

let addFunction = splat_apply(function() {
        let result = 0;
        for(let i=0; i<arguments.length; i++) {
            result = result + arguments[i];
        }
        return result;
    }
)

let addFunction_call = splat_call(function() {
    console.log('############')
    console.log(arguments)
    let result = 0;
    for(let i=0; i<arguments.length; i++) {
        result = result + arguments[i];
    }
    return result;
}
)

console.log(addFunction([1,2,3,4]))

var test = [1,2,3];
console.log(typeof(test))
console.log(typeof(_.toArray(test)))

console.log('start call add function')
console.log(addFunction_call([1,2,3,4]))


function unsplat(fun) { return function() {
    return fun.call(null, _.toArray(arguments))};
    }

    var joinElements = unsplat(function(array) { return array.join(' ') });

console.log(joinElements(1, 2));

var x = 10;
var o = { x: 15 };
 
function f()
{
    console.log('11111111')
    console.log(this.x);
}
 
f();
f.call(o);