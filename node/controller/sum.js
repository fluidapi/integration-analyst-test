module.exports = sum

function sum(array, key){
    return array.reduce((result, currentValue) => {
        return result + currentValue[key]
    }, 0)
}